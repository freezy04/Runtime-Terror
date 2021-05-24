package com.example.mobidoc.models;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;



public class AppointmentTest {

    @Test
    public void testUIDConstructor(){
        String s = anyString();
        Appointment app = new Appointment(s,s,s,s,s,s,s,s,s,s);
        assertEquals(s, app.getDoctor_Name());
    }

    @Test
    public void testSetters(){
        Appointment app = new Appointment();
        String s = anyString();
        app.setId(s);
        app.setPatientUid(s);
        app.setPatient_Name(s);
        app.setDoctorUid(s);
        app.setDoctor_Name(s);
        app.setDate_for_appointment(s);
        app.setTime_for_appointment(s);
        app.setReason_for_appointment(s);
        app.setAppointment_Cost(s);
        app.setStatus(s);
        app.setNotes(s);
    }

    @Test
    public void testGetters(){

        Appointment app = new Appointment();
        app.getId();
        app.getPatientUid();
        app.getPatient_Name();
        app.getDoctorUid();
        app.getDoctor_Name();
        app.getDate_for_appointment();
        app.getTime_for_appointment();
        app.getReason_for_appointment();
        app.getAppointment_Cost();
        app.getStatus();
        app.getNotes();
    }

}