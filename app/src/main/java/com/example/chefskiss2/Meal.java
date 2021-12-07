package com.example.chefskiss2;

public class Meal {
    int id;
    String date;
    String recipeTitle;
    String time;

    public Meal(int id1, String dateFromQ, String recipeTitle, String time) {
        id = id1;
        date = dateFromQ;
        this.recipeTitle = recipeTitle;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
