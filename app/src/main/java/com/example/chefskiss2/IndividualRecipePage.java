package com.example.chefskiss2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class IndividualRecipePage extends AppCompatActivity {

    private static final String TAG = "IndividualRecipePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_recipe_page);
        Log.d(TAG, "onCreate: Starting");

        Button btnGoHome = (Button) findViewById(R.id.btnGoHome);

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked btnGoHome");

                Intent intent = new Intent(IndiviudalRecipePage.this, Homepage.class);
                startActivity(intent);
                )

            }
        });

    }
}