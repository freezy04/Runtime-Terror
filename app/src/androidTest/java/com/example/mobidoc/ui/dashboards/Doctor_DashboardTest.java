package com.example.mobidoc.ui.dashboards;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.adapterPatientUpcoming;
import com.example.mobidoc.models.Appointment;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Doctor_DashboardTest {
    @Rule
    public ActivityScenarioRule<Doctor_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Doctor_Dashboard.class);


    @Before
    public void loginUser(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword("rr@email.com","Password1!");
    }
    @Test
    public void testInUserAcceptanceCriteria(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            //activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED);
           // Bundle b = new Bundle();
           // activity.onCreate(b);
            List<Appointment> userList = new ArrayList<>();
            adapterPatientUpcoming A = new adapterPatientUpcoming(activity,userList);
        });
    }

    @Test
    public void getAllUsers(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.getAllUsers("2e0Igj5IEDQOfEIcX8AXc6kPCrI3");
        });
    }

    @Test
    public void clickHomeNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_home);
        });
    }

    @Test
    public void clickAppointmentNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_appointments);
        });

    }

    @Test
    public void clickConsultationNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_consultation);
        });

    }

    @Test
    public void clickProfileNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.menu_profile);
        });
    }

}