package com.kgstrivers.tinderc.Model;

public class Logind {

    private String email,LastLogin;

    public Logind(String email, String lastLogin) {
        this.email = email;
        LastLogin = lastLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
    }
}
