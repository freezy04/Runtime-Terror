package com.example.mobidoc.ui.profiles;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

public class Edit_Patient_ProfileTest {

    @Rule
    public ActivityScenarioRule<Edit_Patient_Profile> activityScenarioRule = new ActivityScenarioRule<>(Edit_Patient_Profile.class);


    @Test
    public void GoToProfile_UserLoggedIn_onClickGoToProfile(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity ->{
            activity.Profile.performClick();
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


}