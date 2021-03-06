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
import com.example.mobidoc.adapters.AdapterPatientAccepted;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.Appointment.Doctor_List;
import com.example.mobidoc.ui.Appointment.ViewCompletedAppointmentsActivity;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.profiles.Patient_Profile;
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


public class Patient_Dashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterPatientAccepted AdapterPatient;
    List<Appointment> userDoctor;
    FirebaseAuth firebaseAuth;
    BottomNavigationView home_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.users_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Patient_Dashboard.this));
        userDoctor = new ArrayList<>();
        getAllUsers();
        ClickNavBar();

    }

    private void getAllUsers() {

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fUser != null){
            getAllUsers(fUser.getUid());
        }

    }

    public void getAllUsers(String uid){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Appointments");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                userDoctor.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Appointment modelUsers = ds.getValue(Appointment.class);

                    if(modelUsers.getPatientUid().equals(uid) && modelUsers.getStatus().equals("accepted")){
                        userDoctor.add(modelUsers);
                    }

                    Collections.sort(userDoctor, new Comparator<Appointment>() {
                        @Override
                        public int compare(Appointment o1, Appointment o2) {
                            return o1.getDate_for_appointment().compareTo(o2.getDate_for_appointment());
                        }
                    });
                    Collections.sort(userDoctor, new Comparator<Appointment>() {
                        @Override
                        public int compare(Appointment o1, Appointment o2) {
                            if(o1.getDate_for_appointment().equals(o2.getDate_for_appointment())){
                                return o1.getTime_for_appointment().compareTo(o2.getTime_for_appointment());
                            }
                            return 0;
                        }
                    });

                    AdapterPatient = new AdapterPatientAccepted(Patient_Dashboard.this, userDoctor);

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
        inflater.inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.log_out:
                Paper.book().delete(Utilities.USER_KEY);
                Paper.book().delete(Utilities.Doctor);
                startActivity(new Intent(Patient_Dashboard.this , MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ClickNavBar() {
        home_nav = findViewById(R.id.bottom_navigation);
        home_nav.setSelectedItemId(R.id.menu_home);
        home_nav.setOnNavigationItemSelectedListener(item -> ClickOnNavBar(item.getItemId()));
    }

    public boolean ClickOnNavBar(int itemId){
            Intent activity;
            switch(itemId){

                case R.id.menu_home:
                    return true;
                case R.id.menu_appointments:
                    activity = new Intent(Patient_Dashboard.this, ViewCompletedAppointmentsActivity.class);
                    activity.putExtra("userType", "Patient");
                    startActivity(activity);
                    return true;
                case R.id.menu_consultation:
                    activity = new Intent(Patient_Dashboard.this, Doctor_List.class);
                    startActivity(activity);
                    return true;
                case R.id.menu_profile:
                    activity = new Intent(Patient_Dashboard.this, Patient_Profile.class);
                    startActivity(activity);
                    return true;

            }
            return true;

        }


}