package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class AccountInfoPage extends AppCompatActivity {
    private Account loggedInAcct;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    private static final String TAG = "AccountInfoPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info_page);
        DatabaseHelper db = new DatabaseHelper(this);
        Log.d(TAG, "onCreate: Starting.");

        loggedInAcct = (Account) getIntent().getSerializableExtra("account");

        EditText emailEditText = (EditText) findViewById(R.id.userEmail2);
        EditText passwordEditText = (EditText) findViewById(R.id.password2);
        EditText usernameEditText = (EditText) findViewById(R.id.username2);

        emailEditText.setText(loggedInAcct.getEmail());
        emailEditText.setText(loggedInAcct.getEmail());
        usernameEditText.setText(loggedInAcct.getUsername());
        passwordEditText.setText(loggedInAcct.getPassword());

        toolbar = (MaterialToolbar) findViewById(R.id.topAppbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

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
                        Intent intent = new Intent(AccountInfoPage.this, Homepage.class);
                        intent.putExtra("account", loggedInAcct);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    case R.id.nav_saved_recipes:
                        Intent intent1 = new Intent(AccountInfoPage.this, SavedRecipes.class);
                        intent1.putExtra("account", loggedInAcct);
                        startActivity(intent1);
                        finishAffinity();
                        break;
                    case R.id.nav_create_recipes:
                        Intent intent2 = new Intent(AccountInfoPage.this, CreateRecipe.class);
                        intent2.putExtra("account", loggedInAcct);
                        startActivity(intent2);
                        finishAffinity();
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(AccountInfoPage.this, AccountInfoPage.class);
                        intent3.putExtra("account", loggedInAcct);
                        startActivity(intent3);
                        finishAffinity();
                        break;
                    case R.id.nav_log_out:
                        Intent intent4 = new Intent(AccountInfoPage.this, MainActivity.class);
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



        Button editaccountbtn = (Button) findViewById(R.id.buttontoedit);

        editaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked editaccountbtn.");

                String emailString = emailEditText.getText().toString();
                String passwordString = passwordEditText.getText().toString();
                String usernameString = usernameEditText.getText().toString();

                Account newAcc = new Account(usernameString, emailString, passwordString);

                db.updateOne(loggedInAcct, newAcc);

                Intent intent = new Intent(AccountInfoPage.this, AccountInfoPage.class);
                intent.putExtra("account", newAcc);
                startActivity(intent);
                AccountInfoPage.this.finish();

            }
        });
    }
}