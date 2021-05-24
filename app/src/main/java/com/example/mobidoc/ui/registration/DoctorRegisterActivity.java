package com.example.mobidoc.ui.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Doctor;
import com.example.mobidoc.ui.login.Login;
import com.example.mobidoc.utils.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class DoctorRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //test
    public EditText emailET, passwordET, confirmPasswordET, fNameET, lNameET, qualificationsET, experienceET;
    public Spinner specializationSPN;
    private String specialization;
    public TextView showPasswordTW, showConfirmPasswordTW, haveAccountTW;
    public Button registerBTN;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        initializeActivity();

        //if user already has an account switch to login screen
        haveAccountTW.setOnClickListener(v -> switchToLogin());

        //validate details
        registerBTN.setOnClickListener(v -> registerUser());

    }

    public void switchToLogin(){
        startActivity(new Intent(DoctorRegisterActivity.this, Login.class));
        finish();
    }

    private void initializeActivity() {
        //set up action bar
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Doctor Account Registration");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Paper.init(this); //    @ Dylan 2179115 added this Code to store information locally

        //initialize swing elements
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        confirmPasswordET = findViewById(R.id.confirmPasswordET);
        fNameET = findViewById(R.id.fNameET);
        lNameET = findViewById(R.id.lNameET);
        qualificationsET = findViewById(R.id.qualificationsET);
        experienceET = findViewById(R.id.experienceET);
        specializationSPN = findViewById(R.id.specializationSPN);
        registerBTN = findViewById(R.id.registerBTN);
        showPasswordTW = findViewById(R.id.showPasswordTW);
        showConfirmPasswordTW = findViewById(R.id.showConfirmPasswordTW);
        haveAccountTW = findViewById(R.id.haveAccountTW);

        //Show / Hide Passwords TextInputLayouts
        showPasswordTW.setText(" ");
        showPasswordTW.setOnClickListener(v -> toggleShowPassword(passwordET, showPasswordTW));
        showConfirmPasswordTW.setText(" ");
        showConfirmPasswordTW.setOnClickListener(v -> toggleShowPassword(confirmPasswordET, showConfirmPasswordTW));

        mAuth = FirebaseAuth.getInstance();//get connection to firebase

        //set up progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Doctor...");

        specializationSpinnerSetUp();
    }

    private void specializationSpinnerSetUp() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.doctorSpecializations, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        specializationSPN.setAdapter(adapter);
        specializationSPN.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        specialization = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        specialization = "Other";
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

    private boolean validateExperience(String experience) {
        if (experience.isEmpty()) {
            experienceET.setError("Experience cannot be empty");
            return false;
        }
        Pattern lowerCase = Pattern.compile("\\p{Lower}");//all lowercase letters
        Pattern upperCase = Pattern.compile("\\p{Upper}");//all uppercase letters
        Pattern special = Pattern.compile("\\p{Punct}");//all special characters
        Matcher hasLowerCase = lowerCase.matcher(experience);
        Matcher hasUpperCase = upperCase.matcher(experience);
        Matcher hasSpecial = special.matcher(experience);
        if (hasLowerCase.find() || hasUpperCase.find() || hasSpecial.find()) {
            experienceET.setError("Please use only numbers to indicate years of experience");
            return false;
        }
        return true;
    }

    private boolean validateDetails(String email, String password, String confirm_password, String experience) {
        boolean email_valid = validateEmail(email);
        boolean password_valid = validatePassword(password);
        boolean confirm_password_valid = validateConfirmPassword(confirm_password, password);
        boolean fName_valid = fieldNotNull(fNameET);
        boolean lName_valid = fieldNotNull(lNameET);
        boolean qualifications_valid = fieldNotNull(qualificationsET);
        boolean experience_valid = validateExperience(experience);
        return email_valid && password_valid && confirm_password_valid && fName_valid && lName_valid && qualifications_valid && experience_valid;
    }

    private void addUserToDB(Doctor doc){
        progressDialog.dismiss();
        FirebaseUser user = mAuth.getCurrentUser();

        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(DoctorRegisterActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DoctorRegisterActivity.this, "Verification email failed", Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(doc.getUser_type() + "s");
        doc.setUid(user.getUid());
        reference.child(doc.getUid()).setValue(doc);
        Toast.makeText(DoctorRegisterActivity.this, "Registered...\n" + user.getEmail(), Toast.LENGTH_SHORT).show();

        /* ---- Dylan 2179115 added this code ----*/
        Paper.book().write(Utilities.Doctor, "Doc");
        startActivity(new Intent(DoctorRegisterActivity.this, Login.class));
        finish();
        Toast.makeText(DoctorRegisterActivity.this, "Please Login now", Toast.LENGTH_LONG).show();
    }

    private void registerUser() {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString();//should we trim here?
        String confirmPassword = confirmPasswordET.getText().toString();
        String fName = fNameET.getText().toString().trim();
        String lName = lNameET.getText().toString().trim();
        String qualifications = qualificationsET.getText().toString().trim();
        String experience = experienceET.getText().toString().trim();

        if (!validateDetails(email, password, confirmPassword, experience)) {
            return;
        }
        Doctor doc = new Doctor(fName, lName, "Doctor", email, qualifications, experience, specialization);
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(doc.getEmail(), password)
                .addOnCompleteListener(DoctorRegisterActivity.this, task -> {
                    if (task.isSuccessful()) {
                        addUserToDB(doc);
                    } else {
                        // If sign in fails, display a message to the user.
                        progressDialog.dismiss();
                        Toast.makeText(DoctorRegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(DoctorRegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
