package com.example.chefskiss2;

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

public class CreateRecipe extends AppCompatActivity{
    public RecipeController RC;
    private String imageURI;
    final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(this);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");

        //initialized
        EditText title = (EditText) findViewById(R.id.editTextTitle);
        EditText description = (EditText) findViewById(R.id.editTextDescription);
        EditText ingredients = (EditText) findViewById(R.id.editTextIngredients);


        ImageButton image = (ImageButton) findViewById(R.id.imageButton);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creates an activity result based on the input
                Intent gallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });


        Button createRecipe = (Button) findViewById(R.id.createRecipe);

        Button cancel = (Button) findViewById(R.id.cancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRecipe.this, Homepage.class);
                intent.putExtra("account", loggedInAcct);
                startActivity(intent);
                finishAffinity();
            }
        });


        createRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleString = title.getText().toString();
                String descriptionString = description.getText().toString();
                String ingredientsString = ingredients.getText().toString();

                //*** Checking if the description is greater than 1000 characters
                if (descriptionString.length() < 1000) {


                    if (imageURI == null) {
                        //Converts bitmap to byte array
                        Uri path = Uri.parse("android.resource://com.example.chefskiss2/" + R.drawable.custom_lock_icon);
                        imageURI = path.toString();
                    }

                    Recipe recipe = new Recipe(loggedInAcct.getId(), titleString, ingredientsString, descriptionString, imageURI);
                    rdb.addOne(recipe);
                    rdb.close();

                    Intent intent = new Intent(CreateRecipe.this, SavedRecipes.class);
                    intent.putExtra("account", loggedInAcct);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(CreateRecipe.this, "Description must be less than 1000 characters", Toast.LENGTH_SHORT).show();
                }

                }
            });
        }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Uri imageUri;
        ImageView imageView = findViewById(R.id.imageButton);

        if (resultCode == RESULT_OK && reqCode == 100){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setCropToPadding(true);
            imageURI = imageUri.toString();

        }
    }
    }
