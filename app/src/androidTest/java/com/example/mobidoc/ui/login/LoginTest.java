package com.example.mobidoc.ui.login;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void loginUser_UserDoctorNotLoggedIn_isSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("drken@gmail.com");
            activity.User_password.setText("Jazzman@1");
            activity.login_Button.performClick();
        });
    }

    @Test
    public void loginUser_UserDoctorNotLoggedIn_isUnsuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("drken@gmail.com");
            activity.User_password.setText("MyNameJeff");
            activity.login_Button.performClick();
        });
    }

    @Test
    public void loginUser_UserPatientNotLoggedIn_isUnsuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("sample_user@gmail.com");
            activity.User_password.setText("Meeso");
            activity.login_Button.performClick();
        });
    }

    @Test
    public void loginUser_UserPatientNotLoggedIn_isSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("sample_user@gmail.com");
            activity.User_password.setText("Jazzman@1");
            activity.login_Button.performClick();
        });
    }

    @Test
    public void CheckUserEmail_UserNotLoggedIn_isSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("soap@gmail.com");
            activity.User_password.setText("Jazzman@1");
            assertEquals(true, activity.ValidateDetails(activity.User_email,activity.User_password));
        });
    }

    @Test
    public void CheckUserEmail_UserNotLoggedIn_isUnSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("bobsaunty");
            activity.User_password.setText("Jazzman@1");
            assertEquals(false, activity.ValidateDetails(activity.User_email,activity.User_password));
        });
    }

    @Test
    public void CheckUserPassword_UserNotLoggedIn_isSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("gradea@yahoo.com");
            activity.User_password.setText("Jazzman@1");
            assertEquals(true, activity.ValidateDetails(activity.User_email,activity.User_password));
        });
    }

    @Test
    public void CheckUserPassword_UserNotLoggedIn_isUnSuccessful(){
        activityScenarioRule.getScenario().onActivity(activity ->{
            activity.User_email.setText("gradea@yahoo.com");
            activity.User_password.setText(" ");
            assertEquals(false, activity.ValidateDetails(activity.User_email,activity.User_password));
        });
    }
}