package com.example.chefskiss2;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomepageInstrumentedTest {
    public ActivityScenario scenario;
    private Account acct = new Account("user", "user@user.com", "pass");

    @Rule
    public ActivityScenarioRule<Homepage> activityRule = new ActivityScenarioRule(Homepage.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
    }

    @Test
    public void navigationViewTest() {
        //scenario.
    }

}
