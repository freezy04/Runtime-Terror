package com.example.mobidoc.utils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobidoc.models.Doctor;
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

        public static FirebaseUser getCurrentUser(){
                mAuth = FirebaseAuth.getInstance();
                return mAuth.getCurrentUser();
        }


//        public static String getUID(){
//                FirebaseUser user = getCurrentUser();
//                return user.getUid();
//        }
//
//        public static void returnDoctor(String UID, String USER_TYPE){
//
//
//                FirebaseDatabase db = FirebaseDatabase.getInstance();
//                DatabaseReference ref = db.getReference();
//                DatabaseReference user_ref = ref.child(USER_TYPE); //Doctor or Patient
//
//
//                user_ref.orderByKey().equalTo(UID).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                //Doctor D = new Doctor();
//
//                                for ( DataSnapshot ds : snapshot.getChildren()) {
//                                        com.example.mobidoc.models.Doctor doctor = ds.getValue(Doctor.class);
//                                        //creates an instance of doctor and assigns the firebase data to it's respective columns
//                                        Log.d("TestingApp","This is " + ds.getValue());
//                                        Log.d("TestingApp","This is " +  doctor.getEmail());
//                                        Log.d("TestingApp","This is " + doctor.getLast_name());
//                                        Log.d("TestingApp","This is " + doctor.getUser_type());
//                                        Log.d("TestingApp","This is " + doctor.getFirst_name());
//
//
//
//                                }
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                });
//
//
//        }

}
