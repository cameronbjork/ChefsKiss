package com.example.chefskiss2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IndividualRecipePage extends AppCompatActivity {

    private static final String TAG = "IndividualRecipePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_recipe_page);
        Log.d(TAG, "onCreate: Starting");
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
        Account acct = (Account) getIntent().getSerializableExtra("account");

        TextView title = (TextView) findViewById(R.id.textViewRecipeName);
        TextView directions = (TextView) findViewById(R.id.textViewRecipeDirections);
        TextView ingredients = (TextView) findViewById(R.id.textViewIngredients);

        title.setText(recipe.getTitle());
        directions.setText(recipe.getDirections());
        ingredients.setText(recipe.getIngredients());

        Button btnGoHome = (Button) findViewById(R.id.btnGoHome);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked btnGoHome");

                Intent intent = new Intent(IndividualRecipePage.this, SavedRecipes.class);
                intent.putExtra("account", acct);
                startActivity(intent);
                finishAffinity();

            }
        });

    }
}