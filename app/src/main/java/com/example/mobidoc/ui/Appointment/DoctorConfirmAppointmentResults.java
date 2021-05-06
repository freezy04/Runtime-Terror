package com.example.mobidoc.ui.Appointment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.models.Patient;
import com.example.mobidoc.models.ViewPatientList;
import com.example.mobidoc.ui.login.Login;
import com.example.mobidoc.ui.registration.DoctorRegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DoctorConfirmAppointmentResults extends AppCompatActivity {

    public EditText newMedicationET, appointmentCostET;
    public Button confirmBTN , BackBTN;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private String appointmentUID, patientUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_confirm_appointment_results);

        initializeActivity();

        confirmBTN.setOnClickListener(v -> {
            updateDetails();
            startActivity(new Intent(DoctorConfirmAppointmentResults.this, ViewPatientList.class));
            finish();
        });
        BackBTN.setOnClickListener(v -> {
            startActivity(new Intent(DoctorConfirmAppointmentResults.this, ViewPatientList.class));
            finish();
        });

    }

    private void initializeActivity() {
        //set up action bar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Confirm Appointment Results");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //initialize swing elements
        newMedicationET = findViewById(R.id.newMedicationET);
        appointmentCostET = findViewById(R.id.appointmentCostET);
        confirmBTN = findViewById(R.id.confirmBTN);
        BackBTN = findViewById(R.id.backBTN);

        mAuth = FirebaseAuth.getInstance();//get connection to firebase

        Intent confirmResults = getIntent();
        appointmentUID = confirmResults.getStringExtra("appointmentUID");
        patientUID = confirmResults.getStringExtra("patientUID");

        //set up progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Confirming results...");
    }

    private boolean costValid(String cost) {
        if (cost.isEmpty()) {
            appointmentCostET.setError("Appointment cost cannot be empty");
            return false;
        }
//        Pattern lowerCase = Pattern.compile("\\p{Lower}");//all lowercase letters
//        Pattern upperCase = Pattern.compile("\\p{Upper}");//all uppercase letters
//        Pattern special = Pattern.compile("\\p{Punct}");//all special characters
//        Matcher hasLowerCase = lowerCase.matcher(cost);
//        Matcher hasUpperCase = upperCase.matcher(cost);
//        Matcher hasSpecial = special.matcher(cost);
//        if (hasLowerCase.find() || hasUpperCase.find() || hasSpecial.find()) {
//            appointmentCostET.setError("Appointment cost must contain only numbers");
//            return false;
//        }
        return true;
    }

    private void updateCost(String cost) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Appointments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Appointment app = ds.getValue(Appointment.class);
                    app.setId(ds.getKey());
                    if (app.getId().equals(appointmentUID)) {
                        app.setAppointment_Cost(cost);
                        Map<String, Object> appointmentUpdate = new HashMap<>();
                        appointmentUpdate.put(appointmentUID, app);
                        ref.updateChildren(appointmentUpdate);
                        break;
                    }
                }
                progressDialog.dismiss();
                Toast.makeText(DoctorConfirmAppointmentResults.this, "Update successful.", Toast.LENGTH_SHORT).show();
                //TODO: switch to appropriate screen
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(DoctorConfirmAppointmentResults.this, "Appointment update failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMedication(String medication, String cost) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Patients");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Patient pat = ds.getValue(Patient.class);
                    if (pat.getUid().equals(patientUID)) {
                        pat.setCurrentMedication(medication);
                        Map<String, Object> patientUpdate = new HashMap<>();
                        patientUpdate.put(patientUID, pat);
                        ref.updateChildren(patientUpdate);
                        break;
                    }
                }
                updateCost(cost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DoctorConfirmAppointmentResults.this, "Medication update failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void confirmUpdates(String medication, String cost) {
        progressDialog.show();

        //TODO: remove testing
        patientUID = "u9d0ngAERLNk543UhIwhl4muRy82";
        appointmentUID = "-MZxazD9vpT0F5aY1BaY";

        updateMedication(medication, cost);
    }

    private void updateDetails() {
        String newMedication = newMedicationET.getText().toString().trim();
        String appointmentCost = appointmentCostET.getText().toString().trim();
        if (costValid(appointmentCost)) {
            confirmUpdates(newMedication, appointmentCost);
        }
    }

}
