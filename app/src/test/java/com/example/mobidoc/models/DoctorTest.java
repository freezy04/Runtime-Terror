package com.example.mobidoc.models;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class DoctorTest {

    @Test
    public void testUIDConstructor(){
        String s = anyString();
        Doctor doc = new Doctor(s,s,s,s,s,s,s,s);
        assertEquals(s, doc.getUid());
    }

    @Test
    public void testNonUIDConstructor(){
        String s = anyString();
        Doctor doc = new Doctor(s,s,s,s,s,s,s);
        assertEquals(s, doc.getFirst_name());
    }

    @Test
    public void testSetters(){
        Doctor doc = new Doctor();
        String s = anyString();
        doc.setUid(s);
        doc.setFirst_name(s);
        doc.setLast_name(s);
        doc.setUser_type(s);
        doc.setEmail(s);
        doc.setQualifications(s);
        doc.setExperience(s);
        doc.setSpecialization(s);
    }

    @Test
    public void testGetters(){
        Doctor doc = new Doctor();
        doc.getUid();
        doc.getFirst_name();
        doc.getLast_name();
        doc.getUser_type();
        doc.getEmail();
        doc.getQualifications();
        doc.getExperience();
        doc.getSpecialization();
    }

}