package com.example.harikakonagala.booklisting;

import java.util.Arrays;

import static android.R.attr.rating;

/**
 * Created by Harika Konagala on 6/28/2017.
 */

public class books {
    private String title;
    private String[] authors;
    private String infoLink;
    private String imageURL;


    public books(String title, String[] authors, String infoLink, String imageURL) {
        this.title = title;
        this.authors = authors;
        this.infoLink = infoLink;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                '}';
    }
}
