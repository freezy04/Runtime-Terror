package com.example.mobidoc.models;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class BookedAppointmentListTest {


    @Test
    public void testEmptyConstructor(){


        BookedAppointmentList book = new BookedAppointmentList();
        String anyString = anyString();
        book.setDate_for_appointment(anyString);
        book.setDoctorUid(anyString);
        book.setPatient_Name(anyString);
        book.setPatientID(anyString);
        book.setTime_for_appointment(anyString);
        book.setPatient_Message(anyString);
    }

    @Test
    public void testUidConstructor(){

        String anyString = anyString();
        BookedAppointmentList book = new BookedAppointmentList(anyString,anyString,anyString,anyString,anyString,anyString);
        assertEquals(anyString,book.getDoctorUid());
    }

    @Test
    public  void testGetters(){
        String anyString = anyString();
        BookedAppointmentList book = new BookedAppointmentList(anyString,anyString,anyString,anyString,anyString,anyString);
        book.getDate_for_appointment();
        book.getDoctorUid();
        book.getPatient_Name();
        book.getPatientID();
        book.getTime_for_appointment();
        book.getPatient_Message();
    }

}