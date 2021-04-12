package com.example.mobidoc;

public class User {

    private String Name , Surname , Email , PhoneNumber, User_type;

   /* private User(String name , String surname , String email , String phoneNumber, String user_type){

        Name = name;
        Surname = surname;
        Email = email;
        PhoneNumber = phoneNumber;

    } */

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }
}
