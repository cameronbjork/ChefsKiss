package com.example.chefskiss2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public boolean addOne(Account account) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL +"TEXT", account.getEmail());
        cv.put(COLUMN_USERNAME+"TEXT", account.getUsername());
        cv.put(COLUMN_PASSWORD+"TEXT", account.getPassword());

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

    public void login(Account account) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqlSelect = {"id", "email", "username", "password"};
        String tableName = USER_TABLE;
        String login = "SELECT " + COLUMN_USERNAME + "TEXT, " + COLUMN_PASSWORD + "TEXT FROM " + USER_TABLE
                + " WHERE " + COLUMN_USERNAME + "TEXT = " + account.getUsername() + "&&" + COLUMN_PASSWORD +
                "TEXT = " + account.getPassword();
        Cursor cursor = db.rawQuery(login, null, null);
        List<Account> result = new ArrayList();
       /** if (cursor.moveToFirst()) {
            id = cursor.getId();

        } **/



        db.execSQL(login);

       /** Cursor password = databaseHelper.rawQuery("SELECT  " + DatabaseHelper.COLUMN_PASSWORD + " WHERE" + usernameString +
                " = " + DatabaseHelper.COLUMN_USERNAME, new String[] {"1"});
        password = db.rawQuery("SELECT " + COLUMN_PASSWORD + " FROM " + USER_TABLE + DatabaseHelper.COLUMN_PASSWORD
                        + " WHERE" + usernameString + " = " + DatabaseHelper.COLUMN_USERNAME,
                new String[] {"1"}); **/

    }

    public Cursor rawQuery(String s, String[] strings) {
        return null ;
    }
}
