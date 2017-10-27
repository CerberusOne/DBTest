package com.example.aingaran.dbtest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.aingaran.dbtest", appContext.getPackageName());
    }

    @Test
    public void testDatabaseCreation() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(appContext);
        ArrayList<ImageClass> list = new ArrayList<>();

        ImageClass img = new ImageClass("img1", 1965, 10, 5);
        ImageClass imgCompare;
        db.insertData(img);

        int startYear = 1960;
        int endYear = 1970;
        int startMonth = 9;
        int endMonth = 12;
        int startDay = 1;
        int endDay = 10;

        list = db.TimeFilterList(startYear, endYear);

        for(int i = 0; i < list.size()-1; i++) {
            imgCompare = list.get(i);

            //assert year
            assertTrue(startYear <= imgCompare.getYear());
            assertTrue(endYear >= imgCompare.getYear());

            //assert month
            assertTrue(startMonth <= imgCompare.getMonth());
            assertTrue(endMonth >= imgCompare.getMonth());

            //assert day
            assertTrue(startDay <= imgCompare.getDay());
            assertTrue(endDay >= imgCompare.getDay());

            System.out.println("year: " + imgCompare.getYear());
        }
    }
}
