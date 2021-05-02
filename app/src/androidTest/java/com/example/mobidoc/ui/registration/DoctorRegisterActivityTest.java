package com.example.mobidoc.ui.registration;

import androidx.test.ext.junit.rules.ActivityScenarioRule;


import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoctorRegisterActivityTest {

    @Rule
    public ActivityScenarioRule<DoctorRegisterActivity> activityScenarioRule = new ActivityScenarioRule<>(DoctorRegisterActivity.class);

    @Test
    public void Doctor_DoctorNotRegistered_RegistrationSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            assertTrue(activity.validateFName("ffdjgn", false));
        });
    }

    @Test
    public void Doctor_DoctorNotRegistered_RegistrationUnsuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            assertFalse(activity.validateFName("", false));
        });
    }

    @Test
    public void registerDoctor_DoctorNotRegistered_RegistrationSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.registerBTN.performClick();
        });
    }

}