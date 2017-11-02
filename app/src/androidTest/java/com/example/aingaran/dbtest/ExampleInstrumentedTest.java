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
    public void captionTest() throws Exception {
        ArrayList<ImageClass> list = new ArrayList<>();

        ImageClass img0 = new ImageClass("image0", 2017, 12, 30, 90, 180, "test1");
        ImageClass img1 = new ImageClass("image1", 1, 1, 1, 0, 0, "test2");
        ImageClass img2 = new ImageClass("image2", 1000, 6, 15, 45, 45, "test3");

        list.add(img0);
        list.add(img1);
        list.add(img2);


        ArrayList<ImageClass> high = new ArrayList<>(list);
        high = FilterActivity.filterCaption(high, img0.getCaption());
        assertEquals(img0.getCaption(), high.get(0).getCaption());

        ArrayList<ImageClass> middle = new ArrayList<>(list);
        middle = FilterActivity.filterCaption(middle, img1.getCaption());
        assertEquals(img1.getCaption(), middle.get(0).getCaption());

        ArrayList<ImageClass> low = new ArrayList<>(list);
        low = FilterActivity.filterCaption(low, img2.getCaption());
        assertEquals(img2.getCaption(), low.get(0).getCaption());
    }

    @Test
    public void DateTest() throws Exception {
        ArrayList<ImageClass> list = new ArrayList<>();

        ImageClass imghigh = new ImageClass("image0", 2017, 12, 30, 90, 180, "test1");
        ImageClass imglow = new ImageClass("image1", 1, 1, 1, 0, 0, "test1");
        ImageClass img2 = new ImageClass("image2", 1000, 6, 15, 45, 45, "test1");

        list.add(imghigh);
        list.add(imglow);
        list.add(img2);

        //high bound testing
        ArrayList<ImageClass> high = new ArrayList<>(list);
        high = FilterActivity.filterYear(high, 2016, 2017);
        high = FilterActivity.filterMonth(high, 12, 12);
        high = FilterActivity.filterDay(high, 30, 30);

        assertEquals(imghigh.getYear(), high.get(0).getYear());
        assertEquals(imghigh.getMonth(), high.get(0).getMonth());
        assertEquals(imghigh.getDay(), high.get(0).getDay());

        //low bound testing
        ArrayList<ImageClass> low = new ArrayList<>(list);
        low = FilterActivity.filterYear(low, 1, 1);
        low = FilterActivity.filterMonth(low, 1, 1);
        low = FilterActivity.filterDay(low, 1, 1);

        assertEquals(imglow.getYear(), low.get(0).getYear());
        assertEquals(imglow.getMonth(), low.get(0).getMonth());
        assertEquals(imglow.getDay(), low.get(0).getDay());
    }

    @Test
    public void LocationTest() throws Exception {
        ArrayList<ImageClass> list = new ArrayList<>();
        int highLat = 90;
        int highLong = 190;
        int lowLat = 0;
        int lowLong = 0;


        ImageClass imghigh = new ImageClass("image0", 2017, 12, 30, highLat, highLong, "test1");
        ImageClass imglow = new ImageClass("image1", 1, 1, 1, lowLat, lowLong, "test1");
        ImageClass img2 = new ImageClass("image2", 1000, 6, 15, 45, 45, "test1");

        list.add(imglow);
        list.add(imghigh);
        list.add(img2);

        //high bound testing
        ArrayList<ImageClass> high = new ArrayList<>(list);
        high = FilterActivity.filterLat(high, highLat, highLat);
        high = FilterActivity.filterLong(high, highLong, highLong);

        assertEquals(imghigh.getLat(), high.get(0).getLat());
        assertEquals(imghigh.getLon(), high.get(0).getLon());


        //low bound testing
        ArrayList<ImageClass> low = new ArrayList<>(list);
        low = FilterActivity.filterLat(low, lowLat, lowLat);
        low = FilterActivity.filterLong(low, lowLong, lowLong);

        assertEquals(imglow.getLat(), low.get(0).getLat());
        assertEquals(imglow.getLon(), low.get(0).getLon());
    }
/*
    @Test
    public void testDatabaseCreation() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(appContext);

        ImageClass imgCompare;
        db.insertData("image1", 2017, 10, 5, 50, 50, "test1");

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
*/
}
