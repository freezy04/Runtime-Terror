package com.example.mobidoc.ui.Appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.AdapterAppointmentList;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientViewAppointmentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterAppointmentList AdapterPatient;
    List<Appointment> userPatient;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_appointment);
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.users_recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PatientViewAppointmentActivity.this));
        userPatient = new ArrayList<>();
        getAllAppointments();

        NavBar();

    }

    public void NavBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Your Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        startActivity(new Intent(PatientViewAppointmentActivity.this, Patient_Dashboard.class));
        finish();
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
                    if(modelUsers.getPatientUid().equals(fUser.getUid())){
                        userPatient.add(modelUsers);
                    }

                    AdapterPatient = new AdapterAppointmentList(PatientViewAppointmentActivity.this, userPatient);
                    recyclerView.setAdapter(AdapterPatient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}