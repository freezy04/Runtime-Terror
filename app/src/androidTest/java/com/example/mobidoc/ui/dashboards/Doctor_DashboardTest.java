package com.example.mobidoc.ui.dashboards;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class Doctor_DashboardTest {

    @Rule
    public ActivityScenarioRule<Doctor_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Doctor_Dashboard.class);

    /*@Test
    public void UserLoggedIn_ClickOnProfile(){
        activityScenarioRule.getScenario().onActivity(activity -> {
           activity.doctor_profile.performClick();
        });
    }*/

    @Test
    public void UserLoggedIn_ClickOnBookedAppointments(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.doctor_appointments.performClick();
        });
    }

    /* @Test
    public void UserLoggedIn_ClickOnPatientRecords(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.p.performClick();
        });
    } */

    /*@Test
    public void UserLoggedIn_ClickOnProfile(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.doctor_profile.performClick();
        });
    }*/
}