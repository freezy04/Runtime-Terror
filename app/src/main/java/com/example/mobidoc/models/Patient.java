package com.example.mobidoc.models;

import com.google.firebase.firestore.PropertyName;

public class Patient {

    private String uid, first_name,last_name, user_type, email , age, sex, diseaseHistory, medicationHistory, allergies, currentMedication;

    public Patient(){}

    public Patient(String uid, String first_name, String last_name, String user_type, String email, String age,
                   String sex, String diseaseHistory, String medicationHistory, String allergies, String currentMedication) {//with uid
        this.uid = uid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.diseaseHistory = diseaseHistory;
        this.medicationHistory = medicationHistory;
        this.allergies = allergies;
        this.currentMedication = currentMedication;
    }

    public Patient(String first_name, String last_name, String user_type, String email, String age,
                   String sex, String diseaseHistory, String medicationHistory, String allergies, String currentMedication) {//without uid
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.diseaseHistory = diseaseHistory;
        this.medicationHistory = medicationHistory;
        this.allergies = allergies;
        this.currentMedication = currentMedication;
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
    @PropertyName("age")
    public String getAge() {
        return age;
    }
    @PropertyName("age")
    public void setAge(String age) {
        this.age = age;
    }
    @PropertyName("sex")
    public String getSex() {
        return sex;
    }
    @PropertyName("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }
    @PropertyName("disease_history")
    public String getDiseaseHistory() {
        return diseaseHistory;
    }
    @PropertyName("disease_history")
    public void setDiseaseHistory(String diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }
    @PropertyName("medication_history")
    public String getMedicationHistory() {
        return medicationHistory;
    }
    @PropertyName("medication_history")
    public void setMedicationHistory(String medicationHistory) {
        this.medicationHistory = medicationHistory;
    }
    @PropertyName("allergies")
    public String getAllergies() {
        return allergies;
    }
    @PropertyName("allergies")
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    @PropertyName("CurrentMedication")
    public String getCurrentMedication() {
        return currentMedication;
    }
    @PropertyName("CurrentMedication")
    public void setCurrentMedication(String currentMedication) {
        this.currentMedication = currentMedication;
    }
}
