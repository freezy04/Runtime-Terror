package com.example.mobidoc.ui.dashboards;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;

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

}