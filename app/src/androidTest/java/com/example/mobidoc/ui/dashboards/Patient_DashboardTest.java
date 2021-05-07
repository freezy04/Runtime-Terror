package com.example.mobidoc.ui.dashboards;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class Patient_DashboardTest {
    @Rule
    public ActivityScenarioRule<Patient_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Patient_Dashboard.class);

    @Test
    public void UserLoggedIn_ClickOnAppointmentList(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.AppointmentList.performClick();
        });
    }

    @Test
    public void UserLoggedIn_ClickOnBookAppointment(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.BookAppointment.performClick();
        });
    }

    @Test
    public void UserLoggedIn_ClickOnPatientProfile(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.Profile_page.performClick();
        });
    }
}