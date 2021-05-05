package com.example.mobidoc.ui.dashboards;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;

public class Patient_DashboardTest {
    @Rule
    public ActivityScenarioRule<Patient_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Patient_Dashboard.class);

    /*@Test
    public void UserLoggedIn_onClickViewConsultations(){
        activityScenarioRule.getScenario().onActivity(activity -> {
           activity.BookAppointment.performClick();
        });
    }*/
}