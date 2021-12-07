package com.example.chefskiss2;

import static android.net.Uri.parse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class ViewIndividualMeal extends AppCompatActivity{
    public RecipeController RC;
    private String imageURI = "";
    final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_meal);
        RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(this);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");
        Recipe recipeIn = (Recipe) getIntent().getSerializableExtra("recipe");
        String timeOfDay = (String) getIntent().getSerializableExtra("time");
        String date = (String) getIntent().getSerializableExtra("date");

        //initialized
        EditText title = (EditText) findViewById(R.id.viewRecipeName);
        title.setText(recipeIn.getTitle());
        EditText directions = (EditText) findViewById(R.id.viewRecipeDescription);
        directions.setText(recipeIn.getDirections());
        EditText ingredients = (EditText) findViewById(R.id.viewRecipeIngredients);
        ingredients.setText(recipeIn.getIngredients());


        ImageButton image = (ImageButton) findViewById(R.id.viewRecipeImage);
        Uri uri = parse(recipeIn.getImageURI());
        imageURI = uri.toString();
        image.setImageURI(uri);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creates an activity result based on the input
                Intent gallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        Button deleteRecipe = (Button) findViewById(R.id.deleteRecipe);
        deleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MealScheduleDatabaseHelper mdb = new MealScheduleDatabaseHelper(getApplicationContext());
                mdb.delete(loggedInAcct, recipeIn, date, timeOfDay);
                Intent intent = new Intent(ViewIndividualMeal.this, Homepage.class);
                intent.putExtra("account", loggedInAcct);
                startActivity(intent);
                finishAffinity();
            }
        });


        Button editRecipe = (Button) findViewById(R.id.editRecipe);

        Button cancel = (Button) findViewById(R.id.backToSavedButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ViewIndividualMeal.this, Homepage.class);
                    intent.putExtra("account", loggedInAcct);
                    startActivity(intent);
                    finishAffinity();
            }
        });


        editRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleString = title.getText().toString();
                String descriptionString = directions.getText().toString();
                String ingredientsString = ingredients.getText().toString();

                //*** Checking if the description is greater than 1000 characters
                if (directions.length() < 1000) {

                    if (imageURI.isEmpty()) {
                        //Converts bitmap to byte array
                        Uri path = parse("android.resource://com.example.chefskiss2/" + R.drawable.custom_lock_icon);
                        imageURI = path.toString();
                    }

                    Recipe recipe = new Recipe(loggedInAcct.getId(), titleString, ingredientsString, descriptionString, imageURI);
                    rdb.updateOne(recipeIn, recipe);
                    rdb.close();

                    Intent intent = new Intent(ViewIndividualMeal.this, IndividualRecipePage.class);
                    intent.putExtra("account", loggedInAcct);
                    intent.putExtra("recipe", recipe);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(ViewIndividualMeal.this, "Description must be less than 1000 characters", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Uri imageUri;
        ImageView imageView = findViewById(R.id.viewRecipeImage);

        if (resultCode == RESULT_OK && reqCode == 100){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setCropToPadding(true);
            imageURI = imageUri.toString();

        }
    }
}