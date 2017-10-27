package com.example.aingaran.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "photos.db";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "photos_table";
    public static final String COL0 = "ID";
    public static final String COL1 = "NAME";
    public static final String COL2 = "YEAR";
    public static final String COL3 = "MONTH";
    public static final String COL4 = "DAY";


    public SQLiteDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT, " +
                COL2 + " INTEGER, " +
                COL3 + " INTEGER, "  +
                COL4 + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        //onCreate(db);
    }

    //user format: db.insert("filename", "2017-10-05 13:00:00");
    public boolean insertData(ImageClass img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL1, img.getName());
        Log.d("SQLiteDatabaseHelper", "inserting: " + img.getName() + ": " + img.getYear() +
                                                " " + img.getMonth() + " " + img.getDay()
                                                    + "to " + TABLE_NAME);

        values.put(COL2, img.getYear());
        values.put(COL3, img.getMonth());
        values.put(COL4, img.getDay());

        long insertId = db.insert(TABLE_NAME, null, values);
        db.close();

        //return insertId != -1;
        if(insertId == -1)
            return false;
        else
            return true;
    }
/*
    public ArrayList<String> TimeFilter(int syear, int eyear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> list = new ArrayList();
        Cursor cursor;

        //query db with dates
        cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE (" +
                COL2 + " BETWEEN " + syear  + " AND " + eyear   + ") AND (" +
                COL3 + " BETWEEN " + smonth + " AND " + emonth  + ") AND (" +
                COL4 + " BETWEEN " + sday   + " AND " + eday    + ")", null);

        //add results to a list of photo names
        if(cursor.getCount() != 0) {
            do {
                //list.add("test");
                //list.add(cursor.getString(cursor.getColumnIndex(COL1)));
                list.add(cursor.getString(1));
            } while(cursor.moveToNext());
        } else {
            return null;
        }

        //return new list
        return list;
    }
*/
    //returns all the data
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME, null);

        return cursor;
    }

    public ArrayList<ImageClass> TimeFilterList(int syear, int eyear) {
        Cursor cursor = getAllData();
        ArrayList<ImageClass> filteredList = new ArrayList<>();
        ImageClass img = new ImageClass();


        while(cursor.moveToNext()) {
            if(Integer.parseInt(cursor.getString(2)) >= syear){
                if(Integer.parseInt(cursor.getString(2)) <= eyear) {
                    img.setName(cursor.getString(1));
                    img.setYear(Integer.parseInt(cursor.getString(2)));
                    img.setMonth(Integer.parseInt(cursor.getString(3)));
                    img.setDay(Integer.parseInt(cursor.getString(4)));
                    filteredList.add(img);
                }
            }
        }

        return filteredList;
    }
}
