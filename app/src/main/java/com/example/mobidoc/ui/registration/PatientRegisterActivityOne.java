package com.example.mobidoc.ui.registration;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.ui.login.Login;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class PatientRegisterActivityOne extends AppCompatActivity {

    //test
    public EditText emailET, passwordET, confirmPasswordET, fNameET, lNameET, ageET, sexET;
    public TextView haveAccountTW, showPasswordTW, showConfirmPasswordTW;
    public Button nextBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register_one);

        initializeActivity();

        //if user already has an account switch to login screen
        haveAccountTW.setOnClickListener(v -> switchToLogin());

        //validate details
        nextBTN.setOnClickListener(v -> moveToNextStep());

    }

    public void switchToLogin(){
        startActivity(new Intent(PatientRegisterActivityOne.this, Login.class));
        finish();
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
        nextBTN = findViewById(R.id.nextBTN);
        haveAccountTW = findViewById(R.id.haveAccountTW);
        showPasswordTW = findViewById(R.id.showPasswordTW);
        showConfirmPasswordTW = findViewById(R.id.showConfirmPasswordTW);

        //Show / Hide Passwords
        showPasswordTW.setText(" ");
        showPasswordTW.setOnClickListener(v -> toggleShowPassword(passwordET, showPasswordTW));
        showConfirmPasswordTW.setText(" ");
        showConfirmPasswordTW.setOnClickListener(v -> toggleShowPassword(confirmPasswordET, showConfirmPasswordTW));
    }

    private boolean validateEmail(String email) {//if email address is not in valid format, displays error
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Invalid Email");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {//checks if password is in correct format, if not displays error message
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
            passwordET.setError("Password must be between 8-20 characters, and must include at least one lowercase letter, uppercase" +
                        " letter, number and special character");
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(String confirm_password, String password) {
        if (confirm_password.equals(password)) {
            return true;
        }
        confirmPasswordET.setError("Passwords must match");
        return false;
    }

    private boolean fieldNotNull(EditText et) {//checks if personal information fields are empty, if so displays the appropriate error(s)
        if (et.getText().toString().trim().isEmpty()) {
            et.setError("Cannot be empty");
            return false;
        }
        return true;
    }

    private boolean validateAge(String age) {
        if (age.isEmpty()) {
            ageET.setError("Age cannot be empty");
            return false;
        }
        Pattern lowerCase = Pattern.compile("\\p{Lower}");//all lowercase letters
        Pattern upperCase = Pattern.compile("\\p{Upper}");//all uppercase letters
        Pattern special = Pattern.compile("\\p{Punct}");//all special characters
        Matcher hasLowerCase = lowerCase.matcher(age);
        Matcher hasUpperCase = upperCase.matcher(age);
        Matcher hasSpecial = special.matcher(age);
        if (hasLowerCase.find() || hasUpperCase.find() || hasSpecial.find()) {
            ageET.setError("Please use only numbers to indicate age (in years)");
            return false;
        }
        return true;
    }

    private boolean validateDetails(EditText emailET, EditText passwordET, EditText confirmPasswordET, EditText fNameET, EditText lNameET, EditText ageET, EditText sexET) {
        boolean email_valid = validateEmail(emailET.getText().toString().trim());
        boolean password_valid = validatePassword(passwordET.getText().toString().trim());
        boolean confirm_password_valid = validateConfirmPassword(confirmPasswordET.getText().toString().trim(), passwordET.getText().toString().trim());
        boolean fName_valid = fieldNotNull(fNameET);
        boolean lName_valid = fieldNotNull(lNameET);
        boolean age_valid = validateAge(ageET.getText().toString().trim());
        boolean sex_valid = fieldNotNull(sexET);
        return email_valid && password_valid && confirm_password_valid && fName_valid && lName_valid && age_valid && sex_valid;
    }

    private void moveToNextStep() {
        if (validateDetails(emailET, passwordET, confirmPasswordET, fNameET, lNameET, ageET, sexET)) {
            // Check if user is signed in (non-null) and update UI accordingly.
            Intent patientRegistrationStepTwo = new Intent(PatientRegisterActivityOne.this, PatientRegisterActivityTwo.class);
            patientRegistrationStepTwo.putExtra("email", emailET.getText().toString().trim());
            patientRegistrationStepTwo.putExtra("password", passwordET.getText().toString());//should we trim here?
            patientRegistrationStepTwo.putExtra("fName", fNameET.getText().toString().trim());
            patientRegistrationStepTwo.putExtra("lName", lNameET.getText().toString().trim());
            patientRegistrationStepTwo.putExtra("age", ageET.getText().toString().trim());
            patientRegistrationStepTwo.putExtra("sex", sexET.getText().toString().trim());
            startActivity(patientRegistrationStepTwo);
            }
    }

    public void toggleShowPassword(EditText password, TextView showPassword) {
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

