package com.example.mobidoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class PatientRegisterActivityOne extends AppCompatActivity {

    private EditText emailET, passwordET, confirmPasswordET, fNameET, lNameET, ageET, sexET;
    private TextView haveAccountTW, showPasswordTW, showConfirmPasswordTW;
    private Button registerBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register_one);

        networkAvailabilityCheck();
        initializeActivity();

        //Show / Hide Passwords
        showPasswordTW.setText(" ");
        showPasswordTW.setOnClickListener(v -> toggleShowPassword(passwordET, showPasswordTW));
        showConfirmPasswordTW.setText(" ");
        showConfirmPasswordTW.setOnClickListener(v -> toggleShowPassword(confirmPasswordET, showConfirmPasswordTW));

        //if user already has an account switch to login screen
        haveAccountTW.setOnClickListener(v -> {
            startActivity(new Intent(PatientRegisterActivityOne.this, Login_activity.class));
            finish();
        });

        //validate details
        registerBTN.setOnClickListener(v -> {
            if (validateDetails(emailET, passwordET, confirmPasswordET, fNameET, lNameET, ageET, sexET, true)) {
                // Check if user is signed in (non-null) and update UI accordingly.
                Intent patientRegistrationStepTwo = new Intent(PatientRegisterActivityOne.this, PatientRegisterActivityTwo.class);
                patientRegistrationStepTwo.putExtra("email", emailET.getText().toString().trim());
                patientRegistrationStepTwo.putExtra("password", passwordET.getText().toString());//should we trim here?
                patientRegistrationStepTwo.putExtra("fName", fNameET.getText().toString().trim());
                patientRegistrationStepTwo.putExtra("lName", lNameET.getText().toString().trim());
                patientRegistrationStepTwo.putExtra("age", ageET.getText().toString().trim());
                patientRegistrationStepTwo.putExtra("sex", sexET.getText().toString().trim());
                startActivity(patientRegistrationStepTwo);
                finish();
            }
        });

    }

    private void initializeActivity() {
        //set up action bar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Patient Account Registration");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Paper.init(this); //    @ Dylan 2179115 added this Code to store information locally

        //initialize swing elements
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        fNameET = findViewById(R.id.fNameET);
        lNameET = findViewById(R.id.lNameET);
        ageET = findViewById(R.id.ageET);
        sexET = findViewById(R.id.sexET);
        registerBTN = findViewById(R.id.registerBTN);
        haveAccountTW = findViewById(R.id.haveAccountTW);
        showPasswordTW = findViewById(R.id.showPasswordTW);
        showConfirmPasswordTW = findViewById(R.id.showConfirmPasswordTW);
    }

    private void networkAvailabilityCheck() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            startActivity(new Intent(PatientRegisterActivityOne.this, No_Internet.class));
        }
    }

    private boolean validateEmail(String email, boolean displayErrors) {//if email address is not in valid format, displays error
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (displayErrors) {
                emailET.setError("Invalid Email");
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
                passwordET.setError("Password must be between 8-20 characters, and must include at least one lowercase letter, uppercase" +
                        " letter, number and special character");
            }
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(String confirm_password, String password, boolean displayErrors) {
        if (confirm_password.equals(password)) {
            return true;
        }
        if (displayErrors) {
            confirmPasswordET.setError("Passwords must match");
        }
        return false;
    }

    private boolean fieldNotNull(EditText et, boolean displayErrors) {//checks if personal information fields are empty, if so displays the appropriate error(s)
        if (et.getText().toString().trim().isEmpty()) {
            if (displayErrors) {
                et.setError("Cannot be empty");
            }
            return false;
        }
        return true;
    }

    private boolean validateAge(String age, boolean displayErrors) {
        if (age.isEmpty()) {
            if (displayErrors) {
                ageET.setError("Age cannot be empty");
            }
            return false;
        }
        Pattern lowerCase = Pattern.compile("\\p{Lower}");//all lowercase letters
        Pattern upperCase = Pattern.compile("\\p{Upper}");//all uppercase letters
        Pattern special = Pattern.compile("\\p{Punct}");//all special characters
        Matcher hasLowerCase = lowerCase.matcher(age);
        Matcher hasUpperCase = upperCase.matcher(age);
        Matcher hasSpecial = special.matcher(age);
        if (hasLowerCase.find() || hasUpperCase.find() || hasSpecial.find()) {
            if (displayErrors) {
                ageET.setError("Please use only numbers to indicate age (in years)");
            }
            return false;
        }
        return true;
    }

    private boolean validateDetails(EditText emailET, EditText passwordET, EditText confirmPasswordET, EditText fNameET, EditText lNameET, EditText ageET, EditText sexET, boolean displayErrors) {
        boolean email_valid = validateEmail(emailET.getText().toString().trim(), displayErrors);
        boolean password_valid = validatePassword(passwordET.getText().toString().trim(), displayErrors);
        boolean confirm_password_valid = validateConfirmPassword(confirmPasswordET.getText().toString().trim(), passwordET.getText().toString().trim(), displayErrors);
        boolean fName_valid = fieldNotNull(fNameET, displayErrors);
        boolean lName_valid = fieldNotNull(lNameET, displayErrors);
        boolean age_valid = validateAge(ageET.getText().toString().trim(), displayErrors);
        boolean sex_valid = fieldNotNull(sexET, displayErrors);
        return email_valid && password_valid && confirm_password_valid && fName_valid && lName_valid && age_valid && sex_valid;
    }

    private void toggleShowPassword(EditText password, TextView showPassword) {
        if (showPassword.getText().equals(" ")) {
            showPassword.setText(".");
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            password.setSelection(password.length());
            Drawable d = getResources().getDrawable(R.drawable.show_password);
            showPassword.setBackground(d);
        } else {
            showPassword.setText(" ");
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setSelection(password.length());
            Drawable d = getResources().getDrawable(R.drawable.hide_password);
            showPassword.setBackground(d);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}

