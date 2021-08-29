package com.kgstrivers.tinderc.Model;

public class Users {
    String Name,Signupdatetime,Imageurl,bio,intr1,intr2,intr3,intr4,intr5;


    public Users(String name, String signupdatetime, String imageurl, String bio, String intr1, String intr2, String intr3, String intr4, String int5) {
        Name = name;
        Signupdatetime = signupdatetime;

        Imageurl = imageurl;
        this.bio = bio;
        this.intr1 = intr1;
        this.intr2 = intr2;
        this.intr3 = intr3;
        this.intr4 = intr4;
        this.intr5 = int5;

    }



    public String getIntr1() {
        return intr1;
    }

    public void setIntr1(String intr1) {
        this.intr1 = intr1;
    }

    public String getIntr2() {
        return intr2;
    }

    public void setIntr2(String intr2) {
        this.intr2 = intr2;
    }

    public String getIntr3() {
        return intr3;
    }

    public void setIntr3(String intr3) {
        this.intr3 = intr3;
    }

    public String getIntr4() {
        return intr4;
    }

    public void setIntr4(String intr4) {
        this.intr4 = intr4;
    }

    public String getIntr5() {
        return intr5;
    }

    public void setIntr5(String intr5) {
        this.intr5 = intr5;
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
