package com.example.harikakonagala.booklisting;

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

import static android.R.attr.rating;
import static android.R.attr.thumbnail;

/**
 * Created by Harika Konagala on 6/28/2017.
 */

public class BookUtils {

    public static final String LOG_TAG = BookUtils.class.getSimpleName();

    public BookUtils() {
    }

    private static List<books> extractBooks(String booksJSON){
        if (TextUtils.isEmpty(booksJSON)) {
            return null;
        }

        List<books> booksList = new ArrayList<>();
        try{

            JSONObject jsonObj = new JSONObject(booksJSON);
            JSONArray items = jsonObj.getJSONArray("items");

                for(int i =0;i<items.length();i++) {
                    JSONObject item = items.getJSONObject(i);
                    JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                    String title = volumeInfo.getString("title");
                    String[] authors = new String[]{"No Authors"};
                    if (!volumeInfo.isNull("authors")) {
                        JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                        Log.d(LOG_TAG, "authors #" + authorsArray.length());
                        authors = new String[authorsArray.length()];
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authors[j] = authorsArray.getString(j);
                        }
                    }
                    String info = volumeInfo.getString("infoLink");

                    // Json parsing for image
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    String images = "";
                    if (imageLinks.has("imageLinks")) {
                        images = imageLinks.getString("smallThumbnail");
                    }

                    books data = new books(title, authors, info, images);
                    booksList.add(data);
                }

        }catch (JSONException e){

            Log.e("BookUtils", "Problem parsing the booksJSON results", e);
        }

        return booksList;
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

    public static List<books> fetchBooksData(String requestUrl){
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {

            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<books> bookItems = extractBooks(jsonResponse);


        return bookItems;
    }
}


