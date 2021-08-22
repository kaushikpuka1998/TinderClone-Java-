package com.kgstrivers.tinderc.Model;

public class Logoutd {
    private String email,LastLogout;

    public Logoutd(String email, String lastLogout) {
        this.email = email;
        LastLogout = lastLogout;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastLogout() {
        return LastLogout;
    }

    public void setLastLogout(String lastLogout) {
        LastLogout = lastLogout;
    }
}
