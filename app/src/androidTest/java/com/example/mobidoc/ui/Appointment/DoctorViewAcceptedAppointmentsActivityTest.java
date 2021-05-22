package com.example.mobidoc.ui.Appointment;

import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.adapters.AdapterPatient;
import com.example.mobidoc.models.Appointment;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DoctorViewAcceptedAppointmentsActivityTest{
    @Rule
    public ActivityScenarioRule<DoctorViewAcceptedAppointmentsActivity> activityScenarioRule = new ActivityScenarioRule<>(DoctorViewAcceptedAppointmentsActivity.class);
    @Before
    public void loginUser(){

        FirebaseAuth.getInstance().signInWithEmailAndPassword("correctemail","correctpasswor");
    }
    @Test
    public void test_in_user_acceptance_criteria(){
        //   activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED);

        activityScenarioRule.getScenario().onActivity(activity ->{
            //activity.onCreate(new Bundle());
            List<Appointment> patients = new ArrayList<>();

            AdapterPatient adapterPatient = new AdapterPatient(activity, patients);
        });
    }
}