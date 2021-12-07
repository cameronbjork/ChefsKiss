package com.example.chefskiss2;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class CreateRecipeInstrumentedTest {
    private Account loggedInAcct = new Account(100,"cam", "@.com", "pass");
    private Intent intent = new Intent(getApplicationContext(), CreateRecipe.class)
            .putExtra("account", loggedInAcct);
    @Rule
    public ActivityScenarioRule<CreateRecipe> activityRule = new ActivityScenarioRule<>(intent);

    @Before
    public void init() {
        //scenario = activityRule.getScenario();
        //scenario.moveToState(Lifecycle.State.CREATED);
    }

    @Test
    public void onCreateTest() {
        ActivityScenario scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        //Tests that the program is started in the before
        assertEquals(Lifecycle.State.CREATED, scenario.getState());
        scenario.close();
    }

    @Test
    public void correctInfoTest() {
        ActivityScenario scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.launch(CreateRecipe.class);
        scenario.onActivity(activity -> {
            EditText name = activity.findViewById(R.id.editTextTitle);
            EditText description = activity.findViewById(R.id.editTextDescription);
            EditText ingredients = activity.findViewById(R.id.editTextIngredients);

            name.setText("Name");
            description.setText("Directions");
            ingredients.setText("Ingredients");

            Button createRecipeBtn = activity.findViewById(R.id.createRecipe);
            createRecipeBtn.callOnClick();

            RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(activity.getApplicationContext());
            ArrayList<Recipe> recipes = rdb.getSavedRecipes(loggedInAcct);

            assertEquals("Name", recipes.get(0).getTitle());
            assertEquals("Directions", recipes.get(0).getDirections());
            assertEquals("Ingredients", recipes.get(0).getIngredients());
            assertEquals(100, recipes.get(0).getId());
            assertNotNull(recipes.get(0).getImageURI());
            rdb.close();

        });
        scenario.close();
    }

    @After
    public void removeScenario() {
        ActivityScenario scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(activity.getApplicationContext());
            Recipe r = new Recipe(100, "Name", "desc", "ingredients");
            rdb.deleteOne(r);
        });
        scenario.close();
    }

}
