package com.example.mobidoc.ui.profiles;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.R;

import org.junit.Rule;
import org.junit.Test;

//Please work :(

public class Patient_ProfileTest {
    @Rule
    public ActivityScenarioRule<Patient_Profile> activityScenarioRule = new ActivityScenarioRule<>(Patient_Profile.class);



    @Test
    public void clickOnNavBar_profile() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked(R.id.menu_profile);
        });
    }

    @Test
    public void clickOnNavBar_home() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked(R.id.menu_home);
        });
    }

    @Test
    public void clickOnNavBar_appointments() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked(R.id.menu_appointments);
        });
    }

    @Test
    public void clickOnNavBar_consultation() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked(R.id.menu_consultation);
        });
    }

    @Test
    public void getPatientDetails() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.getPatientDetails("OIU0JFoE8ceLlx5qjKtn1fg5OiO2");
        });
    }
}