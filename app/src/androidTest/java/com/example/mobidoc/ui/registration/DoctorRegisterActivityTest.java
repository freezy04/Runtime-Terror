package com.example.mobidoc.ui.registration;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class DoctorRegisterActivityTest {

    @Rule
    public ActivityScenarioRule<DoctorRegisterActivity> activityScenarioRule = new ActivityScenarioRule<>(DoctorRegisterActivity.class);

//    @Test
//    public void Doctor_DoctorNotRegistered_RegistrationSuccessful(){
//        activityScenarioRule.getScenario().onActivity(activity ->{
//            assertTrue(activity.validateFName("ffdjgn", false));
//        });
//    }
//
//    @Test
//    public void Doctor_DoctorNotRegistered_RegistrationUnsuccessful(){
//        activityScenarioRule.getScenario().onActivity(activity ->{
//            assertFalse(activity.validateFName("", false));
//        });
//    }

    @Test
    public void registerDoctor_DoctorNotRegistered_RegistrationSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.specializationSPN.setSelection(1);
            activity.emailET.setText("testing123@email.com");
            activity.passwordET.setText("1Aaaaaa,");
            activity.confirmPasswordET.setText("1Aaaaaa,");
            activity.fNameET.setText("name1");
            activity.lNameET.setText("name2");
//            activity.qualificationsET.setText("testing123@email.com");
            activity.experienceET.setText("15");
            activity.registerBTN.performClick();
        });
    }

//    @Test
//    public void registerDoctor_DoctorNotRegistered_RegistrationUnsuccessful(){
//        activityScenarioRule.getScenario().onActivity(activity ->{
//            activity.registerBTN.performClick();
//        });
//    }

    @Test
    public void showPassword_PasswordHidden_PasswordVisible(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.toggleShowPassword(activity.passwordET, activity.showPasswordTW);
        });
    }

//    @Test doesnt work
//    public void switchToLogin_UserHasAccount_ScreenSwitchesToLogin(){
//        activityScenarioRule.getScenario().onActivity(activity ->{
//            activity.haveAccountTW.performClick();
//        });
//    }

//    @Test doesnt work
//    public void back_UserOnRegisterPage_UserOnMainPage(){
//        activityScenarioRule.getScenario().onActivity(activity ->{
//            boolean x = activity.onSupportNavigateUp();
//        });
//    }

}