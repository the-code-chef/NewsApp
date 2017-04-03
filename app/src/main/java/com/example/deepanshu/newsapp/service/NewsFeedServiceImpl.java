package com.example.deepanshu.newsapp.service;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.deepanshu.newsapp.model.NewsFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepanshu on 2/4/17.
 */

public class NewsFeedServiceImpl extends AsyncTaskLoader<List<NewsFeed>> {

    private final static String LOG_TAG = NewsFeedServiceImpl.class.getSimpleName();

    private static final String RESPONSE = "response";
    private static final String RESULTS = "results";

    private static final String TITLE = "webTitle";
    private static final String SECTION_NAME = "sectionName";
    private static final String WEB_URL = "webUrl";
    private static final String API_URL = "apiUrl";
    private static final String PUBLICATION_DATE = "webPublicationDate";
    private static final String IS_HOSTED = "isHosted";

    public NewsFeedServiceImpl(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<NewsFeed> loadInBackground() {
        List<NewsFeed> listOfNews;
        String reponseStr = makeHttpRequest(getUri());
        listOfNews = parseJson(reponseStr);
        return listOfNews;
    }

    private ArrayList<NewsFeed> parseJson(String bookJsonStr) {
        ArrayList<NewsFeed> bookArrayList = new ArrayList<>();

        try {
            JSONObject newsJsonObject = new JSONObject(bookJsonStr);
            JSONObject responseData = newsJsonObject.getJSONObject(RESPONSE);
            JSONArray newsJsonArray = responseData.getJSONArray(RESULTS);

            if (null != newsJsonArray && newsJsonArray.length() > 0) {

                for (int i = 0; i < newsJsonArray.length(); i++) {

                    JSONObject itemJsonObject = newsJsonArray.getJSONObject(i);

                    bookArrayList.add(new NewsFeed(
                            itemJsonObject.getString(TITLE),
                            itemJsonObject.getString(SECTION_NAME),
                            itemJsonObject.getString(WEB_URL),
                            itemJsonObject.getString(PUBLICATION_DATE)
                    ));
                }
            } else {
                bookArrayList = null;
            }
        } catch (JSONException e) {
            Log.d(LOG_TAG, e.toString());
        }
        return bookArrayList;
    }

    private String makeHttpRequest(URL url) {
        String bookJsonStr = "";

        if (url == null) {
            return bookJsonStr;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Create the request to Google Books API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                if (null == inputStream) {
                    // Nothing to do.
                    bookJsonStr = null;
                }

                if (inputStream != null) {
                    reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line).append("\n");
                    }
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    bookJsonStr = null;
                }
                bookJsonStr = buffer.toString();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the book list data, there's no point in attempting
            // to parse it.
            bookJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return bookJsonStr;
    }

    private URL getUri() {
        try {
            return new URL(getUriBuilder());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUriBuilder() {
        return new Uri.Builder()
                .scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("q", "Android")
                .appendQueryParameter("api-key", "07ce41a3-ffb5-4ca1-8106-3705aa9dc404")
                .build()
                .toString();
    }
}
