package com.example.chefskiss2;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;

import android.app.Instrumentation;
import android.widget.Button;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    public ActivityScenario scenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule(MainActivity.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
    }

    @Test
    public void testLoginBtn() {
        scenario.onActivity(activity -> {
            Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(LoginAccount.class.getName(), null, true);
            Button btn = activity.findViewById(R.id.loginAccountBtn);
            btn.performClick();

            assertEquals(1, am.getHits());

        });
    }

    @Test
    public void testCreateAccountBtn() {
        scenario.onActivity(activity -> {
            Instrumentation.ActivityMonitor am = getInstrumentation().addMonitor(CreateAccount.class.getName(), null, true);
            Button btn = activity.findViewById(R.id.createAccountBtn);
            btn.performClick();

            assertEquals(1, am.getHits());

        });
    }

    @After
    public void closeScenario() {
        scenario.close();
    }

}
