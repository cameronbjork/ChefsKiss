package com.example.chefskiss2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Homepage extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private CustomCalendar mealCalendar;
    private ListView mealCalendarlist;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String sDate;
    private ArrayList<Meal> meals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Account loggedInAcct = (Account) getIntent().getSerializableExtra("account");

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
                        Intent intent = new Intent(Homepage.this, Homepage.class);
                        intent.putExtra("account", loggedInAcct);
                        startActivity(intent);
                        finishAffinity();
                        break;
                    case R.id.nav_saved_recipes:
                        Intent intent1 = new Intent(Homepage.this, SavedRecipes.class);
                        intent1.putExtra("account", loggedInAcct);
                        startActivity(intent1);
                        finishAffinity();
                        break;
                    case R.id.nav_create_recipes:
                        Intent intent2 = new Intent(Homepage.this, CreateRecipe.class);
                        intent2.putExtra("account", loggedInAcct);
                        startActivity(intent2);
                        finishAffinity();
                        break;
                    case R.id.nav_account:
                        Intent intent3 = new Intent(Homepage.this, AccountInfoPage.class);
                        intent3.putExtra("account", loggedInAcct);
                        startActivity(intent3);
                        finishAffinity();
                        break;
                    case R.id.nav_log_out:
                        Intent intent4 = new Intent(Homepage.this, MainActivity.class);
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


        //Meal Calendar
        mealCalendarlist = (ListView) findViewById(R.id.mealCalendarList);
        mealCalendar = findViewById(R.id.mealCalendar);
        HashMap<Object, Property> descriptionHashMap = new HashMap<>();

        //Default view for calendar dates
        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_calendar_view;
        defaultProperty.dateTextViewResource = R.id.calendarTextView;
        descriptionHashMap.put("default", defaultProperty);

        //Current Date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_calendar_view;
        currentProperty.dateTextViewResource = R.id.calendarTextView;
        descriptionHashMap.put("current", currentProperty);

        //Present Date
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_calendar_view;
        presentProperty.dateTextViewResource = R.id.calendarTextView;
        descriptionHashMap.put("present",presentProperty);

        mealCalendar.setMapDescToProp(descriptionHashMap);

        HashMap<Integer, Object> dateHashMap = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH), "current");
        dateHashMap.put(2, "present");
        dateHashMap.put(20, "present");

        mealCalendar.setDate(calendar, dateHashMap);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent results = result.getData();
                    Recipe recipePassedThrough = (Recipe) results.getSerializableExtra("recipe");
                    String time = (String) results.getSerializableExtra("time");

                    Toast.makeText(getApplicationContext(), recipePassedThrough.getTitle(), Toast.LENGTH_SHORT).show();

                    MealScheduleDatabaseHelper mdb = new MealScheduleDatabaseHelper(getApplicationContext());
                    if (recipePassedThrough != null) {
                        if(time.equals("Breakfast")) {
                            mdb.addMealForDate(sDate, "B", recipePassedThrough);
                        } else if (time.equals("Lunch")) {
                            mdb.addMealForDate(sDate, "L", recipePassedThrough);
                        } else {
                            mdb.addMealForDate(sDate, "D", recipePassedThrough);
                        }
                    }
                    mdb.close();
                }
            }
        });

        //onDateClick
        mealCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                        + "/" + selectedDate.get(Calendar.MONTH)
                        + "/" + selectedDate.get(Calendar.YEAR);

                MealScheduleDatabaseHelper mdb = new MealScheduleDatabaseHelper(getApplicationContext());
                meals = mdb.getMealsForDate(sDate, loggedInAcct);
                mdb.close();

                ArrayList<String[]> breakLunDin = new ArrayList<>();

                String[] breakfast = null;
                String[] lunch = null;
                String[] dinner = null;
                if(meals.size() > 0) {
                    for (int i = 0; i < meals.size(); i++) {
                        if (meals.get(i).getTime().equals("B")) {
                            breakfast = new String[]{"Breakfast", meals.get(i).getRecipeTitle()};
                            breakLunDin.add(breakfast);
                        } else if (meals.get(i).getTime().equals("L")) {
                            lunch = new String[]{"Lunch", meals.get(i).getRecipeTitle()};
                            breakLunDin.add(lunch);
                        } else if (meals.get(i).getTime().equals("D")) {
                            dinner = new String[]{"Dinner", meals.get(i).getRecipeTitle()};
                            breakLunDin.add(dinner);
                        }
                    }
                } else {
                    breakfast = new String[]{"Breakfast"};
                    lunch = new String[]{"Lunch"};
                    dinner = new String[]{"Dinner"};
                    breakLunDin.add(breakfast);
                    breakLunDin.add(lunch);
                    breakLunDin.add(dinner);
                }

                if (breakfast == null) {
                    breakfast = new String[]{"Breakfast"};
                    breakLunDin.add(breakfast);
                }
                if (lunch == null) {
                    lunch = new String[]{"Lunch"};
                    breakLunDin.add(lunch);
                }

                if (dinner == null) {
                    dinner = new String[]{"Dinner"};
                    breakLunDin.add(dinner);
                }

                MealScheduleListAdapter adapter = new MealScheduleListAdapter(getApplicationContext(), R.layout.adapter_meal_layout, breakLunDin);

                //List for navigating meals
                mealCalendarlist.setAdapter(adapter);

                mealCalendarlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] item = (String[]) adapterView.getItemAtPosition(i);

                        Toast.makeText(getApplicationContext(), item[0], Toast.LENGTH_SHORT).show();
                        if(item.length == 1) {
                            Intent intent = new Intent(Homepage.this, SelectRecipe.class);
                            intent.putExtra("account", loggedInAcct);
                            intent.putExtra("time", item[0]);
                            activityResultLauncher.launch(intent);
                        } else {
                            Recipe recipeToSend = new Recipe(0,"","","");
                            for(int i2=0; i < meals.size(); i++){
                                if (meals.get(i2).getRecipeTitle().equals(item[1])) {
                                    RecipeDatabaseHelper rdb = new RecipeDatabaseHelper(getApplicationContext());
                                    recipeToSend = rdb.findRecipeByIdAndTitle(loggedInAcct.getId(), item[1]);
                                }
                            }

                            Intent intent2 = new Intent(Homepage.this, IndividualRecipePage.class);
                            intent2.putExtra("account", loggedInAcct);
                            intent2.putExtra("recipe", recipeToSend);
                            intent2.putExtra("from", "Home");
                            startActivity(intent2);
                            finish();
                        }
                    }
                });

            }
        });
    }
}