package com.example.chefskiss2;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AccountController {
    private String username;
    private String email;
    private String password;
    private BufferedWriter writer;

    public AccountController() {

    }

    public void createAccount(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        //Add to DB here

    }



    public Boolean isLoggedIn() {
        return this.username != null;
    }
}

