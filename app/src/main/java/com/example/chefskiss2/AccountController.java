package com.example.chefskiss2;


import java.io.BufferedWriter;

public class AccountController {
    private Account account;

    public AccountController(Account account) {
        this.account = account;
    }



    public Boolean isLoggedIn() {
        return this.account.getUsername() != null;
    }
}

