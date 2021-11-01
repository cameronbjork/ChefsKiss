package com.example.chefskiss2;


import java.io.Serializable;

public class Recipe implements Serializable {
    private String title;
    private String ingredients;
    private String directions;
    private String id;


    public Recipe(String title, String ingredients, String directions) {
        this.title = title;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public String getTitle() {
        return this.title;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public String getDirections() {
        return this.directions;
    }


}
