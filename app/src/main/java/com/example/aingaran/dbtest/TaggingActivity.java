package com.example.aingaran.dbtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class TaggingActivity extends AppCompatActivity {
    String TAG = "TaggingActivity";
    EditText captionInput;
    String caption;
    int day, month, year;
    Intent cameraIntent;
    String imagePath;
    String date;
    Bitmap bitmap = null;
    ImageView imageView;
    ExifInterface exifInterface;
    double latVal, longVal;
    SQLiteDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);

        imageView = (ImageView)findViewById(R.id.imageView);
        cameraIntent = getIntent();

        //get file path from MainActivity
        imagePath = cameraIntent.getStringExtra("imagePath");

        //open image
        File imageFile = new File(imagePath);
        if(imageFile.exists()) {
            try {
                //get bitmap of file
                bitmap = BitmapFactory.decodeFile(imagePath);

                //modify the photo to fit the screen and orientation
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                imageView.setImageBitmap(scaled);
                imageView.setRotation(90);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void addCaption (View view) {
        captionInput = (EditText)findViewById(R.id.captionInput);
        caption = captionInput.getText().toString();
        Log.d(TAG, "Caption: " + caption);
        captionInput.setText("");
    }

    public void addTimestamp(View view) throws ParseException {
        try {
            exifInterface = new ExifInterface(imagePath);
            date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            Log.d(TAG, "date: " + date);
        }  catch (IOException e) {
            e.printStackTrace();
        }

        String[] splitWhitespace = date.split(" ");
        for(int i = 0; i < Array.getLength(splitWhitespace); i++) {
            Log.d(TAG, "Whitespace split " + i + ": " + splitWhitespace[i]);
        }

        String[] splitDates = splitWhitespace[0].split(":");
        for(int i = 0; i < Array.getLength(splitDates); i++) {
            Log.d(TAG, "Dates split " + i + ": " + splitDates[i]);
        }

        year = Integer.parseInt(splitDates[0]);
        month = Integer.parseInt(splitDates[1]);
        day = Integer.parseInt(splitDates[2]);


        /*
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateTime = sdf.format(Calendar.getInstance().getTime());

        Date parse = sdf.parse(dateTime);
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        //System.out.println(c.get(Calendar.MONTH) + c.get(Calendar.DATE) + c.get(Calendar.YEAR));
        day = c.get(Calendar.DATE);
        month  = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        Toast toast = Toast.makeText(getApplicationContext(),
                "Time: " + day + "/" + month + "/" + year, Toast.LENGTH_SHORT);
        toast.show();
        */
    }

    public void addGeolocation(View view) {
        float[] latLong = null;

        try {
            exifInterface = new ExifInterface(imagePath);
            if (exifInterface.getLatLong(latLong)) {
                latVal = latLong[0];
                longVal = latLong[1];
                Log.d(TAG, "Lat: " + latVal);
                Log.d(TAG, "Long: " + longVal);
            } else {
                Log.d(TAG, "getLatLong Failed");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Save(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        //db = new SQLiteDatabaseHelper(this);

        //InsertData(imagePath, );

        startActivity(intent);
    }
}
