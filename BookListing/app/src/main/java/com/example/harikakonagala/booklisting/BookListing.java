package com.example.harikakonagala.booklisting;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListing extends AppCompatActivity implements View.OnClickListener{


    public static final String LOG_TAG = BookListing.class.getSimpleName();
    private String SAMPLE_USGS_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=30";
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private BookAdapter bookAdapter;
    EditText keywordSearch;
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);

        ListView listView = (ListView) findViewById(R.id.book_list);
        bookAdapter = new BookAdapter(this, new ArrayList<books>());
        listView.setAdapter(bookAdapter);
        keywordSearch = (EditText) findViewById(R.id.et_search);
        searchButton = (Button) findViewById(R.id.search_button);


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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            searchAct();

        }else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO INTERNET CONNECTION");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchButton.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        searchButton.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {

        searchAct();
    }

    private void searchAct() {
        String search_url = SAMPLE_USGS_URL + keywordSearch.getText().toString().replaceAll("\\s+","+");
        bookTask bTask = new bookTask();
        bTask.execute(search_url);

    }


    private class bookTask extends AsyncTask<String, Void, List<books>>{

        @Override
        protected List<books> doInBackground(String... params) {
            if(params.length <1 || params[0] == null){
                return null;
            }
            List<books> listBooks = BookUtils.fetchBooksData(params[0]);
            return listBooks;
        }

        @Override
        protected void onPostExecute(List<books> bookses) {

            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO DATA AVAILABLE");
            bookAdapter.clear();

            if(bookses !=null && !bookses.isEmpty()){
                bookAdapter.addAll(bookses);
            }
        }
    }
}
