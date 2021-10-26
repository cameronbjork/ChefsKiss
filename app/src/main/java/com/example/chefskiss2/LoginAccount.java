package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        TextView errorMessage = (TextView) findViewById(R.id.invalidInfoMessage);

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        Button loginAccount = (Button) findViewById(R.id.loginBtn);

        loginAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Account acct = new Account(usernameString, passwordString);
                databaseHelper.login(acct);
                try {

                    if (passwordString.equals(password.toString())) {
                        Intent intent = new Intent(LoginAccount.this, Homepage.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    errorMessage.setText(R.string.invalidusername);
                }
            }
        });

    }
}