package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class Homepage extends AppCompatActivity {

    public Homepage() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");

        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.nav_home:
                        Toast.makeText(Homepage.this, "Home is Clicked",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Homepage.this, Homepage.class);
                        intent.putExtra("account", loggedInAcct);
                        startActivity(intent);
                        break;
                    case R.id.nav_saved_recipes:
                        Toast.makeText(Homepage.this, "Saved Recipes is Clicked",Toast.LENGTH_SHORT).show();
                        //Intent intent1 = new Intent()
                        break;
                    case R.id.nav_create_recipes:
                        Intent intent2 = new Intent(Homepage.this, CreateRecipe.class);
                        intent2.putExtra("account", loggedInAcct);
                        startActivity(intent2);
                        Toast.makeText(Homepage.this, "Create Recipes is Clicked",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(Homepage.this, AccountInfoPage.class);
                        intent3.putExtra("account", loggedInAcct);
                        startActivity(intent3);
                        Toast.makeText(Homepage.this, "Account is Clicked",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_log_out:
                        Intent intent4 = new Intent(Homepage.this, MainActivity.class);
                        startActivity(intent4);
                        finishAffinity();break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }
}