package com.example.mobidoc.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobidoc.R;
import com.example.mobidoc.ui.dashboards.Doctor_Dashboard;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
import com.example.mobidoc.ui.login.Login;
import com.example.mobidoc.utils.Utilities;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread splash = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000/4);
                    RememberMe();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        splash.start();
        getSupportActionBar().hide(); // hiding ActionBar  on splash Screen

    }
    public void RememberMe(){
        String User_key = Paper.book().read(Utilities.USER_KEY);
        String Doctor = Paper.book().read(Utilities.Doctor);

        if (User_key != null) {

            if (Doctor != null) {
                //user is a doctor
                startActivity(new Intent(getApplicationContext(), Doctor_Dashboard.class));
                finish();
            }
            else {
                //user is a patient
                startActivity(new Intent(getApplicationContext(), Patient_Dashboard.class));
                finish();
            }
        }
        else {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}