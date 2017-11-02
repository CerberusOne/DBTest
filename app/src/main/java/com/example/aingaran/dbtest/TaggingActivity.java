package com.example.aingaran.dbtest;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class TaggingActivity extends AppCompatActivity {
    String TAG = "TaggingActivity";
    EditText captionInput;
    Intent cameraIntent;
    String date;
    Bitmap bitmap = null;
    ImageView imageView;
    ExifInterface exifInterface;
    ImageClass img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);

        img = new ImageClass();
        imageView = findViewById(R.id.imageView);
        cameraIntent = getIntent();

        //set the file's name
        img.setName(cameraIntent.getStringExtra("imagePath"));

        //open image
        File imageFile = new File(img.getName());
        if(imageFile.exists()) {
          //  try {
                //get bitmap of file
                bitmap = BitmapFactory.decodeFile(img.getName());

                //modify the photo to fit the screen and orientation
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                imageView.setImageBitmap(scaled);
                //imageView.setRotation(90);
            //} catch (Exception e) {
              //  e.printStackTrace();
            //}
        }
    }

    public void addCaption (View view) {
        captionInput = findViewById(R.id.caption);
        String caption = captionInput.getText().toString();
        Log.d(TAG, "Caption: " + caption);
        captionInput.setText("");
        img.setCaption(caption);
    }

    public void addTimestamp(View view) throws ParseException {
        try {
            exifInterface = new ExifInterface(img.getName());
            date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            Log.d(TAG, "date: " + date);
            String[] splitWhitespace = date.split(" ");
            String[] splitDates = splitWhitespace[0].split(":");

            img.setYear(Integer.parseInt(splitDates[0]));
            img.setMonth(Integer.parseInt(splitDates[1]));
            img.setDay(Integer.parseInt(splitDates[2]));

            Log.d(TAG, "Year: " + img.getYear());
            Log.d(TAG, "Month: " + img.getMonth());
            Log.d(TAG, "Day: " + img.getDay());
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addGeolocation(View view) {
        int permissionCheck;

        permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //only provide location if permission was granted
        if (permissionCheck == PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
            //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                img.setLat((int)location.getLatitude());
                img.setLon((int)location.getLongitude());
                Log.d(TAG, "Lat:" + img.getLat());
                Log.d(TAG, "Long:" + img.getLon());
            }
        } else {
            Log.d(TAG, "Lat: Failed");
            Log.d(TAG, "Long: Failed");
        }


        /* exif information method

        float [] latLong = null;

        try {
            exifInterface = new ExifInterface(img.getName());
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
        */
    }

    public void Save(View view) {
        SQLiteDatabaseHelper db;
        Intent intent = new Intent(this, MainActivity.class);

        db = new SQLiteDatabaseHelper(this);
        db.insertData(img.getName(), img.getYear(), img.getMonth(), img.getDay(),
                img.getLat(), img.getLon(), img.getCaption());
        Log.d(TAG, "Inserting: " + img.getName() + "\t" + img.getYear() + "\t"
                + img.getMonth() + "\t" + img.getDay() + "\t" + img.getLat() + "\t"
                + img.getLon() + "\t" + img.getCaption());

        startActivity(intent);
    }
}
