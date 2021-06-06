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
    public ActivityScenarioRule<DoctorViewPendingAppointmentsActivity> activityScenarioRule = new ActivityScenarioRule<>(DoctorViewPendingAppointmentsActivity.class);

    @Before
    public void loginUser() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword("test2@gmail.com", "Freezy@04");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Test
    public void clickPendingAppointment() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.clickAdapterAppointment("-Mayj6xd4HQOgG8xzhj8", "iXVat02y4laSyrT2vrdZVgHd6SD2",
                    "Nina Rose Tidy", "pending");
        });
    }

    @Test
    public void RejectAppointment() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.rejectApp("-Mayj6xd4HQOgG8xzhj8");
        });
    }

    @Test
    public void AcceptAppointment() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.acceptApp("-Mayj6xd4HQOgG8xzhj8");
        });
    }

    @Test
    public void onBackPressed() {

        activityScenarioRule.getScenario().onActivity(activity -> {

            activity.onSupportNavigateUp();
            activity.onBackPressed();
            

        });
    }

    @Test
    public void getAllUsers() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.getAllUsers("2e0Igj5IEDQOfEIcX8AXc6kPCrI3");
        });
    }

    @Test
    public void clickHomeNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.nav_home2);
        });
    }

    @Test
    public void clickAppointmentNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.nav_pendingappointments2);
        });

    }

    @Test
    public void nav_profileNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.nav_profile2);
        });

    }
    
    @Test
    public void clickPaNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.nav_pateintrecords);
        });
    }

    @Test
    public void clicknav_acceptedappointments_NavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.onNavBarItemClicked_(R.id.nav_acceptedappointments2);
        });
    }
}
