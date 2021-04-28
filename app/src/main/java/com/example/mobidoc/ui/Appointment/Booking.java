package com.example.mobidoc.ui.Appointment;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Patient;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.dashboards.Doctor_Dashboard;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
import com.example.mobidoc.ui.registration.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class Booking extends AppCompatActivity {

    TextView mDisplayDate,mDisplayTime;
    Button mBook;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String Sdate, Stime, myUid,DoctorUid;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    int hour,Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mDisplayDate = findViewById(R.id.Date);
        mDisplayTime = findViewById(R.id.Time);
        mBook = findViewById(R.id._Book);

        checkUserStatus();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Booking Appointment...");

        Intent intent = getIntent();
        DoctorUid = intent.getStringExtra("hisUid");

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
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String Date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(Date);
                Sdate = Date;
            }
        };

        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                Minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Booking.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        Stime = time;
                        mDisplayTime.setText(time);
                    }
                },hour,Minute,false
                );
                timePickerDialog.show();
            }
        });

        mBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Booking.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Appointment will await confirmation from the doctor");
                dialog.setPositiveButton("Booking", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String _Date = Sdate.trim();
                        String _Time = Stime.trim();

                        BookAppointment(DoctorUid,_Date,_Time);
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
        });
    }

    private void BookAppointment(String DoctorUid, String Date,String Time){
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("PatientUid", myUid);
        hashMap.put("DoctorUid", DoctorUid);
        hashMap.put("Date_for_appointment", Date);
        hashMap.put("Time_for_appointment", Time);
        databaseReference.child("Appointments").push().setValue(hashMap);

        mDisplayTime.setText("");
        mDisplayDate.setText("");

        Toast.makeText(Booking.this,
                "Appointment Booked...",
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Booking.this, Patient_Dashboard.class));
        finish();
    }

    private void checkUserStatus(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            // mProfile.setText(user.getEmail());
            myUid = user.getUid();
        }else{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}