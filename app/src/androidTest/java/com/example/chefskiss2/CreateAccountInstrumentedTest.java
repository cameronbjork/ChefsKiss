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

import android.content.res.Resources;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class CreateAccountInstrumentedTest {
    public ActivityScenario scenario;

    @Rule
    public ActivityScenarioRule<CreateAccount> activityRule = new ActivityScenarioRule(CreateAccount.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        //Create a correct account
        scenario.onActivity(activity -> {

            //First checks to see if user can correctly import information
            EditText email = activity.findViewById(R.id.userEmail);
            email.setText("@.com");

            EditText username = activity.findViewById(R.id.username);
            username.setText("testing");

            EditText password = activity.findViewById(R.id.password);
            password.setText("Testing1!");

            Button createAccountBtn = activity.findViewById(R.id.createAccountBtn2);

            //Clicks create account button and adds to DB
            createAccountBtn.performClick();
        });

    }

    @Test
    public void onCreateTest() {
        //Tests that the program is started in the before
        assertEquals(Lifecycle.State.CREATED, scenario.getState());
    }

    @Test
    public void incorrectEmailTest() {
        scenario.onActivity(activity -> {
            EditText email = activity.findViewById(R.id.userEmail);

            //CreateAccountBtn
            Button createAccountBtn = activity.findViewById(R.id.createAccountBtn2);

            //ErrorMessage text to view that correct error is displayed
            TextView errorMessage = activity.findViewById(R.id.invalidInfoMessage);

            //Check for null value
            email.setText("");
            createAccountBtn.performClick();
            assertEquals("*Invalid Email Address", errorMessage.getText().toString());

            //Check for email with only an @
            email.setText("@");
            createAccountBtn.performClick();
            assertEquals("*Invalid Email Address", errorMessage.getText().toString());

            //Check for email with only .com
            email.setText(".com");
            createAccountBtn.performClick();
            assertEquals("*Invalid Email Address", errorMessage.getText().toString());

        });
    }

    @Test
    public void incorrectPasswordTest() {
        scenario.onActivity(activity -> {
            EditText password = activity.findViewById(R.id.password);
            EditText email = activity.findViewById(R.id.userEmail);

            email.setText("@.com");

            //CreateAccountBtn
            Button createAccountBtn = activity.findViewById(R.id.createAccountBtn2);

            //ErrorMessage text to view that correct error is displayed
            TextView errorMessage = activity.findViewById(R.id.invalidInfoMessage);

            //Check for null value
            password.setText("");
            createAccountBtn.performClick();
            assertEquals("Password must contain:\n - Uppercase Letter\n - Lowercase Letter\n - Number\n - Special Character($!?,.)", errorMessage.getText().toString());

            //Check for password with only a capital Letter
            password.setText("C");
            createAccountBtn.performClick();
            assertEquals("Password must contain:\n - Uppercase Letter\n - Lowercase Letter\n - Number\n - Special Character($!?,.)", errorMessage.getText().toString());

            //Check for password with uppercase and lowercase letter
            password.setText("Cam");
            createAccountBtn.performClick();
            assertEquals("Password must contain:\n - Uppercase Letter\n - Lowercase Letter\n - Number\n - Special Character($!?,.)", errorMessage.getText().toString());

            //Check for password with uppercase letter, lowercase letter, and number
            password.setText("Cam1");
            createAccountBtn.performClick();
            assertEquals("Password must contain:\n - Uppercase Letter\n - Lowercase Letter\n - Number\n - Special Character($!?,.)", errorMessage.getText().toString());

            //Check for password with uppercase letter, lowercase letter, number, and special character
            password.setText("Cam1!");
            createAccountBtn.performClick();
            assertEquals("Username already taken", errorMessage.getText().toString());
        });
    }

    @Test
    public void invalidUsernameTest() {
        scenario.onActivity(activity -> {
            EditText email = activity.findViewById(R.id.userEmail);
            EditText username = activity.findViewById(R.id.username);
            EditText password = activity.findViewById(R.id.password);


            //CreateAccountBtn
            Button createAccountBtn = activity.findViewById(R.id.createAccountBtn2);

            //ErrorMessage text to view that correct error is displayed
            TextView errorMessage = activity.findViewById(R.id.invalidInfoMessage);

            email.setText("@.com");
            password.setText("Test1!");
            username.setText("testing");

            createAccountBtn.setPressed(true);

            assertEquals("Username already taken", errorMessage.getText().toString());

        });
    }

    @After
    public void closeScenario() {
        //Delete account from DB and close Scenario
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            db.deleteOne(new Account("testing", "Testing1!"));
            db.close();
        });

        scenario.close();
    }
}
