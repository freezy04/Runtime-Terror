package com.example.mobidoc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

/*** Note to the UI Team:
 *
 *  Need two EditTexts ( min ) for Password and Login
 *  Email : give id Username
 *  Password : give id Password
 *  Need at least One TextView with ID = show_pass  , if you decided to move it please notify me
 *  Button for Login with ID = LoginBtn
 *  ***/
public class Login_activity extends AppCompatActivity {
    public static String User_type;
    private FirebaseAuth firebaseAuth;
    public static FirebaseUser MainUser;
    private EditText User_email, User_password;
    private CheckBox ChRemember;
    private TextView User_forgot_password, Create_account;


    ProgressDialog progressDialogForgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        Paper.init(this);


        /*** NetwWork Avaivilble ***/
        if (!isNetworkAvailable()) {
            System.err.println("No Internet");
            startActivity(new Intent(Login_activity.this, No_Internet.class));
        }

        //databaseReference = FirebaseAuth.getInstance().getReference().child();

        User_email = (EditText) findViewById(R.id.Username);
        User_password = (EditText) findViewById(R.id.Password);
        User_forgot_password = (TextView) findViewById(R.id.for_pass);
        User_forgot_password = (TextView) findViewById(R.id.for_pass);
        Create_account = (TextView) findViewById(R.id.create_user);
        TextView show_password_visibility = (TextView) findViewById(R.id.show_pass);

        ChRemember = findViewById(R.id.checkBox);
        Button login_Button = (Button) findViewById(R.id.loginBtn);

        String email = User_email.getText().toString();
        String password = User_password.getText().toString();




        firebaseAuth = FirebaseAuth.getInstance();

        /*** This checks whether the user cleared their cache before entering the app or just minimised the app ****/


        /* Remember Me Function */
        String User_key = Paper.book().read(Utilities.USER_KEY);
        String Doctor = Paper.book().read(Utilities.Doctor);



        if (User_key != null) {

            if (Doctor != null) {
                //user is a doctor
                startActivity(new Intent(Login_activity.this, Doctor_Dashboard.class));
                finish();
            }
            else {
                //user is a patient
                startActivity(new Intent(Login_activity.this, Patient_Dashboard.class));
                finish();
            }
        }

        /* End of Remember Me */


        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = User_email.getText().toString(), Pass = User_password.getText().toString();
                if (ValidateDetails(User_email, User_password)) {

                    /**** Remember me ****/

                    if (ChRemember.isChecked()) {
                        Paper.book().write(Utilities.USER_KEY, "booked");
                    }
                    firebaseAuth = FirebaseAuth.getInstance();
                    LoginUser(firebaseAuth, Email, Pass);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Something Went Wrong .. sob .. sob", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


        show_password_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle_password(User_password);
            }
        });

        User_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowForgotPasswordDialog();
            }
        });

        Create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_activity.this, Register.class));
                finish();
            }
        });

        progressDialogForgotpassword = new ProgressDialog(this);
    }
    // ********************************************************************************************************************************************************** //

    // sets up a Dialog that enables user to type in their registered email for requesting a new link that resets their passwords
    private void ShowForgotPasswordDialog() {
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
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String GetEmail = RecoverEmail.getText().toString().trim();
                BeginResetting(GetEmail);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
        ;
    }

    // Function show underlying  progress of sending email to the device and show whether an email was sent or not
    private void BeginResetting(String getemaIl) {

        progressDialogForgotpassword.setMessage("Sending Email...");
        progressDialogForgotpassword.show();
        firebaseAuth.sendPasswordResetEmail(getemaIl).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialogForgotpassword.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(Login_activity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login_activity.this, "Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialogForgotpassword.dismiss();
                Toast.makeText(Login_activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show(); // Show an error if everything failed
            }
        });
    }


    public void toggle_password(EditText password) {
        TextView show_password_visibility = findViewById(R.id.show_pass);
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

    private void LoginUser(FirebaseAuth firebaseAuth, String email, String password) {
        String Email = User_email.getText().toString();
        String Password = User_password.getText().toString();


        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(Login_activity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Login Details , Please try again", Toast.LENGTH_LONG);
                            toast.show();
                        } else {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            MainUser = user; //Master User
                            assert user != null;
                            String UID = user.getUid();

                            LoginUserAs(UID, "Doctors");


                        }
                    }
                });
    }

    private boolean ValidateDetails(TextView Email, TextView Password) {

        // Get email id and password
        String getEmailId = Email.getText().toString();
        String getPassword = Password.getText().toString();
        final String regEx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(getEmailId);

        // Check for both field is empty or not
        if (getEmailId.equals("") & (getEmailId.length() == 0) & !m.matches()
                & getPassword.equals("") & (getPassword.length() == 0)) {
            Email.setError("Invalid Email");
            Password.setError("Invalid Password");
            return false;
        }

        return true;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void LoginUserAs(String UID, String USER_TYPE) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        DatabaseReference user_ref = ref.child(USER_TYPE);

        /* Create user from info in Database ****/
        user_ref.orderByKey().equalTo(UID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    Log.d("MyTag", "User is a Doctor :)");
                    User_type = "Doctors";
                    Paper.book().write(Utilities.Doctor, "True"); // used for remember me :D
                    startActivity(new Intent(Login_activity.this, Doctor_Dashboard.class));
                    finish();
                }
                else {
                    Log.d("MyTag", "User is a Patient:)");
                    User_type = "Patients";
                    startActivity(new Intent(Login_activity.this, Patient_Dashboard.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}