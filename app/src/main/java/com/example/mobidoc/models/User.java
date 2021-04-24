package com.example.mobidoc.models;

public class User {

    private String Name , Surname , Email;

   /* private User(String name , String surname , String email , String phoneNumber, String user_type){

        Name = name;
        Surname = surname;
        Email = email;
        PhoneNumber = phoneNumber;

    } */

    public User(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
