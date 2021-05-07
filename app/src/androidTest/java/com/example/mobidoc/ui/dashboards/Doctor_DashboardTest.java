package com.example.mobidoc.ui.dashboards;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class Doctor_DashboardTest {

    @Rule
    public ActivityScenarioRule<Doctor_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Doctor_Dashboard.class);

    @Test
    public void UserLoggedIn_ClickOnProfile(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
           activity.doctor_profile.performClick();
        });
    }

    @Test
    public void UserLoggedIn_ClickOnPendingAppointments(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.doctor_pending_appointments.performClick();
        });
    }

    /* @Test
    public void UserLoggedIn_ClickOnAcceptedAppointments(){
         activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
             activity.doctor_accepted_appointments.performClick();
         });
    } */

}