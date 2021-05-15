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
import com.example.mobidoc.ui.Appointment.Doctor_List;
import com.example.mobidoc.ui.Appointment.ViewCompletedAppointmentsActivity;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.profiles.Patient_Profile;
import com.example.mobidoc.utils.Utilities;

import io.paperdb.Paper;


public class Patient_Dashboard extends AppCompatActivity {
    LinearLayout BookAppointment,Profile_page,AppointmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BookAppointment = findViewById(R.id.ll_consultation);

        AppointmentList = findViewById(R.id.ll_appointments);

        BookAppointment.setOnClickListener(v -> {
            startActivity(new Intent(Patient_Dashboard.this, Doctor_List.class));
        });

        Profile_page = findViewById(R.id.ll_profile);
        Profile_page.setOnClickListener(v -> {
            startActivity(new Intent(Patient_Dashboard.this, Patient_Profile.class));
        });

        AppointmentList.setOnClickListener(v -> {
            Intent patViewCompletedApps = new Intent(Patient_Dashboard.this, ViewCompletedAppointmentsActivity.class);
            patViewCompletedApps.putExtra("userType", "Patient");
            startActivity(patViewCompletedApps);
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

}