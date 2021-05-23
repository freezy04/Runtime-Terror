package com.example.mobidoc.ui.dashboards;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.adapters.adapterPatientUpcoming;
import com.example.mobidoc.models.Appointment;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Patient_DashboardTest {
    @Rule
    public ActivityScenarioRule<Patient_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Patient_Dashboard.class);


    @Before
    public void LoginUser(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword("iv@email.com","Password1!");
    }
    @Test
    public void testInUserAcceptanceCriteria(){
        activityScenarioRule.getScenario().onActivity(activity -> {
//
        });
    }
}