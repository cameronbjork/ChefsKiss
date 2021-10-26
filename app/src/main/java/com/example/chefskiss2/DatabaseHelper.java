package com.example.chefskiss2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "user_table";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ID = "_ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "chefsKiss.db", null, 1);
    }

    //called the first time a database is accessed. Creates a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_USERNAME
                + "TEXT, " + COLUMN_PASSWORD + " TEXT)";

        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. It prevents previous users apps
    // from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE " + USER_TABLE);
        onCreate(db);


    }

    public boolean addOne(String email, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL +"TEXT", email);
        cv.put(COLUMN_USERNAME+"TEXT", username);
        cv.put(COLUMN_PASSWORD+"TEXT", password);

        long insert = db.insert(USER_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }


    }

    //this is called if the user decides to delete their account

    public boolean deleteOne(Account account) {

        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(USER_TABLE, COLUMN_USERNAME + " = " + account.getUsername(), null);
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor rawQuery(String s, String[] strings) {
        return null ;
    }
}
