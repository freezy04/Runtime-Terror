package com.example.mobidoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText mEmailET, mPasswordEt, FirstName, LastName, PhoneNo, DateOfBirth, Qualification, DegreeAndVarsity;
    Button mRegisterBTN;
    TextView mHAVEACCOUNT;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        FirstName = findViewById(R.id.FirstNameName);
        LastName = findViewById(R.id.LastName);
        Qualification = findViewById(R.id.Qualification);
        DateOfBirth = findViewById(R.id.DateOfBirth);
        DegreeAndVarsity = findViewById(R.id.DegreeAndVarsity);
        PhoneNo = findViewById(R.id.PhoneNo);
        mEmailET = findViewById(R.id.email);
        mPasswordEt = findViewById(R.id.password);
        mRegisterBTN = findViewById(R.id.btn_register);
        mHAVEACCOUNT = findViewById(R.id.have_accountTv);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        mRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fname = FirstName.getText().toString().trim();
                String Lname = LastName.getText().toString().trim();
                String qualif = Qualification.getText().toString().trim();
                String DateofB = DateOfBirth.getText().toString().trim();
                String degreeNvarsity = DegreeAndVarsity.getText().toString().trim();
                String phonNo = PhoneNo.getText().toString().trim();
                String email = mEmailET.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();


                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailET.setError("Invalid Email");
                    mEmailET.setFocusable(true);
                }
                else if(password.length()<6){
                    mPasswordEt.setError("Password length at least 6 characters");
                    mPasswordEt.setFocusable(true);
                }
                else{
                    // Check if user is signed in (non-null) and update UI accordingly.

                    registerUser(email, password, Fname, Lname, qualif, DateofB, degreeNvarsity, phonNo);



                }
            }
        });

        mHAVEACCOUNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Register.this,LoginActivity.class));
                //finish();
            }
        });
    }

    private void registerUser(String email, String password, String Fname, String Lname, String qualif, String DateofB, String degreeNvarsity, String phoneNo) {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("uid",uid);
                            hashMap.put("Fisrt name",Fname);
                            hashMap.put("Last name",Lname);
                            hashMap.put("Date of birth", DateofB);
                            hashMap.put("Qualification", qualif);
                            hashMap.put("Degree and varsity", degreeNvarsity);
                            hashMap.put("Phone Number", phoneNo);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("Doctors");

                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(Register.this, "Registered...\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}
