package com.example.mobidoc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.ui.login.Login_activity;
import com.example.mobidoc.ui.registration.DoctorRegisterActivity;
import com.example.mobidoc.ui.registration.PatientRegisterActivityOne;

public class MainActivity extends AppCompatActivity {

    Button mDocRegisterBTN, mPatRegisterBTN, mLoginBYN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDocRegisterBTN = findViewById(R.id.doc_register_btn);
        mPatRegisterBTN = findViewById(R.id.pat_register_btn);
        mLoginBYN = findViewById(R.id.login_btn);

        mDocRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DoctorRegisterActivity.class));
            }
        });
        mPatRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PatientRegisterActivityOne.class));
            }
        });
        mLoginBYN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, Login_activity.class));
            }
        });
    }
}
