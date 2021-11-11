package com.example.chefskiss2;

import androidx.lifecycle.Lifecycle;
import androidx.room.Database;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class DatabaseHelperInstrumentedTest {
    public ActivityScenario scenario;
    private Account acc;

    @Rule
    public ActivityScenarioRule<CreateAccount> activityRule = new ActivityScenarioRule(CreateAccount.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            acc = new Account("testt", "@.com", "Test1!");
            db.addOne(acc);
            db.close();

        });
    }

    @Test
    public void testGetAllUsers() {
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            ArrayList<Account> all = db.getAllUsers();
            db.close();

            boolean result = false;
            if (all.size() > 1) {
                result = true;
            }
            assertTrue(result);
        });
    }

    @Test
    public void testCorrectUpdateOne() {
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            Account updatedAcc = new Account("testy", "this@this.com", "Testy1!");
            assertTrue(db.updateOne(acc, updatedAcc));
            db.deleteOne(updatedAcc);
            db.close();
        });
    }

    @Test
    public void testIncorrectUpdateOne(){
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            Account updatedAcc = new Account("_______", "this@this.com", "Testy1!");
            assertFalse(db.updateOne(updatedAcc, acc));
            db.close();
        });
    }

    @Test
    public void testAddOne() {
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            ArrayList<Account> allAccts = db.getAllUsers();
            db.close();

            boolean result = false;
            for (int i = 0; i < allAccts.size(); i++) {
                if (allAccts.get(i).getUsername().equals(acc.getUsername()));
                result = true;
            }
            assertTrue(result);
        });
    }

    @Test
    public void testCorrectLogin() {
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            assertNotNull(db.login(acc));
            db.close();
        });
    }

    @Test
    public void testIncorrectLogin() {
        scenario.onActivity(activity -> {
            Account wrongUsername = new Account("______", "Test1!");
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            assertNull(db.login(wrongUsername));

            Account wrongPassword = new Account("testt", "nonono");
            assertFalse(db.login(wrongPassword).getLoginStatus());
            db.close();
        });
    }

    @Test
    public void testDeleteOne() {
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            assertTrue(db.deleteOne(acc));
        });
    }

    @Test
    public void testIncorrectDeleteOne() {
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            assertFalse(db.deleteOne(new Account("_____", "_____")));
        });
    }

    @After
    public void endTest() {
        scenario.close();
    }

}
