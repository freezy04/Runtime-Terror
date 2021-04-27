package com.example.mobidoc.models;

import com.google.firebase.firestore.PropertyName;

public class Doctor {

    private String uid, first_name,last_name, user_type, email , qualifications, experience, specialization;
    //private ArrayList<String> Patients;

    public Doctor(){}

    public Doctor(String uid, String first_name, String last_name, String user_type, String email, String qualifications, String experience, String specialization) {//with uid
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
        this.email = email;
        this.qualifications = qualifications;
        this.experience = experience;
        this.specialization = specialization;
    }

    public Doctor(String first_name, String last_name, String user_type, String email, String qualifications, String experience, String specialization) {//without uid
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
        this.email = email;
        this.qualifications = qualifications;
        this.experience = experience;
        this.specialization = specialization;
    }

    @PropertyName("uid")
    public String getUid() {
        return uid;
    }
    @PropertyName("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @PropertyName("first_name")
    public String getFirst_name() {
        return first_name;
    }
     @PropertyName("first_name")
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    @PropertyName("last_name")
    public String getLast_name() {
        return last_name;
    }
    @PropertyName("last_name")
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    @PropertyName("user_type")
    public String getUser_type() {
        return user_type;
    }
    @PropertyName("user_type")
    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
    @PropertyName("email")
    public String getEmail() {
        return email;
    }
    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("qualifications")
    public String getQualifications() {
        return qualifications;
    }
    @PropertyName("qualifications")
    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    @PropertyName("experience")
    public String getExperience() {return experience;}
    @PropertyName("experience")
    public void setExperience(String experience) {this.experience = experience;}

    @PropertyName("specialization")
    public String getSpecialization() {return specialization;}
    @PropertyName("specialization")
    public void setSpecialization(String specialization) {this.specialization = specialization;}


}
