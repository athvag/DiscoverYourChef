package com.team1.discoveryourchef;

public class Favourites {

    private String recipeName,  recipeImage, recipeLink, userId, recipeIngredients;
    private int recipeCalories;

    public Favourites() {
    }

    public Favourites(String recipeName, int recipeCalories, String recipeImage, String recipeIngredients, String userId, String recipeLink) {
        this.recipeName = recipeName;
        this.recipeCalories = recipeCalories;
        this.recipeImage = recipeImage;
        this.recipeIngredients = recipeIngredients;
        this.userId = userId;
        this.recipeLink = recipeLink;

    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeCalories() {
        return recipeCalories;
    }

    public void setRecipeCalories(int recipeCalories) {
        this.recipeCalories = recipeCalories;
    }

    public String getRecipeImage() { return recipeImage; }

    public void setRecipeImage(String recipeImage) { this.recipeImage = recipeImage; }

    public String getRecipeLink() { return recipeLink; }

    public void setRecipeLink(String recipeLink) { this.recipeLink = recipeLink; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getRecipeIngredients() { return recipeIngredients; }

    public void setRecipeIngredients(String recipeIngredients) { this.recipeIngredients = recipeIngredients; }
}
