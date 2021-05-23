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
import com.example.mobidoc.models.Doctor;
import com.example.mobidoc.ui.Appointment.DoctorViewAcceptedAppointmentsActivity;
import com.example.mobidoc.ui.Appointment.DoctorViewPendingAppointmentsActivity;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.dashboards.Doctor_Dashboard;
import com.example.mobidoc.utils.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Doctor_ProfileActivity extends AppCompatActivity {

    private TextView mName;
    private TextView mEmail;
    private TextView mSpecialization;
    private TextView mExperiance;
    private TextView mAge;
    private TextView mContact;
    private TextView mEducation;
    private Button mEditProfileButton;

    private String name, specialization, experiance, education, email, age, contact, address, shift;
    BottomNavigationView home_nav;
    private DatabaseReference mDoctorDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__profile);

        mName = (TextView) findViewById(R.id.doctor_name);
        mSpecialization = (TextView) findViewById(R.id.doctor_specialization);
        mExperiance = (TextView) findViewById(R.id.doctor_experience);
        mEducation = (TextView) findViewById(R.id.doctor_education);
        mEmail = (TextView) findViewById(R.id.doctor_email);
        mAge = (TextView) findViewById(R.id.doctor_age);
        mContact = (TextView) findViewById(R.id.doctor_contact);



        NavBar();
        getDoctorDetails(mAuth.getCurrentUser().getUid());
        ClickNavBar();


        mName = (TextView) findViewById(R.id.doctor_name);



        mEditProfileButton = (Button) findViewById(R.id.edit_profile_button);
        mEditProfileButton.setOnClickListener(v -> {


            Intent editProfile_Intent = new Intent(Doctor_ProfileActivity.this, Doctor_EditProfileActivity.class);

            editProfile_Intent.putExtra("Name",name);
            editProfile_Intent.putExtra("Specialization",specialization);
            editProfile_Intent.putExtra("Experiance",experiance);
            editProfile_Intent.putExtra("Education",education);
            editProfile_Intent.putExtra("Email",email);
            editProfile_Intent.putExtra("Age",age);
            editProfile_Intent.putExtra("Contact",contact);
            editProfile_Intent.putExtra("Address",address);

            startActivity(editProfile_Intent);
        });

    }



        public void getDoctorDetails(String uid){

            FirebaseDatabase db = FirebaseDatabase.getInstance();  // get instance of our Firebase Database
            DatabaseReference ref = db.getReference();             // retrieves the specific Realtime Database
            DatabaseReference user_ref = ref.child("Doctors");

            user_ref.orderByKey().equalTo(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        Doctor doctor = ds.getValue(Doctor.class);
                        mName.setText("Dr. " + doctor.getFirst_name() + " " + doctor.getLast_name());
                        mSpecialization.setText(doctor.getSpecialization());
                        mExperiance.setText(doctor.getExperience());
                        mEducation.setText(doctor.getQualifications());
                        mEmail.setText(doctor.getEmail());
                        mAge.setText(doctor.getLast_name());



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    public void NavBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Doctor Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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
                startActivity(new Intent(Doctor_ProfileActivity.this , MainActivity.class));
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
        startActivity(new Intent(Doctor_ProfileActivity.this, Doctor_Dashboard.class));
        finish();
    }
    public void ClickNavBar() {
        home_nav = findViewById(R.id.bottom_navigation2);
        home_nav.setSelectedItemId(R.id.nav_profile2);
        home_nav.setOnNavigationItemSelectedListener(item -> ClickOnNavBar(item.getItemId()));
    }

    public boolean ClickOnNavBar(int itemId) {
            Intent activity;
            switch (itemId) {

                case R.id.nav_home2:
                    activity = new Intent(Doctor_ProfileActivity.this, Doctor_Dashboard.class);
                    startActivity(activity);
                    return true;
                case R.id.nav_pateintrecords:
                    activity = new Intent(Doctor_ProfileActivity.this, Doctor_Dashboard.class);
                    startActivity(activity);
                    return true;

                case R.id.nav_profile2:
                    activity = new Intent(Doctor_ProfileActivity.this, Doctor_ProfileActivity.class);
                    startActivity(activity);
                    return true;

                case R.id.nav_pendingappointments2:
                    activity = new Intent(Doctor_ProfileActivity.this, DoctorViewPendingAppointmentsActivity.class);
                    startActivity(activity);
                    return true;

                case R.id.nav_acceptedappointments2:

                    activity = new Intent(Doctor_ProfileActivity.this, DoctorViewAcceptedAppointmentsActivity.class);

                    //activity.putExtra("userType", "Doctor");
                    startActivity(activity);

                    //  activity = new Intent(Doctor_Dashboard.this, DoctorViewAcceptedAppointmentsActivity.class);
                    // startActivity(activity);
                    return true;
            }
            return true;

        }


}
