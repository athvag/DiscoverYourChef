package com.team1.discoveryourchef.JsonHandler;

import java.util.ArrayList;
import java.util.List;

public class JsonRecipeResponse {
    private String url;
    private String label;
    private String image;
    private Double calories;
    private List<String> dietLabels;
    private ArrayList<String> ingredientLines;

    public JsonRecipeResponse() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public ArrayList<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(ArrayList<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    @Override
    public String toString() {
        return "JsonRecipeResponse{" +
                "uri='" + url + '\'' +
                ", label='" + label + '\'' +
                ", image='" + image + '\'' +
                ", calories=" + calories +
                ", dietLabels=" + dietLabels +
                ", ingredientLines=" + ingredientLines +
                '}';
    }
}

