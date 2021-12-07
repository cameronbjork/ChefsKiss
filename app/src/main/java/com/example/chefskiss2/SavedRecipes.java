package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


//GEts the saved recipse
public class SavedRecipes extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");


        toolbar = (MaterialToolbar) findViewById(R.id.topAppbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
                TextView text = findViewById(R.id.nav_menu_name);
                text.setText(loggedInAcct.getUsername());

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                //drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.nav_home:
                        Intent intent = new Intent(SavedRecipes.this, Homepage.class);
                        intent.putExtra("account", loggedInAcct);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    case R.id.nav_saved_recipes:
                        Intent intent1 = new Intent(SavedRecipes.this, SavedRecipes.class);
                        intent1.putExtra("account", loggedInAcct);
                        startActivity(intent1);
                        finishAffinity();
                        break;
                    case R.id.nav_create_recipes:
                        Intent intent2 = new Intent(SavedRecipes.this, CreateRecipe.class);
                        intent2.putExtra("account", loggedInAcct);
                        startActivity(intent2);
                        finishAffinity();
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(SavedRecipes.this, AccountInfoPage.class);
                        intent3.putExtra("account", loggedInAcct);
                        startActivity(intent3);
                        finishAffinity();
                        break;
                    case R.id.nav_log_out:
                        Intent intent4 = new Intent(SavedRecipes.this, MainActivity.class);
                        startActivity(intent4);
                        finishAffinity();
                        break;
                    default:
                        return true;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

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
                intent.putExtra("account", loggedInAcct);
                intent.putExtra("recipe", r);
                intent.putExtra("from", "Saved");
                startActivity(intent);
                finishAffinity();
            }
        });

    }
}
