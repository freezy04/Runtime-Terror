package com.example.mobidoc.ui.Appointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.adapters.adapterAppointment;
import com.example.mobidoc.models.Doctor;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
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

public class Doctor_List extends AppCompatActivity {

    RecyclerView recyclerView;
    adapterAppointment AdapterAppointment;
    List<Doctor> userDoctor;
    FirebaseAuth firebaseAuth;
    BottomNavigationView home_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        firebaseAuth = FirebaseAuth.getInstance();

        NavBar();
        ClickNavBar();

        recyclerView = findViewById(R.id.users_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Doctor_List.this));
        userDoctor = new ArrayList<>();
        getAllUsers();
    }


    private void getAllUsers() {

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fUser != null){
            getAllUsers(fUser.getUid());
        }

    }

    public void getAllUsers(String uid){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctors");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                userDoctor.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Doctor modelUsers = ds.getValue(Doctor.class);

                    if(!modelUsers.getUid().equals(uid)){
                        userDoctor.add(modelUsers);
                    }

                    AdapterAppointment = new adapterAppointment(Doctor_List.this, userDoctor);

                    recyclerView.setAdapter(AdapterAppointment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void searchUsers(final String newText) {

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fUser != null){
            searchForUsers(fUser.getUid(), newText);
        }

    }

    public void searchForUsers(String uid, String newText){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Doctors");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                userDoctor.clear();
                for(DataSnapshot ds: snapshot.getChildren()){

                    Doctor modelUsers = ds.getValue(Doctor.class);

                    if(!modelUsers.getUid().equals(uid)){

                        if(modelUsers.getFirst_name().toLowerCase().contains(newText.toLowerCase())  || modelUsers.getEmail().toLowerCase().contains(newText.toLowerCase())){
                            userDoctor.add(modelUsers);
                        }

                    }

                    AdapterAppointment = new adapterAppointment(Doctor_List.this , userDoctor);

                    AdapterAppointment.notifyDataSetChanged();

                    recyclerView.setAdapter(AdapterAppointment);
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
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(!TextUtils.isEmpty(query.trim())){
                    searchUsers(query);
                }
                else{
                    getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(!TextUtils.isEmpty(newText.trim())){
                    searchUsers(newText);
                }
                else{
                    getAllUsers();
                }


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            // mProfile.setText(user.getEmail());
        }else{
            startActivity(new Intent(Doctor_List.this, MainActivity.class));
            finish();
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    public void NavBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Patient Book Appointment");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        startActivity(new Intent(Doctor_List.this, Patient_Dashboard.class));
        finish();
    }

    public void ClickNavBar() {
        home_nav = findViewById(R.id.bottom_navigation);
        home_nav.setSelectedItemId(R.id.menu_consultation);
        home_nav.setOnNavigationItemSelectedListener(item -> onNavBarItemClicked(item.getItemId()));
    }

    public boolean onNavBarItemClicked(int itemId){
        Intent activity;

        switch (itemId) {

            case R.id.menu_home:
                activity = new Intent(Doctor_List.this, Patient_Dashboard.class);
                startActivity(activity);
                return true;

            case R.id.menu_appointments:
                activity = new Intent(Doctor_List.this, ViewCompletedAppointmentsActivity.class);
                startActivity(activity);
                break;

            case R.id.menu_consultation:
                return true;

            case R.id.menu_profile:
                activity = new Intent(Doctor_List.this, Patient_Profile.class);
                startActivity(activity);
                return true;

        }
        return true;
    }

}