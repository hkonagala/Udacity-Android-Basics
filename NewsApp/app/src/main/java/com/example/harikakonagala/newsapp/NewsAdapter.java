package com.example.harikakonagala.newsapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by Harika Konagala on 6/29/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, List<News> newsDate) {
        super(context, 0, newsDate);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        News data = getItem(position);
        TextView heading = (TextView) listItemView.findViewById(R.id.tv_heading);
        heading.setText(data.getWebTitle());

        TextView tags = (TextView) listItemView.findViewById(R.id.tv_tag);
        tags.setText("Tags: " +data.getTag());

        return listItemView;
    }
}
