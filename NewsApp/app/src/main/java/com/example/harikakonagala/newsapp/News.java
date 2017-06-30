package com.example.harikakonagala.newsapp;

/**
 * Created by Harika Konagala on 6/29/2017.
 */

public class News {

    private String webTitle;
    private String webUrl;
    private String tag;

    public News(String webTitle, String webUrl, String tag) {
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.tag = tag;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getTag() {
        return tag;
    }
}
