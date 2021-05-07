package com.example.mobidoc.ui.dashboards;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;

public class Doctor_DashboardTest {

    @Rule
    public ActivityScenarioRule<Doctor_Dashboard> activityScenarioRule = new ActivityScenarioRule<>(Doctor_Dashboard.class);

   /* @Before
    public void loginUser(){
        LoginUserFirebase("tf@gmail.com","Jazzman@1");
    }

    @Test
    public void UserLoggedIn_ClickOnProfile(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
           activity.doctor_profile.performClick();
        });
    }

    @Test
    public void UserLoggedIn_ClickOnPendingAppointments(){
        activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
            activity.doctor_pending_appointments.performClick();
        });
    }

   //  @Test
   // public void UserLoggedIn_ClickOnAcceptedAppointments(){
   //      activityScenarioRule.getScenario().moveToState(Lifecycle.State.CREATED).onActivity(activity -> {
   //          activity.doctor_accepted_appointments.performClick();
   //      });
   // }

    public void LoginUserFirebase(String mail,String pass){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(mail,pass);
    } */
}