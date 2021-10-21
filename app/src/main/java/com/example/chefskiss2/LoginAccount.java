package com.example.chefskiss2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginAccount extends AppCompatActivity {
    public AccountController AC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        TextView errorMessage = (TextView) findViewById(R.id.invalidInfoMessage);

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        Button loginAccount = (Button) findViewById(R.id.loginBtn);


        /**loginAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SQLiteDatabase db = new this.getReadableDatabase();

                try {
                    Cursor password = db.rawQuery("SELECT  " + DatabaseHelper.COLUMN_PASSWORD
                            + " WHERE" + usernameString + " = " + DatabaseHelper.COLUMN_USERNAME,
                            new String[] {"1"});
                } catch (Exception e) {
                    errorMessage.setText("*Invalid Username");
                }
            }
        }); **/

    }
}

