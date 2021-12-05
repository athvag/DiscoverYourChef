package com.team1.discoveryourchef.Favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team1.discoveryourchef.R;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesItemViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private List<String> name;
    private List<Integer> calories;
    private List<String> ingredients;
    private List<String> images;
    private List<String> links;
    private FavoritesCallback callback;

    public FavoritesAdapter(List<String> name, List<Integer> calories, List<String> ingredients, List<String> images,List<String> links, FavoritesCallback callback) {
        this.name = name;
        this.calories = calories;
        this.ingredients = ingredients;
        this.images = images;
        this.links = links;
        this.callback = callback;
    }

    @NonNull
    @Override
    public FavoritesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_favorites_item, parent, false);
        return new FavoritesItemViewHolder(view,callback);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesItemViewHolder holder, int position) {
        ((FavoritesItemViewHolder) holder).bind(name.get(position),calories.get(position),ingredients.get(position),images.get(position),links.get(position));


    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}