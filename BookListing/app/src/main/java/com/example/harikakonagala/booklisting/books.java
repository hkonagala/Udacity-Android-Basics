package com.example.harikakonagala.booklisting;

import java.util.Arrays;

import static android.R.attr.rating;

/**
 * Created by Harika Konagala on 6/28/2017.
 */

public class books {
    private String title;
    private String[] authors;
    //private String date;
    //private float rating;
    //private float price;
    private String infoLink;


    public books(String title, String[] authors, String infoLink) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                '}';
    }
}
