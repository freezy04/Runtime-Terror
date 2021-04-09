package com.example.mobidoc;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Utilities {

        public static final String USER_KEY = "default";
        public static final String Patient = "patient";
        public static final String Doctor = "doctor";


        private static FirebaseAuth mAuth;

        public static FirebaseUser getMain(){
                mAuth = FirebaseAuth.getInstance();
                return mAuth.getCurrentUser();
        }



}
