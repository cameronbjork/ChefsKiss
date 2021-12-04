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

import org.naishadhparmar.zcustomcalendar.*;
import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Homepage extends AppCompatActivity implements OnNavigationButtonClickedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private CustomCalendar mealCalendar;
    private ListView mealCalendarlist;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String sDate;
    private ArrayList<Meal> meals;
    private int tempClickPosition = 0;
    private Account loggedInAcct;
    private int someSelected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        loggedInAcct = (Account) getIntent().getSerializableExtra("account");

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
        mealCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        mealCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);

        //Default view for calendar dates
        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_calendar_view;
        defaultProperty.dateTextViewResource = R.id.calendarTextView;
        descriptionHashMap.put("default", defaultProperty);

        //Current Date resource
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_calendar_view;
        currentProperty.dateTextViewResource = R.id.calendarTextView;
        descriptionHashMap.put("current", currentProperty);

        //Some Recipes saved
        Property someProperty = new Property();
        someProperty.layoutResource = R.layout.somerecipes_calendar_view;
        someProperty.dateTextViewResource = R.id.calendarTextView;
        descriptionHashMap.put("some", someProperty);

        //Selected Date resource
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_calendar_view;
        presentProperty.dateTextViewResource = R.id.calendarTextView;
        descriptionHashMap.put("present",presentProperty);

        //Sets the map, and creates specific datet HashMap
        mealCalendar.setMapDescToProp(descriptionHashMap);
        HashMap<Integer, Object> dateHashMap = new HashMap<>();

        //This Calendar is used to get the current date, it is default by android
        //and has a clock to move every day.
        Calendar calendar = Calendar.getInstance();
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH), "current");

        //Get all meals saved in DB
        MealScheduleDatabaseHelper mdb = new MealScheduleDatabaseHelper(getApplicationContext());
        ArrayList<Meal> mealsforMonth = mdb.getMealsForMonth( "" + calendar.get(Calendar.MONTH), loggedInAcct);
        mdb.close();

        String currentDay = "";
        boolean b = false;
        boolean l = false;
        boolean d = false;
        for (int i = 0; i < mealsforMonth.size(); i++) {

            //if the date is the same as the previous entry,
            if(mealsforMonth.get(i).getDate().equals(currentDay) || currentDay == "") {
                if (mealsforMonth.get(i).getTime() == "B") {
                    b = true;
                } else if (mealsforMonth.get(i).getTime() == "L") {
                    l = true;
                } else if (mealsforMonth.get(i).getTime() == "D") {
                    d = true;
                }
            } else {
                b = false;
                l = false;
                d = false;

                if (mealsforMonth.get(i).getTime() == "B") {
                    b = true;
                } else if (mealsforMonth.get(i).getTime() == "L") {
                    l = true;
                } else if (mealsforMonth.get(i).getTime() == "D") {
                    d = true;
                }
            }

            String[] dateList = mealsforMonth.get(i).getDate().split("/");
            currentDay = dateList[0];

            if(b && l && d) {
                dateHashMap.put(Integer.parseInt(dateList[0]), "all");
            } else {
                dateHashMap.put(Integer.parseInt(dateList[0]), "some");
            }


        }

        mealCalendar.setDate(calendar, dateHashMap);

        //This is for after a recipe is passed through to save for a specific date
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent results = result.getData();
                    Recipe recipePassedThrough = (Recipe) results.getSerializableExtra("recipe");
                    String time = (String) results.getSerializableExtra("time");

                    //Saving Recipe to database
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

                //Date in DD/MM/YYYY format
                sDate = selectedDate.get(Calendar.DAY_OF_MONTH)
                        + "/" + selectedDate.get(Calendar.MONTH)
                        + "/" + selectedDate.get(Calendar.YEAR);

                //Remove previously clicked orange button
                if (tempClickPosition != 0) {
                    if (someSelected == 0) {
                        dateHashMap.put(tempClickPosition, "default");
                    } else {
                        dateHashMap.put(tempClickPosition, "some");
                    }
                    mealCalendar.setDate(calendar, dateHashMap);
                }

                //Sets clicked as orange
                dateHashMap.put(selectedDate.get(Calendar.DAY_OF_MONTH), "present");
                mealCalendar.setDate(selectedDate, dateHashMap);
                tempClickPosition = selectedDate.get(Calendar.DAY_OF_MONTH);

                //Gets meals for the date selected
                MealScheduleDatabaseHelper mdb = new MealScheduleDatabaseHelper(getApplicationContext());
                meals = mdb.getMealsForDate(sDate, loggedInAcct);
                mdb.close();

                //List for UI, this is connected to MealScheduleListAdapter
                ArrayList<String[]> breakLunDin = new ArrayList<>();
                String[] breakfast = null;
                String[] lunch = null;
                String[] dinner = null;

                //If there is any meals saved, it will loop through and get the title to place next
                //to breakfast. If not, it will just do breakfast lunch and dinner.
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
                        someSelected = 1;
                    }
                } else {
                    breakfast = new String[]{"Breakfast"};
                    lunch = new String[]{"Lunch"};
                    dinner = new String[]{"Dinner"};
                    breakLunDin.add(breakfast);
                    breakLunDin.add(lunch);
                    breakLunDin.add(dinner);
                    someSelected = 0;
                }

                //This is for when there is less than 3 but more than 1 meal
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

                //Creates the list
                MealScheduleListAdapter adapter = new MealScheduleListAdapter(getApplicationContext(), R.layout.adapter_meal_layout, breakLunDin);
                mealCalendarlist.setAdapter(adapter);

                //When specific meal or schedule is clicked.
                mealCalendarlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String[] item = (String[]) adapterView.getItemAtPosition(i);

                        //Checks if there is a recipe or not a recipe saved
                        if(item.length == 1) {
                            //Select a recipe for your meal
                            Intent intent = new Intent(Homepage.this, SelectRecipe.class);
                            intent.putExtra("account", loggedInAcct);
                            intent.putExtra("time", item[0]);
                            //This goes back up to the overrided method on line 149
                            activityResultLauncher.launch(intent);
                        } else {
                            //View singular recipe
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

    //This is whenever the page is navigated to a different month.
    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {

        Map<Integer, Object>[] arr = new Map[12];
        MealScheduleDatabaseHelper mdb = new MealScheduleDatabaseHelper(getApplicationContext());
        ArrayList<Meal> meals;
        switch(newMonth.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("0", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                    }
                }
                break;
            case Calendar.FEBRUARY:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("1", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.MARCH:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("2", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.APRIL:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("3", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.MAY:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("4", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.JUNE:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("5", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.JULY:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("6", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.AUGUST:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("7", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.SEPTEMBER:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("8", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.OCTOBER:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("9", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.NOVEMBER:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("10", loggedInAcct);
                if (!meals.isEmpty()) {
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                }
                break;
            case Calendar.DECEMBER:
                arr[0] = new HashMap<>();
                meals = mdb.getMealsForMonth("11", loggedInAcct);
                    for (int i = 0; i < meals.size(); i++) {
                        String[] dateSplit = meals.get(i).getDate().split("/");
                        arr[0].put(Integer.parseInt(dateSplit[0]), "some");
                        arr[1] = null;
                    }
                break;

        }
        return arr;
    }
}