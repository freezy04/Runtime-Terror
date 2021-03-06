package com.example.mobidoc.ui.Appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.AdapterAppointmentList;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.dashboards.Doctor_Dashboard;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
import com.example.mobidoc.ui.profiles.Doctor_ProfileActivity;
import com.example.mobidoc.ui.profiles.Patient_Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCompletedAppointmentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterAppointmentList AdapterPatient;
    List<Appointment> userPatient;
    FirebaseAuth firebaseAuth;
    private String userType;
    BottomNavigationView home_nav_doc, home_nav_pat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_appointment);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.users_recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewCompletedAppointmentsActivity.this));
        userPatient = new ArrayList<>();
        getAllAppointments();

        Intent viewCompletedApps = getIntent();
        userType = viewCompletedApps.getStringExtra("userType");

        NavBar();
        ClickNavBar();
    }

    public void NavBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Completed Appointments");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        if (userType.equals("Doctor")) {
            startActivity(new Intent(ViewCompletedAppointmentsActivity.this, Doctor_Dashboard.class));
            finish();
        }else {
            startActivity(new Intent(ViewCompletedAppointmentsActivity.this, Patient_Dashboard.class));
            finish();
        }
    }

    private void getAllAppointments() {
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Appointments");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                userPatient.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Appointment modelUsers = ds.getValue(Appointment.class);
                    modelUsers.setId(ds.getKey());
                    String userUID;
                    if (userType.equals("Doctor")) {
                        userUID = modelUsers.getDoctorUid();
                    } else{
                        userUID = modelUsers.getPatientUid();
                    }
                    if(userUID.equals(fUser.getUid()) && (modelUsers.getStatus().equals("completed")
                            /*|| modelUsers.getStatus().equals("rejected")*/)){
                        userPatient.add(modelUsers);

                    }

                    if(userUID.equals(fUser.getUid()) && modelUsers.getStatus().equals("rejected")){
                        userPatient.add(modelUsers);

                    }

                    AdapterPatient = new AdapterAppointmentList(ViewCompletedAppointmentsActivity.this, userPatient, userType);
                    recyclerView.setAdapter(AdapterPatient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ClickNavBar() {
        home_nav_doc = findViewById(R.id.bottom_navigation2);
        home_nav_pat = findViewById(R.id.bottom_navigation);
        if (userType.equals("Doctor")) {
            home_nav_pat.setVisibility(View.GONE);
            home_nav_doc.setSelectedItemId(R.id.nav_acceptedappointments2);
            home_nav_doc.setOnNavigationItemSelectedListener(item -> {
                Intent activity;
                switch (item.getItemId()) {

                    case R.id.nav_home2:
                    case R.id.nav_pateintrecords:
                        activity = new Intent(ViewCompletedAppointmentsActivity.this, Doctor_Dashboard.class);
                        startActivity(activity);
                        return true;

                    case R.id.nav_profile2:
                        activity = new Intent(ViewCompletedAppointmentsActivity.this, Doctor_ProfileActivity.class);
                        startActivity(activity);
                        return true;

                    case R.id.nav_pendingappointments2:
                        activity = new Intent(ViewCompletedAppointmentsActivity.this, DoctorViewPendingAppointmentsActivity.class);
                        startActivity(activity);
                        return true;

                    case R.id.nav_acceptedappointments2:

                        return true;

                }
                return true;
            });
        } else {
            home_nav_doc.setVisibility(View.GONE);
            home_nav_pat.setSelectedItemId(R.id.menu_appointments);
            home_nav_pat.setOnNavigationItemSelectedListener(item -> {
                Intent activity;
                switch (item.getItemId()) {

                    case R.id.menu_home:
                        activity = new Intent(ViewCompletedAppointmentsActivity.this, Patient_Dashboard.class);
                        startActivity(activity);
                        return true;
                    case R.id.menu_appointments:
                        return true;
                    case R.id.menu_consultation:
                        activity = new Intent(ViewCompletedAppointmentsActivity.this, Doctor_List.class);
                        startActivity(activity);
                        return true;
                    case R.id.menu_profile:
                        activity = new Intent(ViewCompletedAppointmentsActivity.this,Patient_Profile.class);
                        startActivity(activity);
                        return true;

                }
                return true;

            });
        }
    }


}