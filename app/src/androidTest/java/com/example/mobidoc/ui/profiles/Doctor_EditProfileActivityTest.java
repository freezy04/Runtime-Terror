package com.example.mobidoc.ui.profiles;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Doctor_EditProfileActivityTest {
    @Rule
    public ActivityScenarioRule<Doctor_EditProfileActivity> activityScenarioRule = new ActivityScenarioRule<>(Doctor_EditProfileActivity.class);

    //test
    @Test
    public void GoToProfile_UserLoggedIn_onClickGoToProfile(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity ->{
            activity.Profile_page.performClick();
        });
    }

    @Test
    public void UpdateDetailsCheckChangedData_UserLoggedIn_isSuccessful(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity ->{
            assertTrue(activity.isEdited("some string"));
        });
    }

    @Test
    public void UpdateDetailsCheckChangedData_UserLoggedIn_isUnSuccessful(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity ->{
            assertFalse(activity.isEdited(""));
        });
    }

    @Test
    public void updateDetails() {
        activityScenarioRule.getScenario().onActivity(activity -> {
           activity.updateDetails("JAKLyVL37YV3PM0HZoUa8EhFWth2", "first_name", "Michael");
        });
    }
}