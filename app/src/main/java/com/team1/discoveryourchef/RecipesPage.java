package com.team1.discoveryourchef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.team1.discoveryourchef.HomePageRecyclerView.RecyclerAdapter;
import com.team1.discoveryourchef.HomePageRecyclerView.RecyclerCallback;
import com.team1.discoveryourchef.RecipePageRecyclerView.RecyclerAdapter2;
import com.team1.discoveryourchef.RecipePageRecyclerView.RecyclerCallback2;

import java.util.ArrayList;
import java.util.List;

public class RecipesPage extends AppCompatActivity implements View.OnClickListener, RecyclerCallback2 {

    TextView recipeName, recipeCalories;
    ImageView backButton, favourite, recipePhoto;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_page);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        String recipe_name = getIntent().getExtras().getString("recipeName");
        int recipe_calories = getIntent().getExtras().getInt("recipeCalories");
        String recipe_image = getIntent().getExtras().getString("recipeImage");
        String recipe_ingredients = getIntent().getExtras().getString("recipeIngredients");

        backButton = findViewById(R.id.backButton);
        favourite = findViewById(R.id.favouritePhoto);
        recipeName = findViewById(R.id.recipeName);
        recipeCalories = findViewById(R.id.recipeCalories);
        recipePhoto = findViewById(R.id.recipePhoto);
        recipeName.setText(recipe_name);
        recipeCalories.setText("Calories: " + recipe_calories);
        Picasso.get().load(recipe_image).placeholder(R.drawable.defaultfood).fit().into(recipePhoto);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final boolean[] favouriteRecipe = {false};

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!favouriteRecipe[0]) {
                    favourite.setImageDrawable(getResources().getDrawable(R.drawable.favourite));
                    favouriteRecipe[0] = true;
                } else if (favouriteRecipe[0]) {
                    favourite.setImageDrawable(getResources().getDrawable(R.drawable.notfavourite));
                    favouriteRecipe[0] = false;
                }

            }
        });

        recyclerView = findViewById(R.id.recycler_menu2);

        List<String> arrayIngredients = new ArrayList<>();
        String[] separated = recipe_ingredients.split(",");

        for (int i=0; i < separated.length; i++) {
            arrayIngredients.add(separated[i]);
        }
        RecyclerAdapter2 adpt = new RecyclerAdapter2(arrayIngredients, (RecyclerCallback) RecipesPage.this);
        //RecyclerAdapter2 adapter2 = new RecyclerAdapter2(arrayIngredients, (RecyclerCallback) this);
        recyclerView.setAdapter(adpt);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClicked(View view, String ingredients) {
        Toast.makeText(this, "Clicked: "+ ingredients, Toast.LENGTH_SHORT).show();

    }

}