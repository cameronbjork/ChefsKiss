package com.example.chefskiss2;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AccountInfoPageInstrumentedTest {
    public ActivityScenario scenario;
    private Account loggedInAcct= new Account("testinng", "@.com","Test1!");

    @Rule
    public ActivityScenarioRule<AccountInfoPage> activityRule = new ActivityScenarioRule(new Intent(getApplicationContext(), AccountInfoPage.class)
            .putExtra("account", loggedInAcct));

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

    }

    @Test
    public void testAccountPassedFromHomepage() {
        scenario.onActivity(activity -> {

            EditText email = activity.findViewById(R.id.userEmail2);
            EditText username = activity.findViewById(R.id.username2);
            EditText password = activity.findViewById(R.id.password2);

            assertEquals(loggedInAcct.getEmail(), email.getText().toString());
            assertEquals(loggedInAcct.getPassword(), password.getText().toString());
            assertEquals(loggedInAcct.getUsername(), username.getText().toString());

        });
    }

    @Test
    public void testChangeAccountInfo() {
        scenario.onActivity(activity -> {

            EditText email = activity.findViewById(R.id.userEmail2);
            EditText username = activity.findViewById(R.id.username2);
            EditText password = activity.findViewById(R.id.password2);

            Button btn = activity.findViewById(R.id.buttontoedit);

            email.setText("test@test.com");
            username.setText("testtest");
            password.setText("Testing1!");

            btn.performClick();

            EditText email2 = activity.findViewById(R.id.userEmail2);
            EditText username2 = activity.findViewById(R.id.username2);
            EditText password2 = activity.findViewById(R.id.password2);

            assertEquals("test@test.com", email2.getText().toString());
            assertEquals("testtest", username2.getText().toString());
            assertEquals("Testing1!", password2.getText().toString());
        });
    }

    @After
    public void closeScenario() {
        scenario.close();
    }

}
