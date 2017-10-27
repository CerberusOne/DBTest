package com.example.aingaran.dbtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TaggingActivity extends AppCompatActivity {

    EditText captionInput;
    ArrayList<String> keywords; //list of keywords to add to db
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);

        //create a list of keywords
        keywords = new ArrayList<>();
    }

    public void addCaption (View view) {
        captionInput = (EditText)findViewById(R.id.captionInput);
        keywords.add(captionInput.getText().toString());
        Toast toast = Toast.makeText(getApplicationContext(),
                "Adding: " + captionInput.getText().toString(), Toast.LENGTH_SHORT);

        toast.show();
        captionInput.setText("");
    }

    public void addTimestamp(View view) throws ParseException {
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
    }

    public void addGeolocation(View view) {

    }

    public void Save(View view) {

    }




}
