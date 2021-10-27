package com.example.chefskiss2;


public class AccountController {
    private Account account;

    public AccountController(Account account) {
        this.account = account;
    }



    public Boolean isLoggedIn() {
        return this.account.getUsername() != null;
    }
}

