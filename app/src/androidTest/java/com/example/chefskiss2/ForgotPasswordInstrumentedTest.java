package com.example.chefskiss2;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Instrumentation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ForgotPasswordInstrumentedTest {
    public ActivityScenario scenario;
    private Account acct = new Account("user", "user@user.com", "pass");

    @Rule
    public ActivityScenarioRule<ForgotPassword> activityRule = new ActivityScenarioRule(ForgotPassword.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
           DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
           db.addOne(acct);
           db.close();
        });

    }

    @Test
    public void onCreateTest() {
        //Tests that the program is started in the before
        assertEquals(Lifecycle.State.CREATED, scenario.getState());
    }

    @Test
    public void correctEmailTest() {
        scenario.onActivity(activity -> {
            EditText email =  activity.findViewById(R.id.confirmEmail);
            EditText newPass = activity.findViewById(R.id.newPass);
            EditText confirmNewPass = activity.findViewById(R.id.confirmNewPass);

            email.setText("user@user.com");
            newPass.setText("pass2");
            confirmNewPass.setText("pass2");

            Button forgotPassBtn = activity.findViewById(R.id.confirmBtn);
            Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(LoginAccount.class.getName(), null, true);
            forgotPassBtn.callOnClick();

            assertEquals(1, am.getHits());

        });
    }

    @Test
    public void incorrectEmailTest() {
        scenario.onActivity(activity -> {
            EditText email =  activity.findViewById(R.id.confirmEmail);
            EditText newPass = activity.findViewById(R.id.newPass);
            EditText confirmNewPass = activity.findViewById(R.id.confirmNewPass);

            email.setText("use@user.com");
            newPass.setText("pass2");
            confirmNewPass.setText("pass2");

            Button forgotPassBtn = activity.findViewById(R.id.confirmBtn);
            Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(ForgotPassword.class.getName(), null, true);
            forgotPassBtn.callOnClick();

            assertEquals(0, am.getHits());

        });
    }

    @After
    public void removeScenario() {
        scenario.onActivity(activity -> {
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            db.deleteOne(acct);
            db.close();
        });
        scenario.close();
    }

}
