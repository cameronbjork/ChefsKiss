package com.example.chefskiss2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Button backToLogin = (Button) findViewById(R.id.backToLogin);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        //AccountController acctController = new AccountController(Account);

        Button confirm = (Button) findViewById(R.id.confirmBtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.confirmEmail);
                EditText newPass = (EditText) findViewById(R.id.newPass);
                EditText confirmNewPass = (EditText) findViewById(R.id.confirmNewPass);

                String emailString = email.getText().toString();
                String newPasswordString = newPass.getText().toString();
                String confirmString = confirmNewPass.getText().toString();

                ArrayList<Account> allUsers = databaseHelper.getAllUsers();

                for (int i = 0; i < allUsers.size(); i++) {
                    if (allUsers.get(i).getEmail().equals(emailString)) {
                        Account user = allUsers.get(i);
                        if(newPasswordString.equals(confirmString)){
                            Account newAcc = new Account(user.getUsername(), emailString, newPasswordString);
                            databaseHelper.updateOne(user, newAcc);
                        } else {
                            //print error "passwords do not match"
                        }


                    } else {
                        //Need to print error message "no account with that email"
                    }

                }
                //returns user to login
                Intent intent2 = new Intent(ForgotPassword.this, LoginAccount.class);
                startActivity(intent2);

                //Intent intent = new Intent(ForgotPassword.this, LoginAccount.class);
                //startActivity(intent);
            }


        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, LoginAccount.class);
                startActivity(intent);
            }
        });
    }
}