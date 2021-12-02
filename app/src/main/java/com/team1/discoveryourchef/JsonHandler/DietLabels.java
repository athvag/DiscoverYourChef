package com.team1.discoveryourchef.JsonHandler;

import java.util.List;

public class DietLabels {
    private List<String> dietLabels;

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    @Override
    public String toString() {
        return "DietLabels{" +
                "dietLabels=" + dietLabels +
                '}';
    }
}
