package com.example.harikakonagala.quakereport;

/**
 * Created by Harika Konagala on 6/26/2017.
 */

public class earthquakeData {

    private Double mag;
    private String city;
    private Long date;
    private String url;

    public earthquakeData(Double mag, String city, Long date, String url) {
        this.mag = mag;
        this.city = city;
        this.date = date;
        this.url = url;
    }

    public Double getMag() {
        return mag;
    }

    public void setMag(Double mag) {
        this.mag = mag;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
