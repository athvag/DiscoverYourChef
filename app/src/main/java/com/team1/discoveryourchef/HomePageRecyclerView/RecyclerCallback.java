package com.team1.discoveryourchef.HomePageRecyclerView;

import android.view.View;

import java.util.List;

public interface RecyclerCallback {
    public void onItemClicked(View view, String name, int calories, String image, String ingredients);
}
