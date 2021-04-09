package com.example.mobidoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class Register extends AppCompatActivity {

    //GUI element declaration
    EditText mEmailET, mPasswordEt, FirstName, LastName, PhoneNo;
    //    EditText DateOfBirth, Qualification, DegreeAndVarsity;
    Switch userType;
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

        Paper.init(this); //    @ Dylan 2179115 added this Code to store information locally

        FirstName = findViewById(R.id.FirstNameName);
        LastName = findViewById(R.id.LastName);
//        Qualification = findViewById(R.id.Qualification);
//        DateOfBirth = findViewById(R.id.DateOfBirth);
//        DegreeAndVarsity = findViewById(R.id.DegreeAndVarsity);
        PhoneNo = findViewById(R.id.PhoneNo);
        mEmailET = findViewById(R.id.email);
        mPasswordEt = findViewById(R.id.password);
        mRegisterBTN = findViewById(R.id.btn_register);
        mHAVEACCOUNT = findViewById(R.id.have_accountTv);
        userType = findViewById(R.id.userTypeSwitch);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        /*** NetwWork Avaivilble ***/
        if (!isNetworkAvailable()) {
            startActivity(new Intent(Register.this, No_Internet.class));
        }

        mRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fname = FirstName.getText().toString().trim();
                String Lname = LastName.getText().toString().trim();
//                String qualif = Qualification.getText().toString().trim();
//                String DateofB = DateOfBirth.getText().toString().trim();
//                String degreeNvarsity = DegreeAndVarsity.getText().toString().trim();
                String phonNo = PhoneNo.getText().toString().trim().replaceAll(" ", "");//removes all spaces from the phone number
                String email = mEmailET.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();//should we trim here?


                //check for SQL injection?
                if (validateDetails(Fname, Lname, phonNo, email, password, true)) {
                    // Check if user is signed in (non-null) and update UI accordingly.

                    String userTypeStr;
                    if (userType.isChecked()) {//register as a doctor
                        userTypeStr = "Doctor";
                    } else {//register as a patient
                        userTypeStr = "Patient";
                    }


                    registerUser(email, password, Fname, Lname, userTypeStr, phonNo);

//                    registerUser(email, password, Fname, Lname, qualif, DateofB, degreeNvarsity, phonNo);


                }
            }
        });

        mHAVEACCOUNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login_activity.class));
                finish();
            }
        });

        userType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userType.isChecked()) {
                    userType.setText("Doctor");

                } else {
                    userType.setText("Patient");
                }
            }
        });
    }

    protected boolean validateFname(String Fname, boolean displayErrors) {//checks if personal information fields are empty, if so displays the appropriate error(s)
        if (Fname.isEmpty()) {
            if (displayErrors) {
                FirstName.setError("First name cannot be empty");
            }
            return false;
        }
        return true;
    }

    private boolean validateLname(String Lname, boolean displayErrors) {//checks if personal information fields are empty, if so displays the appropriate error(s)
        if (Lname.isEmpty()) {
            if (displayErrors) {
                LastName.setError("Last name cannot be empty");
            }
            return false;
        }
        return true;
    }

    private boolean validatePhoneNo(String phonNo, boolean displayErrors) {//checks if phone number is in correct format, otherwise displays error
        Pattern lowerCase = Pattern.compile("\\p{Lower}");//all lowercase letters
        Pattern upperCase = Pattern.compile("\\p{Upper}");//all uppercase letters
        Pattern special = Pattern.compile("\\p{Punct}");//all special characters
        Matcher hasLowerCase = lowerCase.matcher(phonNo);
        Matcher hasUpperCase = upperCase.matcher(phonNo);
        Matcher hasSpecial = special.matcher(phonNo);
        if (hasLowerCase.find() || hasUpperCase.find() || hasSpecial.find()) {//checks if phone number has any digits or special characters
            if (displayErrors) {
                PhoneNo.setError("Phone number cannot contain letters or special characters");
            }
            return false;
        } else if (phonNo.length() != 10) {//checks if the phone number is the correct length
            if (displayErrors) {
                PhoneNo.setError("Phone number must be 10 numbers long");
            }
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email, boolean displayErrors) {//if email address is not in valid format, displays error
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (displayErrors) {
                mEmailET.setError("Invalid Email");
            }
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, boolean displayErrors) {//checks if password is in correct format, if not displays error message
        //defines patterns and matchers for password security
        Pattern lowerCase = Pattern.compile("\\p{Lower}");//all lowercase letters
        Pattern upperCase = Pattern.compile("\\p{Upper}");//all uppercase letters
        Pattern number = Pattern.compile("\\p{Digit}");//all numbers
        Pattern special = Pattern.compile("\\p{Punct}");//all special characters
        Matcher hasLowerCase = lowerCase.matcher(password);
        Matcher hasUpperCase = upperCase.matcher(password);
        Matcher hasNumber = number.matcher(password);
        Matcher hasSpecial = special.matcher(password);
        //if password has less than 8 or more than 20 characters, does not contain at least one lowercase letter, does not contain at least one uppercase letter,
        //does not contain at least one number or does not contain at least one special character, displays error
        if (password.length() < 8 || password.length() > 20 || !hasLowerCase.find() || !hasUpperCase.find() || !hasNumber.find() || !hasSpecial.find()) {
            if (displayErrors) {
                mPasswordEt.setError("Password must be between 8-20 characters, and must include at least one lowercase letter, uppercase" +
                        " letter, number and special character");
            }
            return false;
        }
        return true;
    }

    private boolean validateDetails(String Fname, String Lname, String phonNo, String email, String password, boolean displayErrors) {
        boolean Fname_valid = validateFname(Fname, displayErrors);
        boolean Lname_valid = validateLname(Lname, displayErrors);
        boolean phonNo_valid = validatePhoneNo(phonNo, displayErrors);
        boolean email_valid = validateEmail(email, displayErrors);
        boolean password_valid = validatePassword(password, displayErrors);
        return Fname_valid && Lname_valid && phonNo_valid && email_valid && password_valid;
    }

    private void registerUser(String email, String password, String Fname, String Lname, String userType, String phoneNo) {
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
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("Fisrt name", Fname);
                            hashMap.put("Last name", Lname);
                            hashMap.put("User Type", userType);
                            hashMap.put("Phone Number", phoneNo);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference(userType + "s");

                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(Register.this, "Registered...\n" + user.getEmail(), Toast.LENGTH_SHORT).show();

                            /* ---- Dylan 2179115 added this code ----*/
                            if ("Doctor".equals(userType)) {
                                Paper.book().write(Utilities.Doctor, "Doc");
                                startActivity(new Intent(Register.this, Doctor_Dashboard.class));
                                finish();
                            } else {
                                startActivity(new Intent(Register.this, Patient_Dashboard.class));
                                finish();
                            }

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
                Toast.makeText(Register.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void registerUser(String email, String password, String Fname, String Lname, String qualif, String DateofB, String degreeNvarsity, String phoneNo) {
//        progressDialog.show();
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            progressDialog.dismiss();
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            String email = user.getEmail();
//                            String uid = user.getUid();
//
//                            HashMap<Object, String> hashMap = new HashMap<>();
//                            hashMap.put("email", email);
//                            hashMap.put("uid", uid);
//                            hashMap.put("Fisrt name", Fname);
//                            hashMap.put("Last name", Lname);
//                            hashMap.put("Date of birth", DateofB);
//                            hashMap.put("Qualification", qualif);
//                            hashMap.put("Degree and varsity", degreeNvarsity);
//                            hashMap.put("Phone Number", phoneNo);
//
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//                            DatabaseReference reference = database.getReference("Doctors");
//
//                            reference.child(uid).setValue(hashMap);
//
//                            Toast.makeText(Register.this, "Registered...\n" + user.getEmail(), Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(Register.this, MainActivity.class));
//                            finish();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            progressDialog.dismiss();
//                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(Register.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
