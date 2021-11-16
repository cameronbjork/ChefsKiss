package com.example.chefskiss2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecipeUnitTest {
    Recipe r1;

    @Before
    public void init() {
        byte[] imagearray = {1, 2};
        r1 = new Recipe(1, "title", "ingredients", "directions", imagearray);
    }

    @Test
    public void recipeCreatedTest() {
        assertNotNull(r1);
    }

    @Test
    public void getTitleTest() {
        assertEquals("title", r1.getTitle());
    }

    @Test
    public void getIngredientsTest() {
        assertEquals("ingredients", r1.getIngredients());
    }

    @Test
    public void getDirerctionsTest() {
        assertEquals("directions", r1.getDirections());
    }

    @Test
    public void getImageArrayTest() {
        assertEquals(1, r1.getImageByteArray()[0]);
    }
}
