package com.example.androiduitesting;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ShowActivityTest {

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testActivitySwitchCorrectly() {
        CityList.getInstance().clear();
        CityList.getInstance().addCity("Edmonton");

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        onView(withText("Edmonton")).perform(click());

        intended(hasComponent(ShowActivity.class.getName()));
    }

    @Test
    public void testCityNameConsistency() {
        String testCityName = "Calgary";

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", testCityName);

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        onView(withId(R.id.textView_cityName))
                .check(matches(withText(testCityName)));

        scenario.onActivity(activity -> {
            TextView cityNameTextView = activity.findViewById(R.id.textView_cityName);
            assertEquals(testCityName, cityNameTextView.getText().toString());
        });
    }

    @Test
    public void testBackButton() {

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ShowActivity.class);
        intent.putExtra("CITY_NAME", "Vancouver");

        ActivityScenario<ShowActivity> scenario = ActivityScenario.launch(intent);

        onView(withId(R.id.button_back))
                .check(matches(isDisplayed()));

        scenario.onActivity(activity -> {
            Button backButton = activity.findViewById(R.id.button_back);
            backButton.performClick();
        });

        scenario.onActivity(activity -> {
            assertTrue(activity.isFinishing());
        });
    }
}