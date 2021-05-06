package com.example.mobidoc.models;

/**
 * Created by Neo on 24-Apr-21.
 */

public class BookedAppointmentList {


    private String Time_for_appointment;
    private String DoctorUid;
    private String PatientID;
    private String Patient_Message;
    private String Patient_Name;
    private String Date_for_appointment;

    public BookedAppointmentList() {
    }

    public BookedAppointmentList( String DoctorUid,String Date_for_appointment, String Patient_Message,String Patient_Name, String Time_for_appointment, String patientID) {
        this.Date_for_appointment =Date_for_appointment;
        this.Time_for_appointment = Time_for_appointment;
        this.DoctorUid = DoctorUid;
        this.PatientID = patientID;
        this.Patient_Name = Patient_Name;
        this.Patient_Message = Patient_Message;
    }





    public String getPatient_Message() {
        return Patient_Message;
    }
    public void setPatient_Message(String Patient_Message) {
        this.Patient_Message = Patient_Message;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }
    public void setPatient_Name(String Patient_Name) {
        this.Patient_Name = Patient_Name;
    }

    public String getDate_for_appointment() {
        return Date_for_appointment;
    }

    public void setDate_for_appointment(String Date_for_appointment) {
        this.Date_for_appointment =Date_for_appointment;
    }

    public String getTime_for_appointment() {
        return Time_for_appointment;
    }

    public void setTime_for_appointment(String time_for_appointment) {
        this.Time_for_appointment = time_for_appointment;
    }

    public String getDoctorUid() {
        return DoctorUid;
    }

    public void setDoctorUid(String DoctorUid) {
        this.DoctorUid= DoctorUid;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        this.PatientID = patientID;
    }
}

