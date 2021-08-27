package com.kgstrivers.tinderc.Model;

public class Cards {

    String imageurl,name,userid,bio;

    public Cards(String imageurl, String name, String userid, String bio) {
        this.imageurl = imageurl;
        this.name = name;
        this.userid = userid;
        this.bio = bio;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
