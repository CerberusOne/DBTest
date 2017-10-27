package com.example.aingaran.dbtest;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
//import android.media.ExifInterface;
import android.location.Location;
import android.location.LocationManager;
import android.support.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS = 100;
    SQLiteDatabaseHelper db;
    private ListView listView;

    static final int PICTURE = 1;
    static final String PATH = "/sdcard/test.jpg";
    ImageView imageTest;
    String TAG = this.getClass().getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Camera permissions
        requestPermissions(new String[] {Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS);

        //location permissions
        ActivityCompat.requestPermissions(this, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); //set location permissions

        //create db
        db = new SQLiteDatabaseHelper(this);
        //listView = (ListView)findViewById(R.id.listView);
        ImageClass img1 = new ImageClass("caterham", 2017, 10, 4);
        ImageClass img2 = new ImageClass("audi", 2014, 1, 4);
        ImageClass img3 = new ImageClass("pagani", 1999, 3, 17);

        //InsertData(img1);
        //InsertData(img2);
        //InsertData(img3);

        imageTest = (ImageView)findViewById(R.id.imageTest);

        //ignore Uri excpeption
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        createListView();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void createListView() {
        ArrayList<ImageClass> list = new ArrayList<>();
        ArrayList<String> listNames = new ArrayList<>();

/*
        Cursor cursor = db.getAllData();
        while(cursor.moveToNext()) {
            list.add(cursor.getString(1));
        }
*/
/*
        //filter data by date
        //list = db.TimeFilter(2016, 2018);
        list = db.TimeFilterList(2016, 2018);
        for(int i = 0; i < list.size()-1; i++) {
            listNames.add(list.get(i).getName());
        }

        Log.d("MainActivity", Arrays.toString(list.toArray()));

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNames);
        listView.setAdapter(adapter);
        */
    }

    public void toCamera (View view) {
        //camera intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //save photo to GalleryApp/Photos
        Uri uriSavedImage = Uri.fromFile(new File(PATH));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        //start camera intent
        startActivityForResult(intent, PICTURE);

    }


    //send bitmap data to tagging activity
        //use tagging activity to save the photo to the database
            //name will be the id + photo
        //use tagging activity to get date and location
        //use tagging activity to save to database
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        String date = null;
        float[] latLong = null;
        double latVal = 0, longVal = 0;
        String latString = null, longString = null;
        String filepath = PATH;

        //send filepath to tagging activity
        Intent tagIntent = new Intent(this, TaggingActivity.class);
        tagIntent.putExtra("imagePath", PATH);

        if((requestCode == PICTURE) && (resultCode == RESULT_OK)) {
            //File io
            File imageFile = new File(PATH);
            if(imageFile.exists()){
                //save a bitmap of the photo just  taken
                Bitmap bitmap = BitmapFactory.decodeFile(PATH);
            }
        }

        //start the tagging activity
        startActivity(tagIntent);

/*
        //check for camera results
        if ((requestCode == PICTURE) && (resultCode == RESULT_OK)) {
            File imageFile = new File(filepath );
            if(imageFile.exists()) {
                //save file
                Bitmap bitmap = BitmapFactory.decodeFile(filepath);

                //modify file for viewing
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);

                intent.putExtra("photo", bitmap);

                imageTest.setImageBitmap(scaled);
                imageTest.setRotation(90);

                try{
                    ExifInterface exifInterface = new ExifInterface(filepath );
                    date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                    Log.d(TAG, "Date: " + date);
*/
/*                  //deprecated getLatLong(float [])
                    if(exifInterface.getLatLong(latLong)) {
                        latVal = latLong[0];
                        longVal = latLong[1];
                        Log.d(TAG, "Lat: " + latVal);
                        Log.d(TAG, "Long: " + longVal);
                    } else {
                        Log.d(TAG, "getLatLong Failed");
                    }
                    //latString = exifInterface.getAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE);
                    //longString = exifInterface.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE);
*/
/*
                    //using new double getLatLong
                    final double[] latLongDouble = exifInterface.getLatLong();
                    if(latLongDouble != null) {
                        latVal = latLongDouble[0];
                        longVal = latLongDouble[1];
                        Log.d(TAG, "Lat: " + latVal);
                        Log.d(TAG, "Long: " + longVal);
                    } else {
                        Log.d(TAG, "LatLong failed");
                    }
*/
/*                  //creating location tag upon photo capture, work around to exifinterface
                    //check the permissions for location tracking
                    int permissionCheck;
                    permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION );

                    //only provide location if permission was granted
                    if(permissionCheck == PERMISSION_GRANTED) {
                        LocationManager lm = (LocationManager) MainActivity.this.getSystemService(MainActivity.this.LOCATION_SERVICE);
                        //Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            longVal = location.getLongitude();
                            latVal = location.getLatitude();
                            Log.d(TAG, "Lat: " + latVal);
                            Log.d(TAG, "Long: " + longVal);
                        } else {
                            Log.d(TAG, "No Location");
                        }
                    }
                    else{
                        Log.d(TAG, "No Location permission");
                        longVal = 46.1;
                        latVal = 46.1;
                    }
*/
/*
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //ImageClass img = new ImageClass(filepath , year)





            }
        }
*/
    }
}

