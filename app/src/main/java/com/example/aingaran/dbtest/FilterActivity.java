package com.example.aingaran.dbtest;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    SQLiteDatabaseHelper db;
    EditText caption;
    EditText yearStart;
    EditText yearEnd;
    EditText monthStart;
    EditText monthEnd;
    EditText dayStart;
    EditText dayEnd;
    EditText latStart;
    EditText latEnd;
    EditText longStart;
    EditText longEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        db = new SQLiteDatabaseHelper(this);
        caption = findViewById(R.id.caption);
        yearStart = findViewById(R.id.yearStart);
        yearEnd = findViewById(R.id.yearEnd);
        monthStart = findViewById(R.id.monthStart);
        monthEnd = findViewById(R.id.monthEnd);
        dayStart = findViewById(R.id.dayStart);
        dayEnd = findViewById(R.id.dayEnd);
        latStart = findViewById(R.id.latStart);
        latEnd = findViewById(R.id.latEnd);
        longStart = findViewById(R.id.longStart);
        longEnd = findViewById(R.id.longEnd);
    }

    public void filter() {
        ArrayList<ImageClass> list = new ArrayList<>();
        Cursor cursor = db.getAllData();
    }

    /*
    public ArrayList<ImageClass> filterList(ArrayList<ImageClass> list) {
        ArrayList<ImageClass> filterList = new ArrayList<>();
        ImageClass img = new ImageClass();
        Iterator<ImageClass> iterator = list.iterator();


        while(iterator.hasNext())
        {
            if (Integer.parseInt(iterator.next().getYear() <=) {
                if (Integer.parseInt(cursor.getString(2)) <= eyear) {
                    img.setName(cursor.getString(1));
                    img.setYear(Integer.parseInt(cursor.getString(2)));
                    img.setMonth(Integer.parseInt(cursor.getString(3)));
                    img.setDay(Integer.parseInt(cursor.getString(4)));
                    filterList.add(img);
                }
            }
        }

            return filterList;
    }
    */
}
