package com.example.chefskiss2;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CreateAccountInstrumentedTest {
    public static final String TEST_USERNAME = "Cameron";
    public static final String TEST_EMAIL = "cameron@cameron.com";
    public static final String TEST_PASSWORD = "Cameron0!";
    public ActivityScenario scenario;

    @Rule
    public ActivityScenarioRule<CreateAccount> activityRule = new ActivityScenarioRule(CreateAccount.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

    }

    @Test
    public void onCreateTest() {
        assertEquals(Lifecycle.State.CREATED, scenario.getState());
    }

}
