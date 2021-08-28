package com.kgstrivers.tinderc.Model;

public class Users {
    String Name,Signupdatetime,Imageurl,bio;


    public Users(String name, String signupdatetime, String imageurl, String bio) {
        Name = name;
        Signupdatetime = signupdatetime;
        Imageurl = imageurl;
        this.bio = bio;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSignupdatetime() {
        return Signupdatetime;
    }

    public void setSignupdatetime(String signupdatetime) {
        Signupdatetime = signupdatetime;
    }
}
