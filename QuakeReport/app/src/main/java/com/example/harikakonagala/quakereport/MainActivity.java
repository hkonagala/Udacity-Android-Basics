package com.example.harikakonagala.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<earthquakeData>> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String SAMPLE_USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";

    private EarthQuakeAdapter earthquakeAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(LOG_TAG, "onCreate() method intialized..");
        ListView listView = (ListView) findViewById(R.id.city_list);

        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        earthquakeAdapter = new EarthQuakeAdapter(this ,new ArrayList<earthquakeData>());
        listView.setAdapter(earthquakeAdapter);

        //on click listener on every item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                earthquakeData earthQuakeData = earthquakeAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(earthQuakeData.getUrl());

                //implicit intent
                Intent i = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(i);
            }
        });

       // EarthquakeTask earthquakeTask = new EarthquakeTask();
       // earthquakeTask.execute(SAMPLE_USGS_URL);



        emptyTextView = (TextView) findViewById(R.id.empty);
        listView.setEmptyView(emptyTextView);
        ConnectivityManager connec = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connec.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }else {
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("NO INTERNET CONNECTION");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<earthquakeData>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader() method intialized..");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(SAMPLE_USGS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "15");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", "time");

        return new EarthquakeLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<earthquakeData>> loader, List<earthquakeData> data) {


        progressBar.setVisibility(View.GONE);
        emptyTextView.setText("NO EARTHQUAKES FOUND");
        Log.i(LOG_TAG, "onLoadFinished() method intialized..");
        earthquakeAdapter.clear();

        if(data!= null && !data.isEmpty()){
            earthquakeAdapter.addAll(data);
        }



    }

    @Override
    public void onLoaderReset(Loader<List<earthquakeData>> loader) {

        Log.i(LOG_TAG, "onLoaderReset() method is called....");
        earthquakeAdapter.clear();
    }

   /* private class EarthquakeTask extends AsyncTask<String, Void, List<earthquakeData>>{
        @Override
        protected List<earthquakeData> doInBackground(String... params) {

            if(params.length <1 || params[0] == null){
                return null;
            }

            List<earthquakeData> earthquakelist = QuertUtils.fetchEarthquakeData(params[0]);

            return earthquakelist;
        }

        @Override
        protected void onPostExecute(List<earthquakeData> earthquakeDatas) {

            earthquakeAdapter.clear();

            if(earthquakeDatas!= null && !earthquakeDatas.isEmpty()){
                earthquakeAdapter.addAll(earthquakeDatas);
            }
        }
    }*/

}
