package com.example.deepanshu.newsapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.deepanshu.newsapp.R;
import com.example.deepanshu.newsapp.customfonts.MyTextView;
import com.example.deepanshu.newsapp.model.NewsFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 01/04/16.
 */
public class ListBaseAdapter extends BaseAdapter {

    private Context context;

    private List<NewsFeed> newsFeeds;

    public ListBaseAdapter(Context context) {
        this.context = context;
        this.newsFeeds = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return newsFeeds.size();
    }

    @Override
    public NewsFeed getItem(int position) {
        return newsFeeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface fonts1 = Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Light.ttf");

        Typeface fonts2 = Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Regular.ttf");

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list, null);

            viewHolder = new ViewHolder();

            viewHolder.title = (MyTextView) convertView.findViewById(R.id.title);
            viewHolder.publicationDate = (MyTextView) convertView.findViewById(R.id.publicationDate);
            viewHolder.sectioName = (MyTextView) convertView.findViewById(R.id.sectionName);
            viewHolder.webUrl = (MyTextView) convertView.findViewById(R.id.weburl);

            viewHolder.title.setTypeface(fonts1);
            viewHolder.publicationDate.setTypeface(fonts1);

            viewHolder.title.setTypeface(fonts2);
            viewHolder.sectioName.setTypeface(fonts2);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsFeed item = getItem(position);

        viewHolder.title.setText(item.getTitle());
        viewHolder.sectioName.setText(item.getSectioName());
        viewHolder.publicationDate.setText(item.getPublicationDate());
        viewHolder.webUrl.setText(item.getWebUrl());

        return convertView;
    }

    public void addFeeds(List<NewsFeed> feedArrayList) {
        this.newsFeeds = feedArrayList;
    }

    public void clear() {
        newsFeeds.clear();
    }

    private class ViewHolder {

        MyTextView sectioName;
        MyTextView publicationDate;
        MyTextView title;
        MyTextView webUrl;
    }
}

