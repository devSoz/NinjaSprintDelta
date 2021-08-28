package com.example.myapplication;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

import java.io.Serializable;

public class AppUser implements Serializable {
    private String uid;
    private String name;

    private String email;

    // @Exclude
    // private boolean isAuthenticated;
    private Boolean isNew;

    public String getuid() {
        return uid;
    }
    public String getuserName() {
        return name;
    }
    public String getEmail() { return email; }
    public Boolean getisNew() {
        return isNew;
    }


    public void setuserName(String Name) {
        this.name = Name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setuid(String uid) { this.uid = uid; }
    public void setisNew(Boolean isNew) {
        this.isNew = isNew;
    }



    public AppUser() {}

    public AppUser(String uid, String name, String email, Boolean isNew)
    {
        this.uid = uid;
        this.name = name;
        this.email = email;

        this.isNew = isNew;
    }


}