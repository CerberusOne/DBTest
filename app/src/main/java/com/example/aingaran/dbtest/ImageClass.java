package com.example.aingaran.dbtest;

import android.content.Context;

/**
 * Created by Aingaran on 2017-10-05.
 */

public class ImageClass {
    private String name;
    private int year, month, day, lat, lon;
    private String caption;

    public ImageClass() {}

    public ImageClass(String name, int year, int month, int day, int lat, int lon, String caption) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.lat = lat;
        this.lon = lon;
        this.caption = caption;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getLat() { return lat; }

    public void setLat(int lat) { this.lat = lat; }

    public int getLon() { return lon; }

    public void setLon(int lon) { this.lon = lon; }

    public String getCaption() { return caption; }

    public void setCaption(String caption) { this.caption = caption; }
}
