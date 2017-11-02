package com.example.aingaran.dbtest;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
//import android.media.ExifInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.aingaran.dbtest.SQLiteDatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS = 100;
    SQLiteDatabaseHelper db;

    static final int PICTURE = 1;
    static final String TEMP_DIR = "/PhotoGalleryApp";
    static final String TEMP_NAME = "/galleryTest";
    static final String EXTENTION = ".jpg";
    static final String ROOT_DIR = "/sdcard";
    final String TAG = this.getClass().getSimpleName();
    String filepath;
    ArrayList<ImageClass> gallery;
    ImageView imageView;
    int currImage = 0;
    File imageFile;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //location permissions
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS);

        //create db
        db = new SQLiteDatabaseHelper(this);
        db.printLogs();

        //create PhotoGallery folder if doesn't exist
        File folder = new File(ROOT_DIR + TEMP_DIR);
        if(!folder.exists()) {
            if(folder.mkdir())
                Log.d(TAG, "Created PhotoGallery directory");
            else
                Log.d(TAG, "failed to create PhotoGallery directory");
        } else {
            Log.d(TAG, "Failed to create PhotoGallery directory");
        }

        //ignore Uri excpeption
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        imageView = findViewById(R.id.imageView);

        Bundle filterGallery = getIntent().getExtras();
        if(filterGallery != null) {
            boolean isFiltered = filterGallery.getBoolean("filterGallery", true);
            if(isFiltered){
                gallery = (ArrayList<ImageClass>) getIntent().getSerializableExtra("filterGallery");
                setupImage(gallery.get(currImage));
            }
        }
        else{
            setupGallery();
        }
    }

    public void toCamera (View view) {
        //camera intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //save photo to GalleryApp/Photos
        filepath = genPath();
        imageFile = new File(filepath);
        Uri uriSavedImage = Uri.fromFile(imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        //start camera intent
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, PICTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "Temp filepath: " + filepath);

        //send filepath to tagging activity
        Intent tagIntent = new Intent(this, TaggingActivity.class);
        tagIntent.putExtra("imagePath", filepath);

        if((requestCode == PICTURE) && (resultCode == RESULT_OK)) {
            if(imageFile.exists()){
                //save a bitmap of the photo just  taken
                Bitmap bitmap = BitmapFactory.decodeFile(filepath);
            }
        }

        //start the tagging activity
        startActivity(tagIntent);
    }

    private String genPath() {
        String name;
        String time;

        time = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        name = ROOT_DIR + TEMP_DIR + TEMP_NAME + time + EXTENTION;

        return name;
    }

    public void toFilter(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("gallery", gallery);
        startActivity(intent);
    }

    public void setupGallery() {
        currImage = 0;
        gallery = new ArrayList<>();
        gallery = db.getGallery();
        if(gallery.size() > 0) {
            setupImage(gallery.get(currImage));
        }
    }

    public void setupImage(ImageClass img) {
        Log.d(TAG, "opening: " + img.getName());
        Bitmap bitmap = BitmapFactory.decodeFile(img.getName());
        int hscale = (int) (bitmap.getHeight() * (512.0/bitmap.getWidth()));    //height scale
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, hscale, true);
        imageView.setImageBitmap(scaled);
        //imageView.setRotation(90);
        db.printLogs();
    }

    public void nextImage(View view) {
        if((currImage + 1) < gallery.size()) {
            currImage++;
            Log.d(TAG, "Current Image: " + gallery.get(currImage).getName());
            setupImage(gallery.get(currImage));
        } else{
            Toast toast = Toast.makeText(this, "Already last image", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void prevImage(View view) {
        if((currImage - 1) >= 0) {
            currImage--;
            Log.d(TAG, "Current Image: " + db.getGallery().get(currImage).getName());
            setupImage(db.getGallery().get(currImage));
        } else{
            Toast toast = Toast.makeText(this, "Already first image", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


