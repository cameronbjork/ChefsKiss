package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        Button loginAccount = (Button) findViewById(R.id.loginBtn);
        Button forgotPassword = (Button) findViewById(R.id.forgotPass);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginAccount.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        loginAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();

                Account acct = new Account(usernameString, passwordString);

                Account result = databaseHelper.login(acct);

                if (result != null && result.getLoginStatus() == true) {
                    Intent intent = new Intent(LoginAccount.this, Homepage.class);
                    intent.putExtra("account", result);
                    startActivity(intent);
                } else if (result != null && result.getLoginStatus() == false) {
                    Toast.makeText(LoginAccount.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginAccount.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}