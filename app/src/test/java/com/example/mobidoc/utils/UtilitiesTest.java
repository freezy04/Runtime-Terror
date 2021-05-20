package com.example.mobidoc.utils;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UtilitiesTest {

    @Test
    public void testUtils(){
        FirebaseUser fb = Utilities.getCurrentUser();
    }

}