package com.team1.discoveryourchef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.team1.discoveryourchef.Favorites.Favorites;
import com.team1.discoveryourchef.RecipePageRecyclerView.RecyclerAdapter2;
import com.team1.discoveryourchef.RecipePageRecyclerView.RecyclerCallback2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipesPage extends AppCompatActivity implements View.OnClickListener, RecyclerCallback2 {

    TextView recipeName, recipeCalories;
    ImageView backButton, favourite, recipePhoto;
    Button full_recipe;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    boolean isFavourite;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String recipe_name;
    int recipe_calories;
    String recipe_image;
    String recipe_ingredients;
    String recipe_link;
    String uniqueID;
    String recipeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_page);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        recipe_name = getIntent().getExtras().getString("recipeName");
        recipe_calories = getIntent().getExtras().getInt("recipeCalories");
        recipe_image = getIntent().getExtras().getString("recipeImage");
        recipe_ingredients = getIntent().getExtras().getString("recipeIngredients");
        recipe_link = getIntent().getExtras().getString("recipeLink");
        isFavourite = getIntent().getBooleanExtra("recipeIsFavorite",false);
        recipeID = getIntent().getExtras().getString("recipeID");

        backButton = findViewById(R.id.backButton);
        favourite = findViewById(R.id.favouritePhoto);
        recipeName = findViewById(R.id.recipeName);
        recipeCalories = findViewById(R.id.recipeCalories);
        recipePhoto = findViewById(R.id.recipePhoto);
        full_recipe = findViewById(R.id.full_recipe);
        recipeName.setText(recipe_name);
        recipeCalories.setText("Calories: " + recipe_calories);
        Picasso.get().load(recipe_image).placeholder(R.drawable.defaultfood).fit().into(recipePhoto);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(!isFavourite){
            favourite.setImageDrawable(getResources().getDrawable(R.drawable.notfavourite));
        }
        else{
            favourite.setImageDrawable(getResources().getDrawable(R.drawable.favourite));
        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFavourite) {
                    favourite.setImageDrawable(getResources().getDrawable(R.drawable.favourite));
                    isFavourite = true;
                    addToFirebase();
                } else if (isFavourite) {
                    favourite.setImageDrawable(getResources().getDrawable(R.drawable.notfavourite));
                    isFavourite = false;
                    removeFromFirebase();
                }

            }
        });

        full_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(recipe_link));
                startActivity(i);
            }
        });

        recyclerView = findViewById(R.id.recycler_menu2);

        List<String> arrayIngredients = new ArrayList<>();
        String[] separated = recipe_ingredients.split(",");

        for (int i=0; i < separated.length; i++) {
            arrayIngredients.add(separated[i]);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter2 adapter2 = new RecyclerAdapter2(arrayIngredients,  RecipesPage.this);
        recyclerView.setAdapter(adapter2);
    }

    private void removeFromFirebase() {
        if(recipeID != null){
            db.collection("Favorites").document(recipeID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(RecipesPage.this, "Favorite removed successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            db.collection("Favorites").document(uniqueID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(RecipesPage.this, "Favorite removed successfully!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void addToFirebase() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uniqueID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Favorites favourites = new Favorites(recipe_name, recipe_calories, recipe_image, recipe_ingredients, currentFirebaseUser.getUid(), recipe_link);
        db.collection("Favorites").document(uniqueID).set(favourites).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RecipesPage.this, "Favorite added successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClicked(View view, String ingredients) {
        //Toast.makeText(this, "Clicked: "+ ingredients, Toast.LENGTH_SHORT).show();

    }

}