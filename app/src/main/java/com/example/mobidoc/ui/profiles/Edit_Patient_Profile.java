package com.example.mobidoc.ui.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_Patient_Profile extends AppCompatActivity {

    private static final String TAG = "Edit_Patient_Profile";
    public int counter = 0;
    TextView patient_fname,patient_lname,patient_age,patient_curr_med,patient_med_hist,patient_allergies,patient_disease_hist;
    Button Update,Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_patient_profile);

    NavBar();

    patient_fname = findViewById(R.id.fNameET);
    patient_lname = findViewById(R.id.lNameET);
    patient_age = findViewById(R.id.ageET);
    patient_med_hist = findViewById(R.id.medicationHistoryET);
    patient_allergies = findViewById(R.id.allergiesET);
    patient_disease_hist = findViewById(R.id.diseaseHistoryET);
    patient_curr_med = findViewById(R.id.newMedicationET);

    Update = findViewById(R.id.update_details);

    Update.setOnClickListener(v -> {

        updateDetails("first_name",patient_fname.getText().toString());
        updateDetails("last_name",patient_lname.getText().toString());
        updateDetails("age",patient_age.getText().toString());
        updateDetails("medicationHistory",patient_med_hist.getText().toString());
        updateDetails("allergies",patient_allergies.getText().toString());
        updateDetails("currentMedication",patient_curr_med.getText().toString());
        updateDetails("diseaseHistory",patient_disease_hist.getText().toString());

        if (counter == 0) {
            Toast.makeText(this, "No Information was Updated", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "User Information was successfully updated", Toast.LENGTH_LONG).show();
        }

    });

    Profile = findViewById(R.id.profile_page);
    Profile.setOnClickListener(v -> {
        startActivity(new Intent(Edit_Patient_Profile.this, Patient_Profile.class));
        finish();
    });


    }


    public void updateDetails(String title , String value){

        if(isEdited(value)){
            counter++;
            UpDateOnFirebaseDetails(title,value);
        }

    }
    public void UpDateOnFirebaseDetails(String title , String value){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth != null){
            String UID = mAuth.getCurrentUser().getUid();
            updateDetails(UID, title, value);
        }

    }

    public void updateDetails(String uid, String title, String value){
        FirebaseDatabase db = FirebaseDatabase.getInstance();   // get instance of our Firebase Database
        DatabaseReference ref = db.getReference();              // retrieves the specific Realtime Database
        DatabaseReference user_ref = ref.child("Patients");     // specify user type

        user_ref.child(uid).child(title).setValue(value);
    }

    public boolean isEdited(String s){

        if(s.isEmpty()){
            return false;
        }
        return true;
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
        startActivity(new Intent(Edit_Patient_Profile.this, Patient_Profile.class));
        finish();
    }

}
