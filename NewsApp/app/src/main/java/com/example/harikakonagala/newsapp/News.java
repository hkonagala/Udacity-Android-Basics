package com.example.harikakonagala.newsapp;

/**
 * Created by Harika Konagala on 6/29/2017.
 */

public class News {

    private String webTitle;
    private String webUrl;
    private String tag;
    private String author;
    private String date;

    public News(String webTitle, String webUrl, String tag, String author, String date) {
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.tag = tag;
        this.author = author;
        this.date = date;
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

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}
