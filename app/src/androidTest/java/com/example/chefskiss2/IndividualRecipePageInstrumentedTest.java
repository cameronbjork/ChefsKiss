package com.example.chefskiss2;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.widget.EditText;

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
public class IndividualRecipePageInstrumentedTest {
    public ActivityScenario scenario;
    private Account loggedInAcct = new Account(100,"cam", "@.com", "pass");
    private Recipe recipe = new Recipe(100, "Title", "Ingredients", "Directions");

   /** @Rule
    public ActivityScenarioRule<IndividualRecipePage> activityRule = new ActivityScenarioRule(new Intent(getApplicationContext(), IndividualRecipePage.class)
            .putExtra("account", loggedInAcct).putExtra("recipe", recipe));

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

    }

    @Test
    public void testCorrectInfoInputted() {
        scenario.onActivity(activity -> {
            EditText title = (EditText) activity.findViewById(R.id.viewRecipeName);
            EditText directions = (EditText) activity.findViewById(R.id.viewRecipeDescription);
            EditText ingredients = (EditText) activity.findViewById(R.id.viewRecipeIngredients);

            assertEquals(recipe.getTitle(), title.getText().toString());
            assertEquals(recipe.getDirections(), directions.getText().toString());
            assertEquals(recipe.getIngredients(), ingredients.getText().toString());
        });
    }

    @After
    public void closeScenario() {
        scenario.close();
    }
**/
}
