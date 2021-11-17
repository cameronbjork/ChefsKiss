package com.example.chefskiss2;


import java.io.Serializable;

public class Account implements Serializable {
    private int ACCOUNT_ID;
    private String username;
    private String email;
    private String password;
    private boolean loginStatus;

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //For test, not used throughout program
    public Account(int id, String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ACCOUNT_ID = id;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getLoginStatus() {
        return this.loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public void setId(int newId) { this.ACCOUNT_ID = newId; }

    public boolean isNull() {
        if (this.username.isEmpty())
            return true;
        return false;
    }

    public int getId() {
        return ACCOUNT_ID;
    }

    public void setEmail(String custEmail) {
        this.email = custEmail;
    }
}
