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
                COLUMN_ID + " INTEGER, " + COLUMN_TITLE + " TEXT, " + COLUMN_PHOTO
                + " TEXT, " + COLUMN_INGREDIENTS + " TEXT, " + COLUMN_DIRECTIONS + " TEXT)";

        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps
    // from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE " + RECIPE_TABLE);
        onCreate(db);


    }

    public boolean addOne(Recipe recipe) {

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Recipe> allRecipes = this.getAllRecipes();

        for (int i = 0; i < allRecipes.size(); i++) {
            if (allRecipes.get(i).getTitle().contains(recipe.getTitle())) {
                return false;
            }
        }

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, recipe.getId());
        cv.put(COLUMN_TITLE, recipe.getTitle());
        cv.put(COLUMN_INGREDIENTS, recipe.getIngredients());
        cv.put(COLUMN_DIRECTIONS, recipe.getDirections());
        cv.put(COLUMN_PHOTO, recipe.getImageURI());

        long insert = db.insert(RECIPE_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
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

                tempCurs = cursor.getColumnIndex(COLUMN_ID);
                int id = cursor.getInt(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_INGREDIENTS);
                String ingredients = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_DIRECTIONS);
                String directions = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_PHOTO);
                String imageURI = cursor.getString(tempCurs);


                Recipe temp = new Recipe(id, title, ingredients, directions, imageURI);
                recipeList.add(temp);

                cursor.moveToNext();
            }
        }

        return recipeList;
    }

    public boolean updateOne(Recipe oldRecipe, Recipe newRecipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, oldRecipe.getId());
        cv.put(COLUMN_TITLE, newRecipe.getTitle());
        cv.put(COLUMN_INGREDIENTS, newRecipe.getIngredients());
        cv.put(COLUMN_DIRECTIONS, newRecipe.getDirections());
        cv.put(COLUMN_PHOTO, newRecipe.getImageURI());


        long result = db.update(RECIPE_TABLE, cv, COLUMN_TITLE + "=?", new String[]{oldRecipe.getTitle()});

        if (result < 1) {
            return false;
        } else {
            return true;
        }
    }


    //this is called if the user decides to delete their account
    public boolean deleteOne(Recipe recipe) {

        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(RECIPE_TABLE, COLUMN_TITLE + " = ?",
                new String[]{recipe.getTitle()});
        db.close();

        if (delete < 1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Recipe> getSavedRecipes(Account acct) {

        int id = acct.getId();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + RECIPE_TABLE + " WHERE " + COLUMN_ID + " = " + id, null);
        ArrayList<Recipe> recipeList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int tempCurs = cursor.getColumnIndex(COLUMN_TITLE);
                String title = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_INGREDIENTS);
                String ingredients = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_DIRECTIONS);
                String directions = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_PHOTO);
                String imageByteArray = cursor.getString(tempCurs);

                Recipe temp = new Recipe(acct.getId(), title, ingredients, directions, imageByteArray);
                recipeList.add(temp);

                cursor.moveToNext();
            }

        }
        return recipeList;
    }

    public Recipe findRecipeByIdAndTitle(int id, String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer id2 = id;

        Cursor cursor = db.rawQuery("select * from " + RECIPE_TABLE + " where " + COLUMN_ID + "=? and " +
                COLUMN_TITLE + "=?", new String[]{id2.toString(), title});

        ArrayList<Recipe> recipeList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int tempCurs = cursor.getColumnIndex(COLUMN_TITLE);
                String title2 = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_INGREDIENTS);
                String ingredients = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_DIRECTIONS);
                String directions = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_PHOTO);
                String imageByteArray = cursor.getString(tempCurs);

                Recipe temp = new Recipe(id, title2, ingredients, directions, imageByteArray);
                recipeList.add(temp);

                cursor.moveToNext();
            }
        }
        return recipeList.get(0);
    }

    /**
    public ArrayList<Recipe> searchSavedRecipes(String searchString) {

        int id = Account.getId();
        String idString = Integer.toString(id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RECIPE_TABLE + " WHERE COLUMN_ID =? AND " +
                "COLUMN_TITLE LIKE '%" + searchString + "%'", new String[]{idString});
        ArrayList<Recipe> recipeList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int tempCurs = cursor.getColumnIndex(COLUMN_TITLE);
                String title = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_INGREDIENTS);
                String ingredients = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_DIRECTIONS);
                String directions = cursor.getString(tempCurs);


                Recipe temp = new Recipe(Account.getId(), title, ingredients, directions);
                recipeList.add(temp);

                cursor.moveToNext();
            }

        }
        return recipeList;
    }
     **/

        public Cursor rawQuery(String s, String[]strings){
            return null;
        }
    }
