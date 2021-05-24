package com.example.mobidoc.ui.Appointment;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class DoctorConfirmAppointmentResultsTest {

    @Rule
    public ActivityScenarioRule<DoctorConfirmAppointmentResults> activityScenarioRule = new ActivityScenarioRule<>(DoctorConfirmAppointmentResults.class);

    @Test
    public void updateDetails_UpdatesConfirmed_UpdateUnsuccessful(){
        activityScenarioRule.getScenario().onActivity(activity -> activity.confirmBTN.performClick());
    }

} 