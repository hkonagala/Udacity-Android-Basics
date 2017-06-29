package com.example.harikakonagala.booklisting;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookListing extends AppCompatActivity {


    public static final String LOG_TAG = BookListing.class.getSimpleName();
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private BookAdapter bookAdapter;
    private static final String DEFAULT_QUERY = "android";
    String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);

        ListView listView = (ListView) findViewById(R.id.book_list);
        bookAdapter = new BookAdapter(this, new ArrayList<books>());
        listView.setAdapter(bookAdapter);

        emptyTextView = (TextView) findViewById(R.id.empty);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                books book = bookAdapter.getItem(position);
                Uri bookUri = Uri.parse(book.getInfoLink());

                Intent intent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(intent);
            }
        });


        listView.setEmptyView(emptyTextView);
        fetchBooks(DEFAULT_QUERY);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        final MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(query);
                searchQuery = query;
                // Reset SearchView
                searchViewAndroidActionBar.clearFocus();
                searchViewAndroidActionBar.setQuery("", false);
                searchViewAndroidActionBar.setIconified(true);
                searchViewItem.collapseActionView();
                // Set activity title to search query
                BookListing.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void fetchBooks(String query){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            bookTask bTask = new bookTask();
            bTask.execute(query);

        }else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO INTERNET CONNECTION");
        }
    }

    private class bookTask extends AsyncTask<String, Void, List<books>>{

        private static final String SAMPLE_USGS_URL = "https://www.googleapis.com/";
        @Override
        protected List<books> doInBackground(String... params) {

            if(params.length <1 || params[0] == null){
                return null;
            }
            List<books> listBooks = BookUtils.fetchBooksData(SAMPLE_USGS_URL + "books/v1/volumes?q=" +params[0]);
            return listBooks;
        }

        @Override
        protected void onPostExecute(List<books> bookses) {

            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO BOOKS AVAILABLE");
            bookAdapter.clear();

            if(bookses !=null && !bookses.isEmpty()){
                bookAdapter.addAll(bookses);
            }
        }
    }
}
