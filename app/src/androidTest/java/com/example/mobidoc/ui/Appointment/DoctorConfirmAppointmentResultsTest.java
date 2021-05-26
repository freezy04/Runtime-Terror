package com.example.mobidoc.ui.Appointment;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DoctorConfirmAppointmentResultsTest {

    @Rule
    public ActivityScenarioRule<DoctorConfirmAppointmentResults> activityScenarioRule = new ActivityScenarioRule<>(DoctorConfirmAppointmentResults.class);

    @Before
    public void loginUser() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword("Test2@gmail.com", "Freezy@04");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Test
    public void updateDetails_UpdatesConfirmed_UpdateUnsuccessful(){
        activityScenarioRule.getScenario().onActivity(activity -> activity.confirmBTN.performClick());
    }

    @Test
    public void initializationActivityTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
                activity.initializeActivity_();
    });
    }
//  consult
//    @Test
//    public void ConfirmUpdateTest(){
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.confirmUpdates_("panado","50","1 per day");
//        });
//    }

    @Test
    public void updateDetails_Unsecceful(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.updateDetails_();
        });
    }

    @Test
    public void onSupportNavigateUpTest(){
        activityScenarioRule.getScenario().onActivity(activity -> {
           activity.onSupportNavigateUp();
        });
    }

}