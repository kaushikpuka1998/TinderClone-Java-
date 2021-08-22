package com.kgstrivers.tinderc.Model;

public class Users {
    String Name,Signupdatetime;


    public Users() {
    }

    public Users(String name, String signupdatetime) {
        Name = name;
        Signupdatetime = signupdatetime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSignupdatetime() {
        return Signupdatetime;
    }

    public void setSignupdatetime(String signupdatetime) {
        Signupdatetime = signupdatetime;
    }
}
