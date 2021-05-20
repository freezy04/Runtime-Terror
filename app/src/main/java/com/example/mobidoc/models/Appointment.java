package com.example.mobidoc.models;

import com.google.firebase.firestore.PropertyName;

public class Appointment {

    private String id, PatientUid, Patient_Name, DoctorUid, Doctor_Name, Date_for_appointment,
            Time_for_appointment, Reason_for_appointment, Appointment_Cost, status, notes;

    public Appointment() {
    }

    //without id
    public Appointment(String patientUid, String patient_Name, String doctorUid, String doctor_Name, String date_for_appointment,
                       String time_for_appointment, String reason_for_appointment, String appointment_Cost, String status, String notes) {
        PatientUid = patientUid;
        Patient_Name = patient_Name;
        DoctorUid = doctorUid;
        Doctor_Name = doctor_Name;
        Date_for_appointment = date_for_appointment;
        Time_for_appointment = time_for_appointment;
        Reason_for_appointment = reason_for_appointment;
        Appointment_Cost = appointment_Cost;
        this.status = status;
        this.notes = notes;
    }

//    //with id
//    public Appointment(String id, String patientUid, String patient_Name, String doctorUid, String doctor_Name, String date_for_appointment,
//                       String time_for_appointment, String reason_for_appointment, String appointment_Cost, String status, String notes) {
//        this.id = id;
//        PatientUid = patientUid;
//        Patient_Name = patient_Name;
//        DoctorUid = doctorUid;
//        Doctor_Name = doctor_Name;
//        Date_for_appointment = date_for_appointment;
//        Time_for_appointment = time_for_appointment;
//        Reason_for_appointment = reason_for_appointment;
//        Appointment_Cost = appointment_Cost;
//        this.status = status;
//        this.notes = notes;
//    }

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("PatientUid")
    public String getPatientUid() {
        return PatientUid;
    }

    @PropertyName("PatientUid")
    public void setPatientUid(String patientUid) {
        PatientUid = patientUid;
    }

    @PropertyName("Patient_Name")
    public String getPatient_Name() {
        return Patient_Name;
    }

    @PropertyName("Patient_Name")
    public void setPatient_Name(String patient_Name) {
        Patient_Name = patient_Name;
    }

    @PropertyName("DoctorUid")
    public String getDoctorUid() {
        return DoctorUid;
    }

    @PropertyName("DoctorUid")
    public void setDoctorUid(String doctorUid) {
        DoctorUid = doctorUid;
    }

    @PropertyName("Doctor_Name")
    public String getDoctor_Name() {
        return Doctor_Name;
    }

    @PropertyName("Doctor_Name")
    public void setDoctor_Name(String doctor_Name) {
        Doctor_Name = doctor_Name;
    }

    @PropertyName("Date_for_appointment")
    public String getDate_for_appointment() {
        return Date_for_appointment;
    }

    @PropertyName("Date_for_appointment")
    public void setDate_for_appointment(String date_for_appointment) {
        Date_for_appointment = date_for_appointment;
    }

    @PropertyName("Time_for_appointment")
    public String getTime_for_appointment() {
        return Time_for_appointment;
    }

    @PropertyName("Time_for_appointment")
    public void setTime_for_appointment(String time_for_appointment) {
        Time_for_appointment = time_for_appointment;
    }

    @PropertyName("Reason_for_appointment")
    public String getReason_for_appointment() {
        return Reason_for_appointment;
    }

    @PropertyName("Reason_for_appointment")
    public void setReason_for_appointment(String reason_for_appointment) {
        Reason_for_appointment = reason_for_appointment;
    }

    @PropertyName("Appointment_Cost")
    public String getAppointment_Cost() {
        return Appointment_Cost;
    }

    @PropertyName("Appointment_Cost")
    public void setAppointment_Cost(String appointment_Cost) {
        Appointment_Cost = appointment_Cost;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    @PropertyName("status")
    public void setStatus(String status) {
        this.status = status;
    }
    @PropertyName("notes")
    public String getNotes() {
        return notes;
    }
    @PropertyName("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
