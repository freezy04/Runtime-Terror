package com.example.mobidoc.ui.Appointment;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.adapterAppointment;
import com.example.mobidoc.models.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Doctor_List_Test {

    @Rule
    public ActivityScenarioRule<Doctor_List> activityScenarioRule = new ActivityScenarioRule<>(Doctor_List.class);

    @Before
    public void loginUser() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword("bm@gmail.com", "Jazzman@1");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Test
    public void test_in_user_acceptance_criteria() {
        //   activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED);

        activityScenarioRule.getScenario().onActivity(activity -> {
            //activity.onCreate(new Bundle());
            List<Doctor> doctors = new ArrayList<>();

            adapterAppointment adapterPatient = new adapterAppointment(activity, doctors);
            adapterPatient.holderItemClicked("77gDrYtgw8RPF7tqfI9TBWzXCGo1", "sadasdf");
        });
    }

    @Test
    public void getAllUsers(){
        activityScenarioRule.getScenario().onActivity(activity -> {
           activity.getAllUsers("77gDrYtgw8RPF7tqfI9TBWzXCGo1");
        });
    }

    @Test
    public void searchForUsers(){
        activityScenarioRule.getScenario().onActivity(activity -> {
           activity.searchForUsers("77gDrYtgw8RPF7tqfI9TBWzXCGo1", "");
        });
    }

    @Test
    public void NavBar_(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.NavBar();
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