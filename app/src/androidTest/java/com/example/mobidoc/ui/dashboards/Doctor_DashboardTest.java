package com.example.mobidoc.ui.dashboards;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.adapters.adapterPatientUpcoming;
import com.example.mobidoc.models.Appointment;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class Doctor_DashboardTest {
    @Rule
    public ActivityScenarioRule<Doctor_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Doctor_Dashboard.class);


    @Before
    public void loginUser(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword("email","password");
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
}