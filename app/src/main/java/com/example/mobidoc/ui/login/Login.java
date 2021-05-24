package com.example.mobidoc.ui.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobidoc.R;
import com.example.mobidoc.ui.MainActivity;
import com.example.mobidoc.ui.No_Internet;
import com.example.mobidoc.ui.dashboards.Doctor_Dashboard;
import com.example.mobidoc.ui.dashboards.Patient_Dashboard;
import com.example.mobidoc.utils.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;


public class Login extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    public EditText User_email, User_password;
    public CheckBox ChRemember;
    public Button login_Button;
    public TextView User_forgot_password, Create_account , show_password_visibility;
    ProgressDialog progressdialogforgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);
        NavBar();
        CheckInternet();

        User_email = findViewById(R.id.Username);
        User_password = findViewById(R.id.Password);
        User_forgot_password = findViewById(R.id.for_pass);
        Create_account = findViewById(R.id.create_user);
        show_password_visibility = findViewById(R.id.show_pass);
        ChRemember = findViewById(R.id.checkBox);
        login_Button = findViewById(R.id.loginBtn);

        RememberMe();

        login_Button.setOnClickListener(v -> {
            if (ValidateDetails(User_email, User_password)) { LoginUser(); }

            else { Toast.makeText(Login.this, "Invalid Login Details", Toast.LENGTH_SHORT).show(); }
        });


        show_password_visibility.setOnClickListener(v -> toggle_password(User_password));

        User_forgot_password.setOnClickListener(v -> Show_ForgotPassword_ProgressDialog());

        Create_account.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, MainActivity.class));   finish();
        });

        progressdialogforgotpassword = new ProgressDialog(this);
    }
    // ********************************************************************************************************************************************************** //

    // sets up a Dialog that enables user to type in their registered email for requesting a new link that resets their passwords
    public void Show_ForgotPassword_ProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password");

        LinearLayout linearLayout = new LinearLayout(this);
        EditText RecoverEmail = new EditText(this);
        RecoverEmail.setHint("Email");
        RecoverEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        RecoverEmail.setMinEms(16);
        linearLayout.addView(RecoverEmail);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);

        // Button for resetting password
        builder.setPositiveButton("Reset", (dialog, which) -> {
            String GetEmail = RecoverEmail.getText().toString().trim();

            if(GetEmail.isEmpty())
            {
                Toast.makeText(Login.this, "Please enter email, Try again...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                BeginResettingPassword(GetEmail);

            }
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();

    }

    // Function show underlying  progress of sending email to the device and show whether an email was sent or not
    private void BeginResettingPassword(String getemaIl) {

        progressdialogforgotpassword.setMessage("Sending Email...");
        progressdialogforgotpassword.show(); // Shows action Bar
        firebaseAuth.sendPasswordResetEmail(getemaIl).addOnCompleteListener(task -> {
            progressdialogforgotpassword.dismiss();
            if (task.isSuccessful()) {
                Toast.makeText(Login.this, "Email Sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "Failed...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            progressdialogforgotpassword.dismiss();
            Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show(); // Show an error if everything failed
        });
    }


    public void toggle_password(EditText password) {

        if (show_password_visibility.getText().equals(" ")) {
            show_password_visibility.setText(".");
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            password.setSelection(password.length());
            Drawable d = getResources().getDrawable(R.drawable.show_password);
            show_password_visibility.setBackground(d);
        } else {
            show_password_visibility.setText(" ");
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setSelection(password.length());
            Drawable d = getResources().getDrawable(R.drawable.hide_password);
            show_password_visibility.setBackground(d);
        }
    }

    public  void LoginUser() {

        firebaseAuth = FirebaseAuth.getInstance();
        String Email = User_email.getText().toString();
        String Password = User_password.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(Login.this,
                task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Incorrect Login Details", Toast.LENGTH_SHORT).show();
                    } else {

                        if(ChRemember.isChecked()){ //Remember me
                            Paper.book().write(Utilities.USER_KEY,"True");
                        }

                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        LoginUserAs(user.getUid());
                    }
                });
    }

    public boolean ValidateDetails(EditText Email, EditText Password) {

        boolean email = true, pass=true;
        String getEmailId = Email.getText().toString();
        String getPassword = Password.getText().toString();
        final String regEx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(getEmailId);


        if(!m.matches()){
            Email.setError("Invalid Email : Please enter a valid email");
            email = false;
        }

        if(getEmailId.equals("") || (getEmailId.length() == 0) ||  (!m.matches()) ){
            Email.setError("Invalid Email : Please enter a valid email");
            email = false;
        }

        if(getPassword.equals("") || (getPassword.length() < 8)){
            Password.setError("Invalid Password : Length cannot be less than 8");
            pass = false;
        }

        return (email & pass);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void LoginUserAs(String UID) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        DatabaseReference user_ref = ref.child("Doctors");

        /* Create user from info in Database ****/
        user_ref.orderByKey().equalTo(UID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    Paper.book().write(Utilities.Doctor, "True"); // used for remember me :D
                    startActivity(new Intent(Login.this, Doctor_Dashboard.class));
                    finish();
                }
                else {

                    startActivity(new Intent(Login.this, Patient_Dashboard.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {   }
        });

    }

    public void NavBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    public void CheckInternet(){
        if (!isNetworkAvailable()) {
            startActivity(new Intent(Login.this, No_Internet.class));
        }
    }

    public void RememberMe(){
        String User_key = Paper.book().read(Utilities.USER_KEY);
        String Doctor = Paper.book().read(Utilities.Doctor);

        if (User_key != null) {

            if (Doctor != null) {
                //user is a doctor
                startActivity(new Intent(Login.this, Doctor_Dashboard.class));
                finish();
            }
            else {
                //user is a patient
                startActivity(new Intent(Login.this, Patient_Dashboard.class));
                finish();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
