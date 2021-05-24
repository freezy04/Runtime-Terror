package com.example.mobidoc.ui.dashboards;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.adapterPatientUpcoming;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.Appointment.DoctorViewAcceptedAppointmentsActivity;
import com.example.mobidoc.ui.Appointment.DoctorViewPendingAppointmentsActivity;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.profiles.Doctor_ProfileActivity;
import com.example.mobidoc.utils.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.paperdb.Paper;

//import com.example.mobidoc.adapters.adapterPatient;

public class Doctor_Dashboard extends AppCompatActivity {
    // LinearLayout doctor_profile,doctor_pending_appointments,doctor_completed_appointments,doctor_accepted_appointments;
    RecyclerView recyclerView;
    adapterPatientUpcoming AdapterPatient;
    List<Appointment> userPatient;
    FirebaseAuth firebaseAuth;
    BottomNavigationView home_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.users_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
                    if(modelUsers.getDoctorUid().equals(fUser.getUid()) && modelUsers.getStatus().equals("accepted")){
                        userPatient.add(modelUsers);
                    }
                    Collections.sort(userPatient, new Comparator<Appointment>() {
                        @Override
                        public int compare(Appointment o1, Appointment o2) {
                            return o1.getDate_for_appointment().compareTo(o2.getDate_for_appointment());
                        }
                    });
                    Collections.sort(userPatient, new Comparator<Appointment>() {
                        @Override
                        public int compare(Appointment o1, Appointment o2) {
                            if(o1.getDate_for_appointment().equals(o2.getDate_for_appointment())){
                                return o1.getTime_for_appointment().compareTo(o2.getTime_for_appointment());
                            }
                            return 0;
                        }
                    });
                    AdapterPatient = new adapterPatientUpcoming(getApplicationContext(), userPatient);
                    recyclerView.setAdapter(AdapterPatient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                startActivity(new Intent(Doctor_Dashboard.this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ClickNavBar() {

        home_nav = findViewById(R.id.bottom_navigation2);
        home_nav.setSelectedItemId(R.id.nav_home2);
        home_nav.setOnNavigationItemSelectedListener(item -> ClickOnNavBar(item.getItemId()));

    }

    public boolean ClickOnNavBar(int itemId){
            Intent activity;
            switch (itemId) {

                case R.id.nav_home2:
                    return true;
                case R.id.nav_pateintrecords:
                    return true;

                case R.id.nav_profile2:
                    activity = new Intent(Doctor_Dashboard.this, Doctor_ProfileActivity.class);
                    startActivity(activity);
                    return true;

                case R.id.nav_pendingappointments2:
                    activity = new Intent(Doctor_Dashboard.this, DoctorViewPendingAppointmentsActivity.class);
                    startActivity(activity);
                    return true;

                case R.id.nav_acceptedappointments2:
                    activity = new Intent(Doctor_Dashboard.this, DoctorViewAcceptedAppointmentsActivity.class);
                    startActivity(activity);

                    return true;

            }
            return true;

        }
    }



