package com.example.mobidoc.ui.Appointment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DoctorConfirmAppointmentResults extends AppCompatActivity {

    public EditText newMedicationET, appointmentCostET, appointmentNotesET;
    public Button confirmBTN;
    private ProgressDialog progressDialog;
    private String appointmentUID, patientUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_confirm_appointment_results);

        initializeActivity();

        confirmBTN.setOnClickListener(v -> updateDetails());

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
        appointmentNotesET = findViewById(R.id.appointmentNotesET);
        TextView headingTW = findViewById(R.id.headingTW);
        confirmBTN = findViewById(R.id.confirmBTN);

        Intent confirmResults = getIntent();
        appointmentUID = confirmResults.getStringExtra("appointmentUID");
        patientUID = confirmResults.getStringExtra("patientUID");
        String patientName = confirmResults.getStringExtra("patientName");

        headingTW.setText(String.format("Appointment with %s", patientName));

        //set up progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Confirming results...");
    }

//    private boolean costValid(String cost) {
//        return !cost.isEmpty();
////        Pattern lowerCase = Pattern.compile("\\p{Lower}");//all lowercase letters
////        Pattern upperCase = Pattern.compile("\\p{Upper}");//all uppercase letters
////        Pattern special = Pattern.compile("\\p{Punct}");//all special characters
////        Matcher hasLowerCase = lowerCase.matcher(cost);
////        Matcher hasUpperCase = upperCase.matcher(cost);
////        Matcher hasSpecial = special.matcher(cost);
////        if (hasLowerCase.find() || hasUpperCase.find() || hasSpecial.find()) {
////            appointmentCostET.setError("Appointment cost must contain only numbers");
////            return false;
////        }
//    }

    private void confirmUpdates(String medication, String cost, String notes) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Patients").child(patientUID).child("currentMedication");
        ref.setValue(medication);
        ref = db.getReference("Appointments").child(appointmentUID).child("appointment_Cost");
        ref.setValue(cost);
        ref = db.getReference("Appointments").child(appointmentUID).child("notes");
        ref.setValue(notes);
        ref = db.getReference("Appointments").child(appointmentUID).child("status");
        ref.setValue("completed");
        progressDialog.dismiss();
        Toast.makeText(DoctorConfirmAppointmentResults.this, "Update successful.", Toast.LENGTH_SHORT).show();
        Intent doctorViewAppointments = new Intent(DoctorConfirmAppointmentResults.this, DoctorViewAcceptedAppointmentsActivity.class);
        startActivity(doctorViewAppointments);
        finish();
    }

    private void updateDetails() {
        progressDialog.show();
        String newMedication = newMedicationET.getText().toString().trim();
        String appointmentCost = appointmentCostET.getText().toString().trim();
        String notes = appointmentNotesET.getText().toString().trim();
        if (!appointmentCost.isEmpty()) {
            confirmUpdates(newMedication, appointmentCost, notes);
        } else{
            progressDialog.dismiss();
            appointmentCostET.setError("Appointment cost cannot be empty");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
