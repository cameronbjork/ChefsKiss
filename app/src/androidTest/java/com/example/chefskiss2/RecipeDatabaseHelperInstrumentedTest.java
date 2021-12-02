package com.example.chefskiss2;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import static org.junit.Assert.*;


import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;


import java.lang.reflect.Array;
import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class RecipeDatabaseHelperInstrumentedTest {
    public ActivityScenario scenario;
    private Recipe recipe;
    private Account loggedInAcct = new Account(100,"cam", "@.com", "pass");

    @Rule
    public ActivityScenarioRule<CreateRecipe> activityRule = new ActivityScenarioRule(new Intent(getApplicationContext(), CreateRecipe.class)
            .putExtra("account", loggedInAcct));

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(activity.getApplicationContext());
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            db.addOne(loggedInAcct);
            loggedInAcct = db.login(loggedInAcct);

            recipe = new Recipe(loggedInAcct.getId(), "All", "Cook that stuff", "Directions");
            rdb.addOne(recipe);
            rdb.close();
        });
    }

    @Test
    public void onCreateTest() {
        //Tests that the program is started in the before
        assertEquals(Lifecycle.State.CREATED, scenario.getState());
    }

    @Test
    public void testAddOne() {
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(activity.getApplicationContext());

            //Test new recipe
            recipe = new Recipe(loggedInAcct.getId(), "All", "Cook that stuff", "Directions");
            assertTrue(rdb.getAllRecipes().get(0).getTitle().contains("All"));
            assertTrue(rdb.getAllRecipes().get(0).getId() == loggedInAcct.getId());
            rdb.close();
        });
    }

    @Test
    public void testGetAllRecipes() {
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper db = new RecipeDatabaseHelper(activity.getApplicationContext());
            assertTrue(db.getAllRecipes().size() > 0);
            db.close();
        });
    }


    @Test
    public void testUpdateOne() {
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper db = new RecipeDatabaseHelper(activity.getApplicationContext());
            Recipe recipe2 = new Recipe(-1, "aye", "All", "Cook that stuff");
            db.close();

            assertTrue(db.updateOne(recipe, recipe2));

        });

    }

    @Test
    public void getSavedRecipes() {
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper db = new RecipeDatabaseHelper(activity.getApplicationContext());
            Recipe r2 = new Recipe(loggedInAcct.getId(), "this", "testing", "tests my patience");
            Recipe r3 = new Recipe (2, "should not be in list", ".", ".");
            db.addOne(r2);
            db.addOne(r3);

            ArrayList<Recipe> usersRecipes = db.getSavedRecipes(loggedInAcct);

            for (int i = 0; i < usersRecipes.size(); i++) {
                if (usersRecipes.get(i).getTitle().equals("this")) {
                    assertTrue(true);
                } else if (usersRecipes.get(i).getTitle().equals("should not be in list")) {
                    assertTrue(false);
                } else if (usersRecipes.get(i).getTitle().equals("All")) {
                    assertTrue(true);
                }
            }

            db.deleteOne(r2);
            db.deleteOne(r3);
            db.close();

        });
    }

    @Test
    public void testDeleteOne() {
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper db = new RecipeDatabaseHelper(activity.getApplicationContext());
            assertTrue(db.deleteOne(recipe));
            db.close();
        });
    }

    @After
    public void removeScenario() {
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(activity.getApplicationContext());
            DatabaseHelper db = new DatabaseHelper(activity.getApplicationContext());
            db.deleteOne(loggedInAcct);
            rdb.deleteOne(recipe);
            db.close();
            rdb.close();
        });

        scenario.close();
    }
}
