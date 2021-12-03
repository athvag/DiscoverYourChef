package com.team1.discoveryourchef.HomePageRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team1.discoveryourchef.R;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder{


    private RecyclerCallback callback;

    public RecyclerItemViewHolder(@NonNull View itemView,RecyclerCallback listener) {
        super(itemView);
        callback = listener;


    }

    public void bind(String name, Integer calories, String ingredients,String image){

        TextView food_name = itemView.findViewById(R.id.food1_name_header);
        TextView food_calories = itemView.findViewById(R.id.food1_cal_header);
        TextView food_desc = itemView.findViewById(R.id.food1_desc);
        ImageView food_image = itemView.findViewById(R.id.food1_image);

        /** Bind the data to the views **/
        Picasso.get().load(image).placeholder(R.drawable.defaultfood).fit().into(food_image);
        food_name.setText(name);
        food_calories.setText(calories.toString()+" Calories");
        food_desc.setText(ingredients);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClicked(v,name,calories,image,ingredients);
            }
        });

    }
}

