package com.example.aingaran.dbtest;

import android.content.Context;

/**
 * Created by Aingaran on 2017-10-05.
 */

public class ImageClass {
    private String name;
    private int year;
    private int month;
    private int day;

    public ImageClass() {}

    public ImageClass(String name, int year, int month, int day) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    void setName(String name) {
        this.name = name;
    }

    void setYear(int year) {
        this.year = year;
    }

    void setMonth(int month) {
        this.month = month;
    }

    void setDay(int day) {
        this.day = day;
    }

    String getName() {
        return name;
    }

    int getYear() {
        return year;
    }

    int getMonth() {
        return month;
    }

    int getDay() {
        return day;
    }
}
