package com.example.chefskiss2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountUnitTest {
    private Account acct;
    private Account acct2;

    @Before
    public void init() {
        acct = new Account("cam", "cambjork@gmail.com","Cambjork1!");

        acct2 = new Account("cam", "CamBjork01!");
    }

    //White Box Testing
    @Test
    public void accountCreatedTest () {
        assertTrue(!acct.isNull());
        assertTrue(!acct2.isNull());
    }

    @Test
    public void getEmailTest () {
        assertEquals(acct.getEmail(), "cambjork@gmail.com");
    }

    @Test
    public void getUsernameTest() {
        assertEquals(acct.getUsername(),"cam");
        assertEquals(acct2.getUsername(), "cam");
    }

    @Test
    public void getPasswordTest() {
        assertEquals(acct.getPassword(), "Cambjork1!");
        assertEquals(acct2.getPassword(), "CamBjork01!");
    }

    @Test
    public void getLoginStatusTest() {
        acct.setLoginStatus(false);
        assertTrue(!acct.getLoginStatus());
        acct2.setLoginStatus(true);
        assertTrue(acct2.getLoginStatus());
    }

    @Test
    public void setLoginStatusTest() {
        acct.setLoginStatus(true);
        assertTrue(acct.getLoginStatus());
    }


}
