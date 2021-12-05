package com.team1.discoveryourchef.Favorites;

import android.view.View;

public interface FavoritesCallback {
    public void onItemClicked(View view, String name, Integer calories, String image, String ingredients,String link);
}
