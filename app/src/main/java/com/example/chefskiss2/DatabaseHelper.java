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
    public static final String COLUMN_SAVEDRECIPES = "saved_recipes";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "chefsKiss.db", null, 3);
    }

    //called the first time a database is accessed. Creates a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " (" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_USERNAME
                + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_SAVEDRECIPES + " TEXT)";

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

        if (allAccounts != null) {
            for (int i = 0; i < allAccounts.size(); i++) {
                if (allAccounts.get(i).getUsername() == null) {
                    return false;
                } else {
                    if (allAccounts.get(i).getUsername().contains(account.getUsername())) {
                        return false;
                    }
                }
                }
            }

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMAIL, account.getEmail());
        cv.put(COLUMN_USERNAME, account.getUsername());
        cv.put(COLUMN_PASSWORD, account.getPassword());
        cv.put(COLUMN_SAVEDRECIPES, "\n");

        long insert = db.insert(USER_TABLE, null, cv);
        db.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addRecipe(Account account, Recipe recipe) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        SQLiteDatabase dbWrite = this.getWritableDatabase();

        Cursor cursor = dbRead.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USERNAME + "=?",new String[]{account.getUsername()});

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SAVEDRECIPES, "\n" + recipe.getID());

        long insert = dbWrite.update(USER_TABLE, cv, COLUMN_USERNAME + " = ?",
                new String[]{account.getUsername()});

        dbRead.close();
        dbWrite.close();

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
                int tempCurs = cursor.getColumnIndex(COLUMN_USERNAME);
                String username = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_EMAIL);
                String email = cursor.getString(tempCurs);

                tempCurs = cursor.getColumnIndex(COLUMN_PASSWORD);
                String password = cursor.getString(tempCurs);

                //Add a method that returns arraylist of recipes based on ID
                tempCurs = cursor.getColumnIndex(COLUMN_SAVEDRECIPES);
                String savedRecipes = cursor.getString(tempCurs);

                //Get all the ID's
                String[] ids = savedRecipes.split("\n");
                Account temp = new Account(username, email, password, ids);

                accountList.add(temp);


                cursor.moveToNext();
            }
        }
        db.close();
        return accountList;
    }

    public boolean updateOne(Account oldAccount, Account newAccount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, newAccount.getUsername());
        cv.put(COLUMN_PASSWORD, newAccount.getPassword());
        cv.put(COLUMN_EMAIL, newAccount.getEmail());


        long result = db.update(USER_TABLE, cv, COLUMN_USERNAME + " = ?", new String[]{oldAccount.getUsername()});
        db.close();

        if (result < 1) {
            return false;
        } else {
            return true;
        }
    }


        //this is called if the user decides to delete their account
        public boolean deleteOne (Account account){

            SQLiteDatabase db = this.getWritableDatabase();

            int delete = db.delete(USER_TABLE, COLUMN_USERNAME + " = ?",
                    new String[]{account.getUsername()});
            db.close();

            if (delete < 1) {
                return false;
            } else {
                return true;
            }
        }

    public Account login(Account account) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE "+ COLUMN_USERNAME + "=?",new String[]{account.getUsername()});
        if (cursor.moveToFirst()) {
            int custId = cursor.getInt(0);
            String custUname = cursor.getString(2);
            account.setId(custId);
            String custPassword = cursor.getString(3);
            if (custUname.equals(account.getUsername()) && custPassword.equals(account.getPassword())) {
                account.setLoginStatus(true);
                return account;
            } else {
                account.setLoginStatus(false);
                return account;
            }
        }
        return null;
    }

    public Cursor rawQuery(String s, String[] strings) {
        return null ;
    }
}
