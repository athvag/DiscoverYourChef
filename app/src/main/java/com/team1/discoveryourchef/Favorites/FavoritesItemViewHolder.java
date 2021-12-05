package com.team1.discoveryourchef.Favorites;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team1.discoveryourchef.R;

public class FavoritesItemViewHolder extends RecyclerView.ViewHolder {
    private FavoritesCallback callback;

    TextView fav_food_name, fav_food_calories, fav_food_description;
    ImageView fav_image;

    public FavoritesItemViewHolder(@NonNull View itemView, FavoritesCallback listener) {
        super(itemView);
        callback = listener;

        fav_food_name = itemView.findViewById(R.id.fav_name_header);
        fav_food_calories = itemView.findViewById(R.id.fav_cal_header);
        fav_food_description = itemView.findViewById(R.id.fav_desc);
        fav_image = itemView.findViewById(R.id.fav_image);
    }

    public void bind(String name, Integer calories, String ingredients, String image,String link) {

        // Bind the data to the views //
        Picasso.get().load(image).placeholder(R.drawable.defaultfood).fit().into(fav_image);
        fav_food_name.setText(name);
        fav_food_calories.setText(calories + " Calories");
        fav_food_description.setText(ingredients);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClicked(v, name, calories, image, ingredients, link);
            }
        });

    }
}