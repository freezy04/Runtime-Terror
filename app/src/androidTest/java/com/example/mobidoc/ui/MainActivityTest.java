package com.example.mobidoc.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testInUserAcceptanceCriteria(){
        activityScenarioRule.getScenario().onActivity(activity -> {

        });
    }

}