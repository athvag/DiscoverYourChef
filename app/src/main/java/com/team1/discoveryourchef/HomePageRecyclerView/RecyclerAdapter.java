package com.team1.discoveryourchef.HomePageRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.team1.discoveryourchef.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<String> name;
    private List<Integer> calories;
    private List<String> ingredients;
    private List<String> images;
    private RecyclerCallback callback;

    public RecyclerAdapter(List<String> name, List<Integer> calories, List<String> ingredients, List<String> images, RecyclerCallback callback) {
        this.name = name;
        this.calories = calories;
        this.ingredients = ingredients;
        this.images = images;
        this.callback =  callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holden_food_item, parent, false);
        return new RecyclerItemViewHolder(view,callback);
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

