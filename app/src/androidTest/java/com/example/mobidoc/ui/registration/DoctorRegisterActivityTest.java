package com.example.mobidoc.ui.registration;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

//test
public class DoctorRegisterActivityTest {

    @Rule
    public ActivityScenarioRule<DoctorRegisterActivity> activityScenarioRule = new ActivityScenarioRule<>(DoctorRegisterActivity.class);

    @Test
    public void registerDoctor_DoctorNotRegistered_RegistrationSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.specializationSPN.setSelection(1);
            activity.emailET.setText("testing123@email.com");
            activity.passwordET.setText("1Aaaaaa,");
            activity.confirmPasswordET.setText("1Aaaaaa,");
            activity.fNameET.setText("name1");
            activity.lNameET.setText("name2");
            activity.qualificationsET.setText("testing123@email.com");
            activity.experienceET.setText("15");
            activity.registerBTN.performClick();
        });
    }

    @Test
    public void registerDoctor_DoctorNotRegistered_RegistrationUnsuccessful(){
        activityScenarioRule.getScenario().onActivity(activity -> activity.registerBTN.performClick());
    }

    @Test
    public void registerDoctor_DoctorNotRegistered_RegistrationUnsuccessfulPasswordMatch(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.passwordET.setText("Adsfsdfsd");
            activity.experienceET.setText("a!");
            activity.registerBTN.performClick();
        });
    }

    @Test
    public void showPassword_PasswordHidden_PasswordVisible(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.showPasswordTW.setText(" ");
            activity.toggleShowPassword(activity.passwordET, activity.showPasswordTW);
        });
    }

    @Test
    public void showPassword_PasswordVisible_PasswordHidden(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.showPasswordTW.setText(".");
            activity.toggleShowPassword(activity.passwordET, activity.showPasswordTW);
        });
    }

    @Test
    public void switchToLogin_UserHasAccount_ScreenSwitchesToLogin(){
        activityScenarioRule.getScenario().onActivity(DoctorRegisterActivity::switchToLogin);
    }

}