package com.team1.discoveryourchef.HomePageRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team1.discoveryourchef.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<String> name;
    private List<Integer> calories;
    private List<String> ingredients;
    private List<String> images;

    public RecyclerAdapter(List<String> name, List<Integer> calories, List<String> ingredients,List<String> images) {
        this.name = name;
        this.calories = calories;
        this.ingredients = ingredients;
        this.images = images;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_menu_item, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecyclerItemViewHolder) holder).bind(name.get(position),calories.get(position),ingredients.get(position),images.get(position));

    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}

