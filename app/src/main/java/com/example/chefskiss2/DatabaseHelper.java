package com.example.chefskiss2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

        ArrayList<Account> allAccounts = this.getAllUsers();

        for (int i=0; i < allAccounts.size(); i++) {
            if (allAccounts.get(i).getUsername().contains(account.getUsername())) {
                return false;
            }
        }

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL +"TEXT", account.getEmail());
        cv.put(COLUMN_USERNAME+"TEXT", account.getUsername());
        cv.put(COLUMN_PASSWORD+"TEXT", account.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Account> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + USER_TABLE, null);

        ArrayList<Account> accountList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int tempCurs = cursor.getColumnIndex("USERNAMETEXT");
                String username = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex("EMAILTEXT");
                String email = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex("PASSWORDTEXT");
                String password = cursor.getString(tempCurs);


                Account temp = new Account(username, email, password);
                accountList.add(temp);

                cursor.moveToNext();
            }
        }
        
        return accountList;
    }

    public boolean updateOne(Account oldAccount, Account newAccount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("USERNAMETEXT", newAccount.getUsername());
        cv.put("PASSWORDTEXT", newAccount.getPassword());
        cv.put("EMAILTEXT", newAccount.getEmail());



        long result = db.update(USER_TABLE, cv, "USERNAMETEXT=?", new String[]{oldAccount.getUsername()});

        if (result == -1) {
            return false;
        } else {
            return true;
        }


    }

    //this is called if the user decides to delete their account
    public boolean deleteOne(Account account) {

        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(USER_TABLE, COLUMN_USERNAME + "TEXT = ?", new String[]{account.getUsername()});
        db.close();

        if (delete == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Account login(Account account) {
        ArrayList<Account> allUsers = this.getAllUsers();

        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUsername().equals(account.getUsername())) {
                if (allUsers.get(i).getPassword().equals(account.getPassword())) {
                    allUsers.get(i).setLoginStatus(true);
                    return allUsers.get(i);
                } else {
                    allUsers.get(i).setLoginStatus(false);
                    return allUsers.get(i);
                }
            }
        }
        return null;
    }

    public Cursor rawQuery(String s, String[] strings) {
        return null ;
    }
}
