package com.team1.discoveryourchef.Favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.team1.discoveryourchef.Home;
import com.team1.discoveryourchef.R;
import com.team1.discoveryourchef.RecipesPage;

import java.util.ArrayList;
import java.util.List;

public class FavoritesPage extends AppCompatActivity implements FavoritesCallback {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ImageView back;

    private FavoritesAdapter favoritesSimpleAdapter;

    LinearLayoutManager linearLayoutManager;


    List<String> names = new ArrayList<>();
    List<Integer> calories = new ArrayList<Integer>();
    List<String> ingredients = new ArrayList<>();
    List<String> images = new ArrayList<>();
    List<String> links = new ArrayList<>();
    List<String> recipeID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        recyclerView = findViewById(R.id.recycler_view_favorites);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        names.clear();
        calories.clear();
        ingredients.clear();
        images.clear();
        links.clear();
        recipeID.clear();
        createRecyclerView();
        back = findViewById(R.id.favorites_backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void createRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("Favorites")
                .whereEqualTo("userId", currentFirebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                names.add(document.getString("recipeName"));

                                //int a = (int) Math.round(jsonResponse.getHits().get(i).getRecipe().getCalories());
                                int caloriesString = document.getLong("recipeCalories").intValue();
                                calories.add(caloriesString);

                                ingredients.add(document.getString("recipeIngredients"));

                                images.add(document.getString("recipeImage"));

                                links.add(document.getString("recipeLink"));
                                recipeID.add(document.getId());

                            }
                            favoritesSimpleAdapter = new FavoritesAdapter(names, calories, ingredients, images,links,recipeID, FavoritesPage.this);

                            favoritesSimpleAdapter.notifyDataSetChanged();

                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(favoritesSimpleAdapter);


                        } else {
                            Toast.makeText(FavoritesPage.this, "No favorites added yet, go add some", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @Override
    protected void onRestart() {
        names.clear();
        calories.clear();
        ingredients.clear();
        images.clear();
        links.clear();
        recipeID.clear();
        createRecyclerView();
        super.onRestart();
    }

    @Override
    protected void onPostResume() {

        super.onPostResume();


    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    //Decide what happens on each item of the recyclerview clicked
    @Override
    public void onItemClicked(View view, String name, Integer calories, String image, String ingredients,String links,Boolean isFavorite,String recipeID) {
        Intent gotoRecipe = new Intent(FavoritesPage.this, RecipesPage.class);
        gotoRecipe.putExtra("recipeName",name);
        gotoRecipe.putExtra("recipeCalories",calories);
        gotoRecipe.putExtra("recipeImage",image);
        gotoRecipe.putExtra("recipeIngredients",ingredients);
        gotoRecipe.putExtra("recipeLink", links);
        gotoRecipe.putExtra("recipeIsFavorite",isFavorite);
        gotoRecipe.putExtra("recipeID",recipeID);
        startActivity(gotoRecipe);
    }
}

