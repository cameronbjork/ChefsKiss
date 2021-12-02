package com.example.chefskiss2;


import java.io.Serializable;

public class Recipe implements Serializable {
    private String title;
    private String ingredients;
    private String directions;
    private int id;
    private String imageURI;


    public Recipe(int id, String title, String ingredients, String directions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public Recipe(int id, String title, String ingredients, String directions, String imageURI) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.directions = directions;
        this.imageURI = imageURI;
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

    public int getId() { return this.id; }

    public String getImageURI() { return imageURI; }
}
