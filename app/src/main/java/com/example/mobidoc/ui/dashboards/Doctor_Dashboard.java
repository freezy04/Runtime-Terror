package com.example.mobidoc.ui.dashboards;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.ui.Appointment.DoctorViewAcceptedAppointmentsActivity;
import com.example.mobidoc.ui.Appointment.DoctorViewPendingAppointmentsActivity;
import com.example.mobidoc.ui.Appointment.ViewCompletedAppointmentsActivity;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.profiles.Doctor_ProfileActivity;
import com.example.mobidoc.utils.Utilities;

import io.paperdb.Paper;

public class Doctor_Dashboard extends AppCompatActivity {
    LinearLayout doctor_profile,doctor_pending_appointments,doctor_completed_appointments,doctor_accepted_appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__dashboard);

        doctor_profile = findViewById(R.id.ll_dotor_profile);
        doctor_profile.setOnClickListener(v -> {
            startActivity(new Intent(Doctor_Dashboard.this, Doctor_ProfileActivity.class));
        });

        doctor_pending_appointments = findViewById(R.id.ll_pending_appointments);
        doctor_pending_appointments.setOnClickListener(v -> {
            startActivity(new Intent(Doctor_Dashboard.this, DoctorViewPendingAppointmentsActivity.class));
        });

        doctor_accepted_appointments = findViewById(R.id.ll_accepted_appointments);
        doctor_accepted_appointments.setOnClickListener(v -> {
            startActivity(new Intent(Doctor_Dashboard.this, DoctorViewAcceptedAppointmentsActivity.class));
        });

        //view completed appointments temporarily using this layout
        doctor_completed_appointments = findViewById(R.id.ll_patient_records);
        doctor_completed_appointments.setOnClickListener(v -> {
            Intent docViewCompletedApps = new Intent(Doctor_Dashboard.this, ViewCompletedAppointmentsActivity.class);
            docViewCompletedApps.putExtra("userType", "Doctor");
            startActivity(docViewCompletedApps);
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
                startActivity(new Intent(Doctor_Dashboard.this , MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
