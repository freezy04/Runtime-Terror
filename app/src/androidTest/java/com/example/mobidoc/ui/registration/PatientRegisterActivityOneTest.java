package com.example.mobidoc.ui.registration;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

//test
public class PatientRegisterActivityOneTest {

    @Rule
    public ActivityScenarioRule<PatientRegisterActivityOne> activityScenarioRule = new ActivityScenarioRule<>(PatientRegisterActivityOne.class);

    @Test
    public void moveTo2_PatientNotRegistered_SuccessfulMove() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.emailET.setText("testing123@email.com");
            activity.passwordET.setText("1Aaaaaa,");
            activity.confirmPasswordET.setText("1Aaaaaa,");
            activity.fNameET.setText("name1");
            activity.lNameET.setText("name2");
            activity.ageET.setText("45");
            activity.sexET.setText("Male");
            activity.nextBTN.performClick();
        });
    }

    @Test
    public void moveTo2_PatientNotRegistered_UnsuccessfulMove() {
        activityScenarioRule.getScenario().onActivity(activity -> activity.nextBTN.performClick());
    }

    @Test
    public void moveTo2_PatientNotRegistered_UnsuccessfulMovePasswordMatch() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.passwordET.setText("Adsfsdfsd");
            activity.ageET.setText("a!");
            activity.nextBTN.performClick();
        });
    }

    @Test
    public void showPassword_PasswordHidden_PasswordVisible() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.showPasswordTW.setText(" ");
            activity.toggleShowPassword(activity.passwordET, activity.showPasswordTW);
        });
    }

    @Test
    public void showPassword_PasswordVisible_PasswordHidden() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.showPasswordTW.setText(".");
            activity.toggleShowPassword(activity.passwordET, activity.showPasswordTW);
        });
    }

    @Test
    public void switchToLogin_UserHasAccount_ScreenSwitchesToLogin() {
        activityScenarioRule.getScenario().onActivity(PatientRegisterActivityOne::switchToLogin);
    }

}