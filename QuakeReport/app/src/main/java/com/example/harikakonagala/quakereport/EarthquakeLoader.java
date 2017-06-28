package com.example.harikakonagala.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Harika Konagala on 6/28/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<earthquakeData>> {

    private String url;

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading() method is called....");
        forceLoad();
    }

    @Override
    public List<earthquakeData> loadInBackground() {

        Log.i(LOG_TAG, "loadInBackground() method is called....");
        if(url == null){
            return null;
        }
        List<earthquakeData> earthquakelist = QuertUtils.fetchEarthquakeData(url);

        return earthquakelist;
    }
}
