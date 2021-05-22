package com.example.mobidoc.ui.Appointment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.AdapterPatient;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.dashboards.Doctor_Dashboard;
import com.example.mobidoc.ui.profiles.Doctor_ProfileActivity;
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
import java.util.Objects;

public class DoctorViewPendingAppointmentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    com.example.mobidoc.adapters.AdapterPatient AdapterPatient;
    List<Appointment> userPatient;
    FirebaseAuth firebaseAuth;
    BottomNavigationView home_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appointments);
        NavBar();
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.users_recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorViewPendingAppointmentsActivity.this));
        userPatient = new ArrayList<>();
        getAllUsers();
        ClickNavBar();
    }



    private void getAllUsers() {
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Appointments");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                userPatient.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Appointment  modelUsers = ds.getValue(Appointment.class);
                    modelUsers.setId(ds.getKey());
                    if(modelUsers.getDoctorUid().equals(fUser.getUid()) && modelUsers.getStatus().equals("pending")){
                        userPatient.add(modelUsers);
                    }

                    AdapterPatient = new AdapterPatient(DoctorViewPendingAppointmentsActivity.this, userPatient);
                    recyclerView.setAdapter(AdapterPatient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void NavBar(){
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Pending Appointments");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        startActivity(new Intent(DoctorViewPendingAppointmentsActivity.this, Doctor_Dashboard.class));
        finish();
    }
    public void ClickNavBar(){
        // BottomNavigationView home_nav;
        home_nav = findViewById(R.id.bottom_navigation2);
        home_nav.setSelectedItemId(R.id.nav_pendingappointments2);
        home_nav.setOnNavigationItemSelectedListener(item -> {
            Intent activity;
            switch(item.getItemId()){

                case R.id.nav_pendingappointments2:
                    activity = new Intent(DoctorViewPendingAppointmentsActivity.this, DoctorViewPendingAppointmentsActivity.class);
                    startActivity(activity);
                    return true;
                case R.id.nav_home2:
                    activity = new Intent(DoctorViewPendingAppointmentsActivity.this,Doctor_Dashboard.class);
                    startActivity(activity);
                    return true;
                case R.id.nav_pateintrecords:
                    activity = new Intent(DoctorViewPendingAppointmentsActivity.this,Doctor_Dashboard.class);
                    startActivity(activity);
                    return true;

                case R.id.nav_profile2:
                    activity = new Intent(DoctorViewPendingAppointmentsActivity.this, Doctor_ProfileActivity.class);
                    startActivity(activity);
                    return true;

                case R.id.nav_acceptedappointments2:
                    activity = new Intent(DoctorViewPendingAppointmentsActivity.this, DoctorViewAcceptedAppointmentsActivity.class);

                    activity.putExtra("userType", "Doctor");
                    startActivity(activity);

                    //  activity = new Intent(Doctor_Dashboard.this, DoctorViewAcceptedAppointmentsActivity.class);
                    // startActivity(activity);
                    return true;



            }
            return true;

        });
    }

}