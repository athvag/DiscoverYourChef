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

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerItemViewHolder2> {

    private List<String> ingredients;
    private RecyclerCallback2 callback2;

    public RecyclerAdapter2(List<String> ingredients, RecyclerCallback2 callback2) {
        this.ingredients = ingredients;
        this.callback2 =  callback2;
    }

    @NonNull
    @Override
    public RecyclerItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_recipe_item, parent, false);
        return new RecyclerItemViewHolder2(view, (RecyclerCallback2) callback2);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerItemViewHolder2 holder, int position) {
        ( holder).bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
