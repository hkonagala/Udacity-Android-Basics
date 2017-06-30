package com.example.harikakonagala.newsapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.key;
import static android.R.attr.tag;
import static com.example.harikakonagala.newsapp.NewsUtility.createUrl;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, LoaderCallbacks<List<News>>{


    public static final String LOG_TAG = NewsActivity.class.getName();

    private static int LOADER_ID = 0;
    SwipeRefreshLayout swipe;
    TextView emptyTextView;
    ProgressBar progressBar;
    private NewsAdapter newsAdapter;

    private LoaderManager loaderManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView listView = (ListView) findViewById(R.id.news_list);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        emptyTextView = (TextView) findViewById(R.id.empty_text_view);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(newsAdapter);
        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = newsAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });

        fetchLoader();

    }

    public void fetchLoader(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO INTERNET CONNECTION");
        }
    }


    @Override
    public void onRefresh() {

        loaderManager.restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        swipe.setRefreshing(false);
        if (data != null) {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO RELEVENAT NEWS AVAILABLE");
            newsAdapter.setNotifyOnChange(false);
            newsAdapter.clear();
            newsAdapter.setNotifyOnChange(true);
            newsAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        Log.i(LOG_TAG, "onLoaderReset() method is called....");
        newsAdapter.clear();
    }

}
