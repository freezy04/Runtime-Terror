package com.example.mobidoc.ui.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Patient;
import com.example.mobidoc.ui.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import io.paperdb.Paper;

public class PatientRegisterActivityTwo extends AppCompatActivity {

    //test
    public EditText diseaseHistoryET, medicationHistoryET, allergiesET;
    public String email, password, fName, lName, age, sex;
    public TextView haveAccountTW;
    public Button registerBTN;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register_two);

        initializeActivity();

        //if user already has an account switch to login screen
        haveAccountTW.setOnClickListener(v -> switchToLogin());

        //validate details
        registerBTN.setOnClickListener(v -> registerUser());
    }

    public void switchToLogin(){
        startActivity(new Intent(PatientRegisterActivityTwo.this, Login.class));
        finish();
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

    private boolean fieldNotNull(EditText et) {//checks if personal information fields are empty, if so displays the appropriate error(s)
        if (et.getText().toString().trim().isEmpty()) {
            et.setError("Cannot be empty");
            return false;
        }
        return true;
    }

    private boolean validateDetails(EditText diseaseHistoryET, EditText medicationHistoryET) {
        boolean dHistory_valid = fieldNotNull(diseaseHistoryET);
        boolean mHistory_valid = fieldNotNull(medicationHistoryET);
        return dHistory_valid && mHistory_valid;
    }

    private void addUserToDB(Patient pat){
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
        Intent i = new Intent(PatientRegisterActivityTwo.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        Toast.makeText(PatientRegisterActivityTwo.this, "Please Login now", Toast.LENGTH_LONG).show();
    }

    private void registerUser() {
        if (!validateDetails(diseaseHistoryET, medicationHistoryET)) {
            return;
        }
        String diseaseHistory = diseaseHistoryET.getText().toString().trim();
        String medicationHistory = medicationHistoryET.getText().toString().trim();
        String allergies = allergiesET.getText().toString().trim();
        Patient pat = new Patient(fName, lName, "Patient", email, age, sex, diseaseHistory, medicationHistory, allergies, "");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(pat.getEmail(), password)
                .addOnCompleteListener(PatientRegisterActivityTwo.this, task -> {
                    if (task.isSuccessful()) {
                        addUserToDB(pat);
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

