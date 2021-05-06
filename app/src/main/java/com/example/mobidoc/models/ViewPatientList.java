package com.example.mobidoc.models;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobidoc.R;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;


import com.example.mobidoc.adapters.adapterAppointment;
import com.example.mobidoc.adapters.adapterPatient;
import com.example.mobidoc.models.Doctor;
import com.example.mobidoc.ui.Appointment.Doctor_List;
import com.example.mobidoc.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPatientList extends AppCompatActivity {
    RecyclerView recyclerView;
    adapterPatient AdapterPatient;
    List<BookedAppointmentList> userPatient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_list);

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.users_recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewPatientList.this));
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
                    BookedAppointmentList  modelUsers = ds.getValue(BookedAppointmentList.class);

                    if(modelUsers.getDoctorUid().equals(fUser.getUid())){
                        userPatient.add(modelUsers);
                    }

                    AdapterPatient = new adapterPatient(ViewPatientList.this, userPatient);
                    recyclerView.setAdapter(AdapterPatient);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}