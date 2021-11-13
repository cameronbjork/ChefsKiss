package com.example.chefskiss2;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.junit.Assert.*;

import android.provider.ContactsContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeDatabaseHelperInstrumentedTest {
    public ActivityScenario scenario;
    private Recipe recipe;

    @Rule
    public ActivityScenarioRule<CreateAccount> activityRule = new ActivityScenarioRule(CreateAccount.class);

    @Before
    public void init() {
        scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper db = new RecipeDatabaseHelper(activity.getApplicationContext());
            recipe = new Recipe("Test Recipe", "All", "Cook that stuff");
            recipe = db.addOne(recipe);
            db.close();
        });
    }

    @Test
    public void testAddOne() {
        scenario.onActivity(activity -> {
            RecipeDatabaseHelper db = new RecipeDatabaseHelper(activity.getApplicationContext());


            //Test new recipe
            Recipe recipe2 = new Recipe("Test Recipe2", "All", "Cook that stuff");
            assertNotNull(db.addOne(recipe2));
            db.deleteOne(recipe2);
            db.close();
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
            Recipe recipe2 = new Recipe("Test Recipe2", "All", "Cook that stuff");
            recipe = db.updateOne(recipe, recipe2);
            db.close();

            assertTrue(recipe.getTitle().equals("Test Recipe2"));

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
            RecipeDatabaseHelper db = new RecipeDatabaseHelper(activity.getApplicationContext());
            db.deleteOne(recipe);
            db.close();
        });

        scenario.close();
    }
}
