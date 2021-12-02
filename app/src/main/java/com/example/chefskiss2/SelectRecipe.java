package com.example.chefskiss2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SelectRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");
        String time = (String) getIntent().getSerializableExtra("time");

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
                Intent returnIntent = new Intent();
                returnIntent.putExtra("recipe", r);
                returnIntent.putExtra("time", time);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        Button backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}
