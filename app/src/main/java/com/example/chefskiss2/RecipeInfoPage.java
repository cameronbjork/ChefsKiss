package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeInfoPage extends AppCompatActivity {

    private static final String TAG = "RecipeInfoPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info_page);
        DatabaseHelper db = new DatabaseHelper(this);
        Log.d(TAG, "onCreate: Starting.");

        EditText titleEditText = (EditText) findViewById(R.id.editTextTitle);
        EditText discriptionEditText = (EditText) findViewById(R.id.editTextDescription);
        EditText ingredientsEditText = (EditText) findViewById(R.id.editTextIngredients);

        titleEditText.setText(Recipe.getTitle());
        discriptionEditText.setText(Recipe.getDirections());
        ingredientsEditText.setText(Recipe.getIngredients());

        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    titleEditText.setSelection(titleEditText.getText().length());
                }

            }
        });

        discriptionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    discriptionEditText.setSelection(discriptionEditText.getText().length());
                }

            }
        });

        ingredientsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    ingredientsEditText.setSelection(ingredientsEditText.getText().length());
                }

            }
        });


        Button cancelRecipeEdit = (Button) findViewById(R.id.cancelButton2);

        cancelRecipeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked backtohomebtn.");

                Intent intent = new Intent(RecipeInfoPage.this, Homepage.class);
                startActivity(intent);
            }
        });
        Button editRecipeBttn = (Button) findViewById(R.id.editRecipe);

        editRecipeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked editaccountbtn.");

                String titleString = titleEditText.getText().toString();
                String discriptionString = discriptionEditText.getText().toString();
                String ingredientsString = ingredientsEditText.getText().toString();

                Recipe newRecipe = new Recipe(titleString, discriptionString, ingredientsString);

                db.updateOne(loggedInAcct, newAcc);

                Intent intent = new Intent(RecipeInfoPage.this, RecipeInfoPage.class);
                intent.putExtra("account", newAcc);
                startActivity(intent);
                RecipeInfoPage.this.finish();

            }
        });
    }

