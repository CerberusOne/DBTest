package com.example.aingaran.dbtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    public void openGallery(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
