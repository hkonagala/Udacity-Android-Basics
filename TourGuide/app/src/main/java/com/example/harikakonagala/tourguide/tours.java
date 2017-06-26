package com.example.harikakonagala.tourguide;

/**
 * Created by Harika Konagala on 6/24/2017.
 */

public class tours {

    private String title;
    private String desc;
    private String date;

    public tours(String title, String desc, String date) {
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }
}
