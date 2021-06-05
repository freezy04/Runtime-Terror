package com.example.mobidoc.ui.Appointment;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DoctorViewPendingAppointmentTest {


    @Rule
    public ActivityScenarioRule<Doctor_List> activityScenarioRule = new ActivityScenarioRule<>(Doctor_List.class);

    @Before
    public void loginUser() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword("test2@gmail.com", "Freezy@04");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Test
    public void onBackPressed() {

        activityScenarioRule.getScenario().onActivity(activity -> {

            activity.onSupportNavigateUp();
            activity.onBackPressed();

        });
    }

    @Test
    public void getAllUsers_Doctor_list() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.getAllUsers("2e0Igj5IEDQOfEIcX8AXc6kPCrI3");
        });
    }

    @Test
    public void clickHomeNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.menu_home);
        });
    }

    @Test
    public void clickAppointmentNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.menu_appointments);
        });

    }

    @Test
    public void clickConsultationNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.menu_consultation);
        });

    }

    @Test
    public void clickProfileNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.menu_profile);
        });
    }
}
