package com.example.chefskiss2;

import android.content.Intent;
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
        RecipeDatabaseHelper recipedatabasehelper = new RecipeDatabaseHelper(this);

        //initialized
        EditText title = (EditText) findViewById(R.id.editTextTitle);
        EditText description = (EditText) findViewById(R.id.editTextDescription);
        EditText ingredients = (EditText) findViewById(R.id.editTextIngredients);


        Button createRecipe = (Button) findViewById(R.id.createRecipe);

        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRecipe.this, Homepage.class);
                startActivity(intent);
            }
        });


        createRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleString = title.getText().toString();
                String descriptionString = description.getText().toString();
                String ingredientsString = ingredients.getText().toString();

                //*** Checking if the description is greater than 1000 characters
                if (description.length() < 1000) {
                    Recipe recipe = new Recipe(Id,titleString, ingredientsString, descriptionString);
                    RecipeDatabaseHelper.addOne(recipe);
                    RecipeDatabaseHelper.close();
                        Intent intent = new Intent(CreateRecipe.this, Homepage.class);
                        intent.putExtra("Recipe", recipe);
                        startActivity(intent);}

                }
            });
        };
    }
