package com.example.mobidoc.ui.registration;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class DoctorRegisterActivityTest {

    @Rule
    public ActivityScenarioRule<DoctorRegisterActivity> asr = new ActivityScenarioRule<>(DoctorRegisterActivity.class);

    @Test
    public void registerDoctor_DoctorNotRegistered_RegistrationFailed() {
        asr.getScenario().onActivity(activity -> {
            activity.emailET.setText("bad email");
            activity.registerBTN.performClick();
        });
    }

}