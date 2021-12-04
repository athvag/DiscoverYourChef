package com.team1.discoveryourchef;

public class Favourites {

    private String recipeName;

    public Favourites() {
    }

    public Favourites(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
