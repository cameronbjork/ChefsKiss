package com.example.chefskiss2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    public static final String RECIPE_TABLE = "recipe_table";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_DIRECTIONS = "directions";


    public RecipeDatabaseHelper(@Nullable Context context) {
        super(context, "chefsKiss.db", null, 3);
    }

    //called the first time a database is accessed. Creates a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + RECIPE_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT, " + COLUMN_PHOTO
                + " BLOB, " + COLUMN_INGREDIENTS + " TEXT, " + COLUMN_DIRECTIONS + " TEXT)";

        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps
    // from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE " + RECIPE_TABLE);
        onCreate(db);


    }

    public Recipe addOne(Recipe recipe) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, recipe.getTitle());
        cv.put(COLUMN_INGREDIENTS, recipe.getIngredients());
        cv.put(COLUMN_DIRECTIONS+"TEXT", recipe.getDirections());

        recipe.setID(db.insert(RECIPE_TABLE, null, cv));

        db.close();

        if (recipe.getID() == -1) {
            return null;
        } else {
            return recipe;
        }
    }

    public ArrayList<Recipe> getAllRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RECIPE_TABLE, null);

        ArrayList<Recipe> recipeList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int tempCurs = cursor.getColumnIndex(COLUMN_TITLE);
                String title = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_INGREDIENTS);
                String ingredients = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_DIRECTIONS+"TEXT");
                String directions = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_ID);
                int id = cursor.getInt(tempCurs);


                Recipe temp = new Recipe(id, title, ingredients, directions);
                recipeList.add(temp);

                cursor.moveToNext();
            }
        }

        return recipeList;
    }

    public Recipe updateOne(Recipe oldRecipe, Recipe newRecipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, newRecipe.getTitle());
        cv.put(COLUMN_INGREDIENTS, newRecipe.getIngredients());
        cv.put(COLUMN_DIRECTIONS+"TEXT", newRecipe.getDirections());

        Integer id = oldRecipe.getID();


        newRecipe.setID(db.update(RECIPE_TABLE, cv,  COLUMN_ID + "=?", new String[]{id.toString()}));

        if (newRecipe.getID() == -1) {
            return null;
        } else {
            return newRecipe;
        }
    }


    //this is called if the user decides to delete their account
    public boolean deleteOne (Recipe recipe){

        SQLiteDatabase db = this.getWritableDatabase();

        Integer id  = recipe.getID();

        long delete = db.delete(RECIPE_TABLE, COLUMN_ID + " = ?",
                new String[]{id.toString()});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**public Recipe findRecipe(Recipe recipe) {
        ArrayList<Recipe> allRecipes = this.getAllRecipes();

        for (int i = 0; i < allRecipes.size(); i++) {
            if (allRecipes.get(i).getTitle().contains(recipe.getTitle())) {
                return allRecipes.get(i);
            }
        }
        return null;
    } **/

    public Cursor rawQuery(String s, String[] strings) {
        return null ;
    }

}
