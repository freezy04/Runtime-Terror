package com.example.mobidoc.ui.dashboards;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class Patient_DashboardTest {
    @Rule
    public ActivityScenarioRule<Patient_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Patient_Dashboard.class);


    @Before
    public void LoginUser(){
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword("bm@gmail.com","Jazzman@1");
                    synchronized(this){
                        wait(3000);
                    }
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    @Test
    public void UserLoggedIn_ClickOnAppointmentList(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.AppointmentList.performClick();
        });
    }


    @Test
    public void UserLoggedIn_ClickOnBookAppointment(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.BookAppointment.performClick();
        });
    }

    @Test
    public void UserLoggedIn_ClickOnPatientProfile(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.Profile_page.performClick();
        });
    }

}