package com.example.mobidoc.ui.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.utils.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class Doctor_EditProfileActivity extends AppCompatActivity {
    public int counter = 0;
    Button Profile_page , Update_details;
    public TextView doc_fname, doc_lname,doc_experience,doc_qualifications,doc_special;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__edit_profile);

        NavBar();

        doc_fname = findViewById(R.id.doc_fNameET);
        doc_lname = findViewById(R.id.doc_lNameET);
        doc_experience = findViewById(R.id.doc_experienceET);
        doc_qualifications = findViewById(R.id.doc_qualify);
        doc_special = findViewById(R.id.doc_spec);

        Update_details = findViewById(R.id.doc_update_details);

        Update_details.setOnClickListener(v -> {

            updateDetails("first_name",doc_fname.getText().toString());
            updateDetails("last_name",doc_lname.getText().toString());
            updateDetails("experience",doc_experience.getText().toString());
            updateDetails("qualifications",doc_qualifications.getText().toString());
            updateDetails("specialization",doc_special.getText().toString());

            if (counter == 0) {
                Toast.makeText(this, "No Information was Updated", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "User Information was successfully updated", Toast.LENGTH_LONG).show();
            }

        });

        Profile_page = findViewById(R.id.doc_profile_page);
        Profile_page.setOnClickListener(v -> {
            startActivity(new Intent(Doctor_EditProfileActivity.this, Doctor_ProfileActivity.class));
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
        DatabaseReference user_ref = ref.child("Doctors");     // specify user type

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
                startActivity(new Intent(Doctor_EditProfileActivity.this , MainActivity.class));
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
