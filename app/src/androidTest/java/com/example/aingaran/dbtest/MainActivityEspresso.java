package com.example.aingaran.dbtest;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Aingaran on 2017-10-05.
 */

public class MainActivityEspresso {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
/*
    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.editTextBox)).perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.sendButton)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.text)).check(matches(withText("HELLO")));
        onView(withContentDescription("Navigate up")).perform(click());
    }
*/
}
