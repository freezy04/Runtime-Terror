package com.example.mobidoc.ui.Appointment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Appointment;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Booking extends AppCompatActivity {

    TextView mDisplayDate, mDisplayTime;
    Button mBook;
    EditText Reason;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String Sdate, Stime, myUid, myName, myNameL,reason_for_appointment, DoctorUid, DoctorName;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    BottomNavigationView home_nav;
    int hour, Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mDisplayDate = findViewById(R.id.Date);
        mDisplayTime = findViewById(R.id.Time);
        mBook = findViewById(R.id._Book);
        Reason = findViewById(R.id.reason);

        NavBar();
        ClickNavBar();

        checkUserStatus();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Booking Appointment...");

        Intent intent = getIntent();
        DoctorUid = intent.getStringExtra("hisUid");
        DoctorName = intent.getStringExtra("hisName");

        reason_for_appointment = Reason.getText().toString();


        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Booking.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date = month+1 + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(Date);
                Sdate = Date;
            }
        };

        mDisplayTime.setOnClickListener(v -> {
            timePicker();
        });


//        mDisplayTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                hour = calendar.get(Calendar.HOUR_OF_DAY);
//                Minute = calendar.get(Calendar.MINUTE);
//                TimePickerDialog timePickerDialog = new TimePickerDialog(Booking.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Calendar c = Calendar.getInstance();
//                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                        c.set(Calendar.MINUTE, minute);
//                        c.setTimeZone(TimeZone.getDefault());
//                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
//                        String time = format.format(c.getTime());
//                        Stime = time;
//                        mDisplayTime.setText(time);
//                    }
//                }, hour, Minute, false
//                );
//                timePickerDialog.show();
//            }
//        });

        mBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ValidateDetails(mDisplayDate, mDisplayTime,Reason)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Booking.this);
                    dialog.setTitle("Are you sure?");
                    dialog.setMessage("Appointment will await confirmation from the doctor");
                    dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String _Date = Sdate.trim();
//                            String _Time = Stime.trim();
//                        String _Time = "20:30 PM";
                            String _Time = mDisplayTime.getText().toString().trim();


                            reason_for_appointment = Reason.getText().toString();
                            BookAppointment_(DoctorUid, _Date, _Time, DoctorName, reason_for_appointment);
                        }
                    });
                    dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
                else{
                    Toast.makeText(Booking.this, "Invaild details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void timePicker() {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        TimePickerDialog picker = new TimePickerDialog(Booking.this,
                (tp, sHour, sMinute) -> mDisplayTime.setText(sHour + ":" + sMinute), hour, minutes, true);
        picker.show();
    }

    private void BookAppointment_(String DoctorUid, String Date, String Time, String Doctor_Name, String Reason_for_appointment) {
        BookAppointment(DoctorUid, Date, Time, DoctorName, reason_for_appointment);
    }
    public void BookAppointment(String DoctorUid, String Date, String Time, String Doctor_Name, String Reason_for_appointment) {
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String timestamp = String.valueOf(System.currentTimeMillis());

        Appointment appointment = new Appointment(myUid, myName+" "+myNameL, DoctorUid, Doctor_Name, Date, Time, Reason_for_appointment, "0", "pending", "");
        databaseReference.child("Appointments").push().setValue(appointment);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("PatientUid", myUid);
//        hashMap.put("Patient_Name", myName +" "+ myNameL);
//        hashMap.put("DoctorUid", DoctorUid);
//        hashMap.put("Doctor_Name", Doctor_Name);
//        hashMap.put("Date_for_appointment", Date);
//        hashMap.put("Time_for_appointment", Time);
//        hashMap.put("Reason_for_appointment", Reason_for_appointment);
//        hashMap.put("Appointment_Cost", "0");
//        databaseReference.child("Appointments").push().setValue(hashMap);

        mDisplayTime.setText("");
        mDisplayDate.setText("");

        progressDialog.dismiss();
        Toast.makeText(Booking.this,
                "Appointment Booked...",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Booking.this, Patient_Dashboard.class));
        finish();
    }

    private boolean ValidateDetails(TextView date, TextView time, EditText reason){
        boolean valid = true;

        if(date.getText().toString().isEmpty()){
            valid = false;
            mDisplayDate.setError("select date for appointment");
        } else  {
            String s = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
            if (s.compareTo(date.getText().toString()) >= 0) {
                valid = false;
                mDisplayDate.setError("Too late to book an appointment on this date");
            }
        }
        if(time.getText().toString().isEmpty()){
            valid = false;
            mDisplayTime.setError("select time for appointment");
        }
        if(reason.getText().toString().isEmpty()){
            valid = false;
            reason.setError("Enter reason for appointment");
        }
        return (valid);
    }
    private void checkUserStatus() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // mProfile.setText(user.getEmail());
            myUid = user.getUid();
            checkStatus(myUid);
        }
    }

    public void checkStatus(String uid){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Patients");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if(ds.child("uid").getValue().equals(uid)){
                        myName = ds.child("first_name").getValue().toString();
                        myNameL = ds.child("last_name").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
        startActivity(new Intent(Booking.this, Doctor_List.class));
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
                activity = new Intent(Booking.this, Patient_Dashboard.class);
                startActivity(activity);
                return true;

            case R.id.menu_appointments:
                activity = new Intent(Booking.this, DoctorViewAcceptedAppointmentsActivity.class);
                startActivity(activity);
                break;

            case R.id.menu_consultation:
                return true;

            case R.id.menu_profile:
                activity = new Intent(Booking.this, Patient_Profile.class);
                startActivity(activity);
                return true;

        }
        return true;
    }
}