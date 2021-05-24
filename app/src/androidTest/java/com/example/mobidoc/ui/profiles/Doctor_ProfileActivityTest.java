package com.example.mobidoc.ui.profiles;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.R;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class Doctor_ProfileActivityTest {

    @Rule
    public ActivityScenarioRule<Doctor_ProfileActivity> activityScenarioRule = new ActivityScenarioRule<>(Doctor_ProfileActivity.class);

    @Test
    public void getDoctorDetails() {
        activityScenarioRule.getScenario().onActivity(activity -> {
           activity.getDoctorDetails("JAKLyVL37YV3PM0HZoUa8EhFWth2");
        });
    }

    @Test
    public void clickOnNavBar_consultation() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_consultation);
        });
    }

    @Test
    public void clickOnNavBar_appointment() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_appointments);
        });
    }

    @Test
    public void clickOnNavBar_profle() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_profile);
        });
    }

    @Test
    public void clickOnNavBar_home() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_home);
        });
    }
}