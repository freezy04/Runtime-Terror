package com.example.mobidoc.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class PatientTest {

    @Test
    public void testUIDConstructor(){
        String s = anyString();
        Patient pat = new Patient(s,s,s,s,s,s,s,s,s,s,s);
        assertEquals(s, pat.getUid());
    }

    @Test
    public void testNonUIDConstructor(){
        String s = anyString();
        Patient pat = new Patient(s,s,s,s,s,s,s,s,s,s);
        assertEquals(s, pat.getFirst_name());
    }

    @Test
    public void testSetters(){
        Patient pat = new Patient();
        String s = anyString();
        pat.setUid(s);
        pat.setFirst_name(s);
        pat.setLast_name(s);
        pat.setUser_type(s);
        pat.setEmail(s);
        pat.setAge(s);
        pat.setSex(s);
        pat.setDiseaseHistory(s);
        pat.setMedicationHistory(s);
        pat.setAllergies(s);
        pat.setCurrentMedication(s);
    }

    @Test
    public void testGetters(){
        Patient pat = new Patient();
        pat.getUid();
        pat.getFirst_name();
        pat.getLast_name();
        pat.getUser_type();
        pat.getEmail();
        pat.getAge();
        pat.getSex();
        pat.getDiseaseHistory();
        pat.getMedicationHistory();
        pat.getAllergies();
        pat.getCurrentMedication();;
    }

}