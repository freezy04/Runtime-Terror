package com.example.mobidoc.ui.registration;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

//test
public class PatientRegisterActivityTwoTest {

    @Rule
    public ActivityScenarioRule<PatientRegisterActivityTwo> activityScenarioRule = new ActivityScenarioRule<>(PatientRegisterActivityTwo.class);

//    @Test
//    public void registerPatient_PatientNotRegistered_SuccessfulRegistration() {
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.email = "testing123@email.com";
//            activity.password = "1Aaaaaa,";
//            activity.fName = "name1";
//            activity.lName = "name2";
//            activity.age = "45";
//            activity.sex = "Male";
//            activity.diseaseHistoryET.setText("Test");
//            activity.medicationHistoryET.setText("Test");
//            activity.registerBTN.performClick();
//        });
//    }

    @Test
    public void registerPatient_PatientNotRegistered_UnsuccessfulRegistration() {
        activityScenarioRule.getScenario().onActivity(activity -> activity.registerBTN.performClick());
    }

    @Test
    public void switchToLogin_UserHasAccount_ScreenSwitchesToLogin() {
        activityScenarioRule.getScenario().onActivity(PatientRegisterActivityTwo::switchToLogin);
    }

}