package com.team1.discoveryourchef.RecipePageRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.team1.discoveryourchef.HomePageRecyclerView.RecyclerCallback;
import com.team1.discoveryourchef.R;

public class RecyclerItemViewHolder2 extends RecyclerView.ViewHolder {

    private RecyclerCallback2 callback2;

    public RecyclerItemViewHolder2(@NonNull View itemView, RecyclerCallback2 listener) {
        super(itemView);
        callback2 = listener;
    }

    public void bind(String ingredients) {

        TextView food_desc = itemView.findViewById(R.id.food1_desc);


        /** Bind the data to the views **/
        food_desc.setText(ingredients);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback2.onItemClicked(v, ingredients);
            }
        });
    }
}
