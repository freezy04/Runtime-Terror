package com.example.mobidoc.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(JUnit4.class)
public class BookedAppointmentListTest {
    @Test
    public void testEmptyConstructor(){
        BookedAppointmentList booked =   new BookedAppointmentList();
        String anyString = anyString();


        booked.setDate_for_appointment(anyString);
        booked.setDoctorUid(anyString);
        booked.setPatient_Name(anyString);
        booked.setPatientID(anyString);
        booked.setTime_for_appointment(anyString);
        booked.setPatient_Message(anyString);
    }

    @Test
    public void testUidConstructorDoctor(){
        String anyString = anyString();
        BookedAppointmentList booked = new BookedAppointmentList(anyString,anyString,anyString,anyString,anyString,anyString);
        assertEquals(anyString,booked.getDoctorUid());



    }

    @Test
    public  void Testgetters(){

        BookedAppointmentList booked =   new BookedAppointmentList();
        booked.getDate_for_appointment();
        booked.getDoctorUid();
        booked.getPatient_Name();
        booked.getPatientID();
        booked.getTime_for_appointment();
        booked.getPatient_Message();
    }


}