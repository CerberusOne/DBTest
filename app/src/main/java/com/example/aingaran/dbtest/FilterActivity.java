package com.example.aingaran.dbtest;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
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
    ArrayList<ImageClass> gallery;
    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

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


        gallery = (ArrayList<ImageClass>) getIntent().getSerializableExtra("gallery");
        if(gallery.size() == 0) {
            Toast toast = Toast.makeText(this, "No data", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void filter(View view) {
        String captionInput     = caption.getText().toString();
        String yearStartInput   = yearStart.getText().toString();
        String yearEndInput     = yearEnd.getText().toString();
        String monthStartInput   = monthStart.getText().toString();
        String monthEndInput    = monthEnd.getText().toString();
        String dayStartInput    = dayStart.getText().toString();
        String dayEndInput      = dayEnd.getText().toString();
        String latStartInput    = latStart.getText().toString();
        String latEndInput      = latEnd.getText().toString();
        String longStartInput   = longStart.getText().toString();
        String longEndInput     = longEnd.getText().toString();



        if(captionInput != "" && captionInput.length() != 0){
            gallery = filterCaption(gallery, captionInput);
        }
        if(yearStartInput != "" && yearStartInput.length() != 0 && yearEndInput != "" && yearEndInput .length() != 0 ) {
            gallery = filterYear(gallery, Integer.parseInt(yearStartInput), Integer.parseInt(yearEndInput));
        }
        if(monthStartInput != "" && monthStartInput.length() != 0 && monthEndInput!= "" && monthEndInput.length() != 0 ) {
            gallery = filterMonth(gallery, Integer.parseInt(monthStartInput), Integer.parseInt(monthEndInput));
        }
        if(dayStartInput != "" && dayStartInput.length() != 0 && dayEndInput != "" && dayEndInput.length() != 0 ) {
            gallery = filterDay(gallery, Integer.parseInt(dayStartInput), Integer.parseInt(dayEndInput));
        }
        if(latStartInput != "" && latStartInput .length() != 0 && latEndInput != "" &&  latEndInput.length() != 0 ) {
            gallery = filterLat(gallery, Integer.parseInt(latStartInput), Integer.parseInt(latEndInput));
        }
        if(longStartInput  != "" && longStartInput.length() != 0 && longEndInput != "" &&  longEndInput .length() != 0 ) {
            gallery = filterLong(gallery, Integer.parseInt(longStartInput), Integer.parseInt(longEndInput));
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("filterGallery", gallery);
        startActivity(intent);
    }

    public static ArrayList<ImageClass> filterCaption(ArrayList<ImageClass> gallery, String key) {
        ArrayList<ImageClass> filteredList = new ArrayList<>();
        for(int i = 0; i < gallery.size(); i++) {
            //Log.d(TAG, "Caption Filter: " + gallery.get(i).getCaption());
            if(gallery.get(i).getCaption() != null) {
                if (gallery.get(i).getCaption().equals(key)) {
                    filteredList.add(gallery.get(i));
                }
            }
        }

        return filteredList;
    }

    public static ArrayList<ImageClass> filterYear(ArrayList<ImageClass> gallery, int start, int end) {
        ArrayList<ImageClass> filteredList = new ArrayList<>();
        for(int i = 0; i < gallery.size(); i++) {
            if(gallery.get(i).getYear() >= start && gallery.get(i).getYear() <= end) {
                filteredList.add(gallery.get(i));
            }
        }

        return filteredList;
    }

    public static ArrayList<ImageClass> filterMonth(ArrayList<ImageClass> gallery, int start, int end) {
        ArrayList<ImageClass> filteredList = new ArrayList<>();
        for(int i = 0; i < gallery.size(); i++) {
            if(gallery.get(i).getMonth() >= start && gallery.get(i).getMonth() <= end) {
                filteredList.add(gallery.get(i));
            }
        }

        return filteredList;
    }

    public static ArrayList<ImageClass> filterDay(ArrayList<ImageClass> gallery, int start, int end) {
        ArrayList<ImageClass> filteredList = new ArrayList<>();
        for(int i = 0; i < gallery.size(); i++) {
            if(gallery.get(i).getDay() >= start && gallery.get(i).getDay() <= end) {
                filteredList.add(gallery.get(i));
            }
        }

        return filteredList;
    }

    public static ArrayList<ImageClass> filterLat(ArrayList<ImageClass> gallery, int start, int end) {
        ArrayList<ImageClass> filteredList = new ArrayList<>();
        for(int i = 0; i < gallery.size(); i++) {
            if(gallery.get(i).getLat() >= start && gallery.get(i).getLat() <= end) {
                filteredList.add(gallery.get(i));
            }
        }

        return filteredList;
    }

    public static ArrayList<ImageClass> filterLong(ArrayList<ImageClass> gallery, int start, int end) {
        ArrayList<ImageClass> filteredList = new ArrayList<>();
        for(int i = 0; i < gallery.size(); i++) {
            if(gallery.get(i).getLon() >= start && gallery.get(i).getLon() <= end) {
                filteredList.add(gallery.get(i));
            }
        }

        return filteredList;
    }
}
