package com.example.harikakonagala.newsapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {


    public static final String LOG_TAG = NewsActivity.class.getName();

    TextView emptyTextView;
    ProgressBar progressBar;
    private NewsAdapter newsAdapter;
    private static final String DEFAULT_QUERY = "debate";
    String searchQuery = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView listView = (ListView) findViewById(R.id.news_list);

        emptyTextView = (TextView) findViewById(R.id.empty_text_view);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(newsAdapter);
        listView.setEmptyView(emptyTextView);

        fetchCurrentNews(DEFAULT_QUERY);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = newsAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                fetchCurrentNews(query);
                searchQuery = query;
                searchViewAndroidActionBar.clearFocus();
                searchViewAndroidActionBar.setQuery("", false);
                searchViewAndroidActionBar.setIconified(true);
                searchViewItem.collapseActionView();
                NewsActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void fetchCurrentNews(String query){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            NewsTask task = new NewsTask();
            task.execute(query);
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO INTERNET CONNECTION");
        }

    }


    private class NewsTask extends AsyncTask<String, Void, List<News>>{

        private static final String BASE_URL = "https://content.guardianapis.com/";
        @Override
        protected List<News> doInBackground(String... params) {

            if(params.length <1 || params[0] == null){
                return null;
            }
            List<News> listNews = NewsUtility.fetchNews(BASE_URL + "search?q=" + params[0]);
            return listNews;
        }

        @Override
        protected void onPostExecute(List<News> newses) {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO RELEVENAT NEWS AVAILABLE");
            newsAdapter.clear();

            if(newses !=null  && !newses.isEmpty()){
                newsAdapter.addAll(newses);
            }
        }
    }
}
