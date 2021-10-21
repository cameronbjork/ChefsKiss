package com.example.chefskiss2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "chefsKiss.db", null, 1);
    }

    //called the first time a database is accessed. Creates a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID +
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

    public boolean addOne(Account account) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String email = account.getEmail();
        String username = account.getUsername();
        String password = account.getPassword();
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        

        long insert = db.insert(USER_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }


    }
    //this is called if the user decides to delete their account
    public void deleteOne(Account account) {

        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(USER_TABLE, COLUMN_USERNAME +" = " + account.getUsername(), null);
        db.close();

    }


}
