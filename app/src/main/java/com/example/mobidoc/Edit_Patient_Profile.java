package com.example.mobidoc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.ui.registration.Patient_Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_Patient_Profile extends AppCompatActivity {

    private static final String TAG = "Edit_Patient_Profile";
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

        Toast.makeText(this, "User Information Updated", Toast.LENGTH_LONG).show();

    });

    Profile = findViewById(R.id.profile_page);
    Profile.setOnClickListener(v -> {
        startActivity(new Intent(Edit_Patient_Profile.this, Patient_Profile.class));
    });


    }


    public void updateDetails(String title , String value){

        if(isEdited(value)){
            UpDateOnFirebaseDetails(title,value);
        }

    }
    public void UpDateOnFirebaseDetails(String title , String value){

        FirebaseDatabase db = FirebaseDatabase.getInstance();   // get instance of our Firebase Database
        DatabaseReference ref = db.getReference();              // retrieves the specific Realtime Database
        DatabaseReference user_ref = ref.child("Patients");     // specify user type
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String UID = mAuth.getCurrentUser().getUid();
        user_ref.child(UID).child(title).setValue(value);

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

}
