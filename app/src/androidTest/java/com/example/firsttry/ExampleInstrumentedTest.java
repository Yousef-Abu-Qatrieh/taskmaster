package com.example.firsttry;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;


import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.firsttry", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddNewTask() {


        onView(withId(R.id.addBtn)).perform(click());
        onView(withId(R.id.TaskTitle)).perform(typeText("Task8"), closeSoftKeyboard());
        onView(withId(R.id.taskDescription)).perform(typeText("skyDiving"), closeSoftKeyboard());
        onView(withId(R.id.editTextStat)).perform(typeText("assigned"), closeSoftKeyboard());
        onView(withId(R.id.submitButtonTask)).perform(click());

    }
}