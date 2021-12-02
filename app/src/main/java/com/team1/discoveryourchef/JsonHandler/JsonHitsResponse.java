package com.team1.discoveryourchef.JsonHandler;

public class JsonHitsResponse {
    private JsonRecipeResponse recipe;


    public JsonHitsResponse() {
    }



    public JsonRecipeResponse getRecipe() {
        return recipe;
    }

    public void setRecipe(JsonRecipeResponse recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "JsonHitsResponse{" +
                "recipe=" + recipe +
                '}';
    }
}
