package com.example.mobidoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_activity extends AppCompatActivity {
    private Button Login_Button;
    private EditText User_email , User_password;
    private TextView Show_password_visibility;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);




        /*** NetwWork Avaivilble ***/
        if(!isNetworkAvailable()){
            startActivity(new Intent(Login_activity.this,No_Internet.class));
        }

        /*** User clicked remember me ***/

        User_email = (EditText) findViewById(R.id.Username);
        User_password = (EditText) findViewById(R.id.Password);
        Show_password_visibility = (TextView)findViewById(R.id.show_pass);

        Login_Button = (Button) findViewById(R.id.loginBtn);

        final String email = User_email.getText().toString();
        final String password = User_password.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance(); //get instance of Firebase Authorisation


        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null) {
            RememberMe(firebaseAuth); // Check if user is signed in (non-null) and update UI accordingly.
        }

        /*** Authenticate User and Sign Them in ***/

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firebaseAuth.getCurrentUser() != null && ValidateDetails(User_email , User_password)) {
                    LoginUser(firebaseAuth, email, password);
                }
            }
        });

        Show_password_visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle_password(User_password);
            }
        });



    }

    private void RememberMe(FirebaseAuth firebaseAuth){
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(Login_activity.this,MainActivity.class));
            finish();
        }
    }

    public void toggle_password(EditText password ){
        TextView show_password_visibility = findViewById(R.id.show_pass);
        if(show_password_visibility.getText().equals(" ")){
            show_password_visibility.setText(".");
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            password.setSelection(password.length());
            Drawable d = getResources().getDrawable(R.drawable.show_password);
            show_password_visibility.setBackground(d);
        }
        else{
            show_password_visibility.setText(" ");
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setSelection(password.length());
            Drawable d = getResources().getDrawable(R.drawable.hide_password);
            show_password_visibility.setBackground(d);
        }
    }

    private void LoginUser(FirebaseAuth firebaseAuth, String email , String password){

        firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(Login_activity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Login Details , Please try again", Toast.LENGTH_LONG);
                        }
                        else{
                            startActivity(new Intent(Login_activity.this , MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private boolean ValidateDetails(TextView Email , TextView Password) {

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
}