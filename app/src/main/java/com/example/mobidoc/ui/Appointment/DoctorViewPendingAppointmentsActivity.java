package com.example.mobidoc.ui.Appointment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.adapterPatient;
import com.example.mobidoc.models.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorViewPendingAppointmentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    adapterPatient AdapterPatient;
    List<Appointment> userPatient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_appointments);

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.users_recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorViewPendingAppointmentsActivity.this));
        userPatient = new ArrayList<>();
        getAllUsers();
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

                    AdapterPatient = new adapterPatient(DoctorViewPendingAppointmentsActivity.this, userPatient);
                    recyclerView.setAdapter(AdapterPatient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}