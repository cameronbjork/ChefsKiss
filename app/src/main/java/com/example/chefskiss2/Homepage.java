package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    public Homepage() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");

        ImageButton recipesButton = (ImageButton) findViewById(R.id.imageButton3);
        recipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, CreateRecipe.class);
                intent.putExtra("account", loggedInAcct);
                startActivity(intent);
            }
        });

        Button accInfoBtn = (Button) findViewById(R.id.buttontoaccinfo);
        accInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Homepage.this, AccountInfoPage.class);
                intent.putExtra("account", loggedInAcct);
                startActivity(intent);
            }
        });

        Button signOut = (Button) findViewById(R.id.signOutBtn);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}