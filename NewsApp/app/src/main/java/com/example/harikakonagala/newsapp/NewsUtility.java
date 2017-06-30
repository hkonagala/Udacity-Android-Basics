package com.example.harikakonagala.newsapp;

import android.text.TextUtils;
import android.util.Log;

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
 * Created by Harika Konagala on 6/29/2017.
 */

public class NewsUtility {

    public static final String LOG_TAG = NewsUtility.class.getName();

    public NewsUtility() {
    }

    private static List<News> extractNewsJson(String NewsJson){
        Log.i(LOG_TAG, "Initiating parsing...");

        if(TextUtils.isEmpty(NewsJson)){
            return null;
        }

        List<News> newsItem = new ArrayList<>();
        try{
            JSONObject rootNewsObj = new JSONObject(NewsJson);
            JSONObject responseObj = rootNewsObj.getJSONObject("response");
            JSONArray results = responseObj.getJSONArray("results");

            for(int i =0; i< results.length(); i++){

                JSONObject result = results.getJSONObject(i);
                String title = result.getString("webTitle");
                String websiteUrl = result.getString("webUrl");
                String tag = result.getString("sectionName");


                News jsonNews = new News(title, websiteUrl, tag);
                newsItem.add(jsonNews);
            }
        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            e.printStackTrace();
        }
        return newsItem;

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> fetchNews(String requestUrl){

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            e.printStackTrace();
        }
        List<News> newsDataItem = extractNewsJson(jsonResponse);
        return newsDataItem;
    }

}
