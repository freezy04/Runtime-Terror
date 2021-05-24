package com.example.mobidoc.ui.dashboards;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.ui.Appointment.Doctor_List;
import com.example.mobidoc.ui.Appointment.ViewCompletedAppointmentsActivity;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.profiles.Patient_Profile;
import com.example.mobidoc.utils.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;


public class Patient_Dashboard extends AppCompatActivity {
    BottomNavigationView home_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ClickNavBar();

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