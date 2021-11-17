package com.example.chefskiss2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SavedRecipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");

        ListView list = (ListView) findViewById(R.id.recipeList);

        RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(this);

        ArrayList<Recipe> recipeList = rdb.getSavedRecipes(loggedInAcct);
        rdb.close();
        RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.adapter_recipe_layout, recipeList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe r = (Recipe) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SavedRecipes.this, IndividualRecipePage.class);
                intent.putExtra("recipe", r);
                intent.putExtra("account", loggedInAcct);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}