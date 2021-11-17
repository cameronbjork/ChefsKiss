package com.example.chefskiss2;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.widget.Button;
import android.widget.EditText;
import 	android.app.Instrumentation.ActivityMonitor;

import androidx.lifecycle.Lifecycle;
import androidx.room.Database;
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
public class LoginAccountInstrumentedTest {
    public ActivityScenario scenario;

    @Rule
    public ActivityScenarioRule<LoginAccount> activityRule = new ActivityScenarioRule(LoginAccount.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

    }

    @Test
    public void onCreateTest() {
        //Tests that the program is started in the before
        assertEquals(Lifecycle.State.CREATED, scenario.getState());
    }

    @Test
    public void testCorrectLogin() {
        scenario.onActivity(activity -> {
            EditText username = activity.findViewById(R.id.username);
            EditText password = activity.findViewById(R.id.password);

            username.setText("cbjork001");
            password.setText("Cbjork001!");

            ActivityMonitor am = getInstrumentation().addMonitor(Homepage.class.getName(), null, true);

            Button btn =  activity.findViewById(R.id.loginBtn);
            btn.performClick();
            btn.setPressed(true);
            btn.invalidate();
            btn.setPressed(false);
            btn.invalidate();

            assertEquals(1, am.getHits());


        });
    }

    @Test
    public void testIncorrectUsername() {
        scenario.onActivity(activity -> {
            EditText username = activity.findViewById(R.id.username);
            EditText password = activity.findViewById(R.id.password);

            //Incorrect Username
            username.setText("cbjork00");
            password.setText("Cbjork001!");

            ActivityMonitor am = getInstrumentation().addMonitor(Homepage.class.getName(), null, true);

            Button btn = activity.findViewById(R.id.loginBtn);
            btn.performClick();
            btn.setPressed(true);
            btn.invalidate();
            btn.setPressed(false);
            btn.invalidate();


            assertEquals(0, am.getHits());

        });
    }

    @Test
    public void testIncorrectPassword() {
        scenario.onActivity(activity -> {
            EditText username = activity.findViewById(R.id.username);
            EditText password = activity.findViewById(R.id.password);

            //Incorrect Password
            username.setText("cbjork001");
            password.setText("Cbjork001");

            ActivityMonitor am = getInstrumentation().addMonitor(Homepage.class.getName(), null, true);

            Button btn = activity.findViewById(R.id.loginBtn);

            btn.performClick();
            btn.setPressed(true);
            btn.invalidate();
            btn.setPressed(false);
            btn.invalidate();

            assertEquals(0, am.getHits());

        });
    }

    @After
    public void closeScenario() {
        scenario.close();
    }

}
