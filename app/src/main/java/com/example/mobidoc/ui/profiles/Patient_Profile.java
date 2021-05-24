package com.example.mobidoc.ui.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Patient;
import com.example.mobidoc.ui.Appointment.Doctor_List;
import com.example.mobidoc.ui.Appointment.ViewCompletedAppointmentsActivity;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
import com.example.mobidoc.utils.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.paperdb.Paper;

public class Patient_Profile extends AppCompatActivity {
    TextView patient_names, patient_age, patient_med_hist, patient_allergies, patient_disease_hist, patient_email, patient_curr_med, patient_gender;
    Button editProfile;
    BottomNavigationView home_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__profile);

        patient_names = findViewById(R.id.patient_names);
        patient_age = findViewById(R.id.patient_age);
        patient_med_hist = findViewById(R.id.patient_med_hist);
        patient_allergies = findViewById(R.id.patient_allergies);
        patient_disease_hist = findViewById(R.id.patient_dis_hist);
        patient_email = findViewById(R.id.patient_email);
        patient_curr_med = findViewById(R.id.patient_curr_med);
        patient_gender = findViewById(R.id.patient_sex);

        NavBar();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        getPatientDetails(mAuth.getCurrentUser().getUid());
        ClickNavBar();

        editProfile = findViewById(R.id.edit_profile_button);
        editProfile.setOnClickListener(v -> {
            startActivity(new Intent(Patient_Profile.this, Edit_Patient_Profile.class));
        });


    }

    public void getPatientDetails(String uid) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();  // get instance of our Firebase Database
        DatabaseReference ref = db.getReference();             // retrieves the specific Realtime Database
        DatabaseReference user_ref = ref.child("Patients");     // specify user type


        user_ref.orderByKey().equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    Patient patient = ds.getValue(Patient.class);

                    patient_names.setText(patient.getFirst_name() + " " + patient.getLast_name());
                    patient_email.setText(patient.getEmail());
                    patient_age.setText(CheckNull(patient.getAge()));
                    patient_allergies.setText(CheckNull(patient.getAllergies()));
                    patient_curr_med.setText(CheckNull(patient.getCurrentMedication()));
                    patient_disease_hist.setText(CheckNull(patient.getDiseaseHistory()));
                    patient_med_hist.setText(CheckNull(patient.getMedicationHistory()));
                    patient_gender.setText(CheckNull(patient.getSex()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void NavBar() {
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Patient Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                Paper.book().delete(Utilities.USER_KEY);
                Paper.book().delete(Utilities.Doctor);
                startActivity(new Intent(Patient_Profile.this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        startActivity(new Intent(Patient_Profile.this, Patient_Dashboard.class));
        finish();
    }

    public String CheckNull(String s) {

        if (s == null || s.isEmpty()) {
            return "Not Applicable";
        }

        return s;
    }

    public void ClickNavBar() {
        home_nav = findViewById(R.id.bottom_navigation);
        home_nav.setSelectedItemId(R.id.menu_profile);
        home_nav.setOnNavigationItemSelectedListener(item -> onNavBarItemClicked(item.getItemId()));
    }

    public boolean onNavBarItemClicked(int itemId){

            Intent activity;
            switch (itemId) {

                case R.id.menu_home:
                    activity = new Intent(Patient_Profile.this, Patient_Dashboard.class);
                    startActivity(activity);
                    return true;
                case R.id.menu_appointments:
                    activity = new Intent(Patient_Profile.this, ViewCompletedAppointmentsActivity.class);
                    activity.putExtra("userType", "Patient");
                    startActivity(activity);
                    return true;
                case R.id.menu_consultation:
                    activity = new Intent(Patient_Profile.this, Doctor_List.class);
                    startActivity(activity);
                    return true;
                case R.id.menu_profile:
                    return true;

            }
            return true;

        }
}
