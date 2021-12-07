package com.example.chefskiss2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MealScheduleDatabaseHelper extends SQLiteOpenHelper {

    public static final String MEAL_TABLE = "meal_schedule_table";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TITLE = "title";


    public MealScheduleDatabaseHelper(@Nullable Context context) {
        super(context, "chefsKiss.db", null, 3);
    }

    //called the first time a database is accessed. Creates a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + MEAL_TABLE + " (" +
                COLUMN_ID + " INTEGER, " + COLUMN_TITLE + " TEXT, " + COLUMN_TIME + " TEXT, "
                + COLUMN_DATE + " TEXT)";

        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps
    // from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE " + MEAL_TABLE);
        onCreate(db);


    }

    public boolean addMealForDate(String date, String time, Recipe meal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, meal.getId());
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TITLE, meal.getTitle());

        long insert = db.insert(MEAL_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Meal> getMealsForMonth(String month, Account loggedInAcct) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer id = loggedInAcct.getId();
        Cursor cursor = db.rawQuery("select * from " + MEAL_TABLE + " where " + COLUMN_ID + "=?", new String[]{id.toString()});

        ArrayList<Meal> meals = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int tempCurs = cursor.getColumnIndex(COLUMN_ID);
                int id1 = cursor.getInt(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_DATE);
                String dateFromQ = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_TITLE);
                String recipeTitle = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_TIME);
                String time = cursor.getString(tempCurs);

                Meal meal = new Meal(id1, dateFromQ, recipeTitle, time);
                String[] mealDate = meal.getDate().split("/");

                if(mealDate[1].equals(month)) {
                    meals.add(meal);
                }
                cursor.moveToNext();
            }
        }
        return meals;

    }

    public ArrayList<Meal> getMealsForDate(String date, Account loggedInAcct) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer id = loggedInAcct.getId();
        Cursor cursor = db.rawQuery("select * from " + MEAL_TABLE + " where " + COLUMN_ID + "=? and " +
                COLUMN_DATE + "=?", new String[]{id.toString(), date});

        ArrayList<Meal> meals = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int tempCurs = cursor.getColumnIndex(COLUMN_ID);
                int id1 = cursor.getInt(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_DATE);
                String dateFromQ = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_TITLE);
                String recipeTitle = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_TIME);
                String time = cursor.getString(tempCurs);

                Meal meal = new Meal(id1, dateFromQ, recipeTitle, time);
                meals.add(meal);
                cursor.moveToNext();
            }
        }



        return meals;
    }


    public void delete(Account loggedIn, Recipe recipe, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer id = loggedIn.getId();

        long delete = 0;

        if (time.equals("Breakfast")) {
            delete = db.delete(MEAL_TABLE, COLUMN_ID + " =? and " + COLUMN_DATE + " =? and "
                            + COLUMN_TIME + " =?",
                    new String[]{id.toString(), date, "B"});
        } else if (time.equals("Lunch")) {
            delete = db.delete(MEAL_TABLE, COLUMN_ID + " =? and " + COLUMN_DATE + " =? and "
                            + COLUMN_TIME + " =?",
                    new String[]{id.toString(), date, "L"});
        } else if (time.equals("Dinner")) {
            delete = db.delete(MEAL_TABLE, COLUMN_ID + " =? and " + COLUMN_DATE + " =? and "
                            + COLUMN_TIME + " =?",
                    new String[]{id.toString(), date, "D"});
        }

        db.close();
    }
}
