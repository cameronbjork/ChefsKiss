package com.example.chefskiss2;


import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    private int ACCOUNT_ID;
    private String username;
    private String email;
    private String password;
    private boolean loginStatus;
    private String[] recipeList;


    //Create Account Constructor
    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //Constructor used after user adds first recipe
    public Account(String username, String email, String password, String[] recipelist) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.recipeList = recipelist;
    }

    //Login Account constructor
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

}
