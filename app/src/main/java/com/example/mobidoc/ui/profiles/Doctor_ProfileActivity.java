package com.example.mobidoc.ui.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Doctor;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.utils.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Doctor_ProfileActivity extends AppCompatActivity {

    private TextView mName, mEmail, mSpecialization, mExperiance, mAge, mContact, mAddress, mEducation;
    private Button mShowRosterPlanButton, mEditProfileButton;

    private String name, specialization, experiance, education, email, age, contact, address, shift;

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

        getDoctorDetails();


        mName = (TextView) findViewById(R.id.doctor_name);

        mShowRosterPlanButton = (Button) findViewById(R.id.show_rosterPlan_button);
        mShowRosterPlanButton.setOnClickListener(v -> {
            //Toast.makeText(Doctor_ProfileActivity.this,"Show Roster Plan Clicked",Toast.LENGTH_SHORT).show();
//            alertDialogBox();


        });

        mEditProfileButton = (Button) findViewById(R.id.edit_profile_button);
        mEditProfileButton.setOnClickListener(v -> {
            //Toast.makeText(Doctor_ProfileActivity.this,"Edit Profile Clicked",Toast.LENGTH_SHORT).show();

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

    private void alertDialogBox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Doctor_ProfileActivity.this);

        View view = getLayoutInflater().inflate(R.layout.roster_dialog, null);

        TextView rosterShift = (TextView) view.findViewById(R.id.roster_shift);
        TextView rosterTimingMorning = (TextView) view.findViewById(R.id.roster_time_morning);
        TextView rosterTimingEvening = (TextView) view.findViewById(R.id.roster_time_evening);
        TextView rosterLunchMorning = (TextView) view.findViewById(R.id.roster_lunch_morning);
        TextView rosterLunchEvening = (TextView) view.findViewById(R.id.roster_lunch_evening);

//        if(shift.equals("Morning")){

            rosterShift.setText(shift);

            rosterTimingMorning.setVisibility(View.VISIBLE);
            rosterTimingEvening.setVisibility(View.GONE);

            rosterLunchMorning.setVisibility(View.VISIBLE);
            rosterLunchEvening.setVisibility(View.GONE);

//        }else {
//
//            rosterShift.setText(shift);
//
//            rosterTimingMorning.setVisibility(View.GONE);
//            rosterTimingEvening.setVisibility(View.VISIBLE);
//
//            rosterLunchMorning.setVisibility(View.GONE);
//            rosterLunchEvening.setVisibility(View.VISIBLE);
//
//        }

        builder.setView(view);
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }


        public void getDoctorDetails(){

            FirebaseDatabase db = FirebaseDatabase.getInstance();  // get instance of our Firebase Database
            DatabaseReference ref = db.getReference();             // retrieves the specific Realtime Database
            DatabaseReference user_ref = ref.child("Doctors");

            user_ref.orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {

                        Doctor doctor = ds.getValue(Doctor.class);
                        mName.setText(doctor.getFirst_name());
                        mSpecialization.setText(doctor.getSpecialization());
                        mExperiance.setText(doctor.getExperience());
                        mEducation.setText(doctor.getQualifications());
                        mEmail.setText(doctor.getEmail());
                        mAge.setText(doctor.getLast_name());

                        Log.d("ProfileDoc", "This is " + doctor.getEmail());

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

}
