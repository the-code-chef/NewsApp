package com.example.deepanshu.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.deepanshu.newsapp.adapter.ListBaseAdapter;
import com.example.deepanshu.newsapp.customfonts.ExpandableHeightListView;
import com.example.deepanshu.newsapp.model.NewsFeed;
import com.example.deepanshu.newsapp.service.NewsFeedServiceImpl;
import com.example.deepanshu.newsapp.utility.Utility;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsFeed>>, SwipeRefreshLayout.OnRefreshListener {

    private static final int LOADER_ID = 0;

    ListBaseAdapter baseAdapter;

    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        ExpandableHeightListView listview = (ExpandableHeightListView) findViewById(R.id.listview);

        baseAdapter = new ListBaseAdapter(MainActivity.this);

        listview.setAdapter(baseAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent contentBrowser = new Intent("android.intent.action.VIEW", Uri.parse(baseAdapter.getItem(position).getWebUrl()));
                startActivity(contentBrowser);
            }
        });

        if (Utility.isNetworkAvailable(MainActivity.this)) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        if (Utility.isNetworkAvailable(MainActivity.this)) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            swipe.setRefreshing(false);
        }
    }

    @Override
    public Loader<List<NewsFeed>> onCreateLoader(int id, Bundle args) {
        return new NewsFeedServiceImpl(this);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsFeed>> loader, List<NewsFeed> data) {
        swipe.setRefreshing(false);
        if (null != data) {
            baseAdapter.clear();
            baseAdapter.addFeeds(data);
            baseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsFeed>> loader) {

    }
}
