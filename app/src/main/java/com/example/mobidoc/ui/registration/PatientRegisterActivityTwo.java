package com.example.mobidoc.ui.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.ui.login.Login_activity;
import com.example.mobidoc.ui.No_Internet;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
import com.example.mobidoc.R;
import com.example.mobidoc.models.Patient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import io.paperdb.Paper;

public class PatientRegisterActivityTwo extends AppCompatActivity {

    private EditText diseaseHistoryET, medicationHistoryET, allergiesET;
    private String email, password, fName, lName, age, sex;
    private TextView haveAccountTW;
    private Button registerBTN, backBTN;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register_two);

        networkAvailabilityCheck();
        initializeActivity();

        //if user already has an account switch to login screen
        haveAccountTW.setOnClickListener(v -> {
            startActivity(new Intent(PatientRegisterActivityTwo.this, Login_activity.class));
            finish();
        });

        //validate details
        registerBTN.setOnClickListener(v -> {
            if (validateDetails(diseaseHistoryET, medicationHistoryET, true)) {
                // Check if user is signed in (non-null) and update UI accordingly.
                String diseaseHistory = diseaseHistoryET.getText().toString().trim();
                String medicationHistory = medicationHistoryET.getText().toString().trim();
                String allergies = allergiesET.getText().toString().trim();
                Patient pat = new Patient(fName, lName, "Patient", email, age, sex, diseaseHistory, medicationHistory, allergies);
                System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.err.println(pat.getEmail() + " " + password);

                registerUser(pat, password);
            }
        });

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initializeActivity() {
        //set up action bar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Patient Account Registration");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Paper.init(this); //    @ Dylan 2179115 added this Code to store information locally

        //initialize swing elements
        diseaseHistoryET = findViewById(R.id.diseaseHistoryET);
        medicationHistoryET = findViewById(R.id.medicationHistoryET);
        allergiesET = findViewById(R.id.allergiesET);
        registerBTN = findViewById(R.id.registerBTN);
        backBTN = findViewById(R.id.backBTN);
        haveAccountTW = findViewById(R.id.haveAccountTW);

        Intent patientRegistrationStepTwo = getIntent();
        email = patientRegistrationStepTwo.getStringExtra("email");
        password = patientRegistrationStepTwo.getStringExtra("password");
        fName = patientRegistrationStepTwo.getStringExtra("fName");
        lName = patientRegistrationStepTwo.getStringExtra("lName");
        age = patientRegistrationStepTwo.getStringExtra("age");
        sex = patientRegistrationStepTwo.getStringExtra("sex");

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Patient...");
    }

    private void networkAvailabilityCheck() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            startActivity(new Intent(PatientRegisterActivityTwo.this, No_Internet.class));
        }
    }

    private boolean fieldNotNull(EditText et, boolean displayErrors) {//checks if personal information fields are empty, if so displays the appropriate error(s)
        if (et.getText().toString().trim().isEmpty()) {
            if (displayErrors) {
                et.setError("Cannot be empty");
            }
            return false;
        }
        return true;
    }

    private boolean validateDetails(EditText diseaseHistoryET, EditText medicationHistoryET, boolean displayErrors) {
        boolean dHistory_valid = fieldNotNull(diseaseHistoryET, displayErrors);
        boolean mHistory_valid = fieldNotNull(medicationHistoryET, displayErrors);
        return dHistory_valid && mHistory_valid;
    }

    private void registerUser(Patient pat, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(pat.getEmail(), password)
                .addOnCompleteListener(PatientRegisterActivityTwo.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        progressDialog.dismiss();
                        FirebaseUser user = mAuth.getCurrentUser();

                        Objects.requireNonNull(user).sendEmailVerification()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(PatientRegisterActivityTwo.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(PatientRegisterActivityTwo.this, "Verification email failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference(pat.getUser_type() + "s");
                        pat.setUid(user.getUid());
                        reference.child(pat.getUid()).setValue(pat);
                        Toast.makeText(PatientRegisterActivityTwo.this, "Registered...\n" + user.getEmail(), Toast.LENGTH_SHORT).show();

                        /* ---- Dylan 2179115 added this code ----*/
                        startActivity(new Intent(PatientRegisterActivityTwo.this, Patient_Dashboard.class));
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        progressDialog.dismiss();
                        Toast.makeText(PatientRegisterActivityTwo.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(PatientRegisterActivityTwo.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}

