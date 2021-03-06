package com.example.mobidoc.ui;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class SplashActivityTest {
   @Rule
   public ActivityScenarioRule<SplashActivity> activityScenarioRule = new ActivityScenarioRule<>(SplashActivity.class);

   @Test
   public void testInUserAcceptanceCriteria(){
       activityScenarioRule.getScenario().onActivity(activity -> {

       });
   }
   
    @Test
    public void CheckRememberMeSendUserDoctor(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.CheckSendRememberUser("Mike");
        });
    }

    @Test
    public void CheckRememberMeSendUserPatient(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.CheckSendRememberUser(null);
        });
    }
}
