package com.kgstrivers.tinderc.Model;

public class Cards {

    String imageurl,name,userid;

    public Cards(String imageurl, String name, String userid) {
        this.imageurl = imageurl;
        this.name = name;
        this.userid = userid;
    }

    public String getImageurl() {
        return imageurl;
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
