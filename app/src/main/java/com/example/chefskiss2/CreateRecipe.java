package com.example.chefskiss2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateRecipe extends AppCompatActivity{
    public RecipeController RC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        //initialized
        EditText title = (EditText) findViewById(R.id.editTextTitle);
        EditText description = (EditText) findViewById(R.id.editTextDescription);
        EditText ingredients = (EditText) findViewById(R.id.editTextIngredients);


        Button createRecipe = (Button) findViewById(R.id.createRecipe);


        createRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = title.getText().toString();
                String description = description.getText().toString();
                String ingredients = ingredients.getText().toString();

                //*** Checking if the description is less than
                if (description.length() < 280) {

                }
            }
        });
    }


}
