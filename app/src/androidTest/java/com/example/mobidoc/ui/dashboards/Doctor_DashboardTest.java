package com.example.mobidoc.ui.dashboards;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mobidoc.R;
//import com.example.mobidoc.adapters.adapterPatientUpcoming;
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
//    @Test
//    public void testInUserAcceptanceCriteria(){
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            //activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED);
//           // Bundle b = new Bundle();
//           // activity.onCreate(b);
//            List<Appointment> userList = new ArrayList<>();
//            adapterPatientUpcoming A = new adapterPatientUpcoming(activity,userList);
//        });
//    }

   // @Test
   // public void getAllUsers_(){
   //     activityScenarioRule.getScenario().onActivity(activity -> {
     //       activity.getAllUsers("ucc6dJnoVKNu3mGdaC9DsICy9No1");
     //   });
   // }

    @Test
    public void clickAppointment() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.getAllUsers("mbcFUqwFCHV0HnrfX3vxUbXfsqs2");
            activity.AdapterPatient1.appointmentClicked("-Mayj6xd4HQOgG8xzhj8", "iXVat02y4laSyrT2vrdZVgHd6SD2",
                    "Nina Rose Tidy", "accepted");
        });
    }

    @Test
    public void clickHomeNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.nav_home2);
        });
    }

    @Test
    public void clickPatientRecordsNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar( R.id.nav_pateintrecords);
        });

    }

    @Test
    public void clickProfilenNavBarItem() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.nav_profile2);
        });

    }

    @Test
    public void clickPendingTest() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar(R.id.nav_pendingappointments2);
        });
    }
    @Test
    public void clickAcceptanceTestNavBarItem2() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.ClickOnNavBar( R.id.nav_acceptedappointments2);
        });
    }

}
