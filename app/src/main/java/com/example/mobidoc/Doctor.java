package com.example.mobidoc;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class Doctor {

    private String first_name,last_name, user_type, phone_num, email ,Qualifications;
    //private ArrayList<String> Patients;

    /* Doctor(String name , String surname , String user , String number , String mail){
        this.first_name= name;
        this.last_name= surname;
        this.user_type= user;
        this.phone_number = number;
        this.email= mail;
    } */

    @PropertyName("first_name")
    public String getFirst_name() {
        return first_name;
    }

    @PropertyName("first_name")
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    @PropertyName("last_name")
    public String getlast_name() {
        return last_name;
    }
    @PropertyName("last_name")
    public void setlast_name(String last_name) {
        this.last_name = last_name;
    }
    @PropertyName("user_type")
    public String getuser_type() {
        return user_type;
    }
    @PropertyName("user_type")
    public void setuser_type(String user_type) {
        this.user_type = user_type;
    }
    @PropertyName("phone_num")
    public String getphone_num() {
        return phone_num;
    }
    @PropertyName("phone_num")
    public void setphone_num(String phone_number) {
        this.phone_num = phone_number;
    }
    @PropertyName("email")
    public String getemail() {
        return email;
    }
    @PropertyName("email")
    public void setemail(String email) {
        this.email = email;
    }

    public String getQualifications() {
        return Qualifications;
    }

    public void setQualifications(String qualifications) {
        Qualifications = qualifications;
    }


}
