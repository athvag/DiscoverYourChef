package com.team1.discoveryourchef.RecipePageRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team1.discoveryourchef.HomePageRecyclerView.RecyclerCallback;
import com.team1.discoveryourchef.HomePageRecyclerView.RecyclerItemViewHolder;
import com.team1.discoveryourchef.R;

import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> ingredients;
    private RecyclerCallback callback;

    public RecyclerAdapter2(List<String> ingredients, RecyclerCallback callback) {
        this.ingredients = ingredients;
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
        ((RecyclerItemViewHolder2) holder).bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
