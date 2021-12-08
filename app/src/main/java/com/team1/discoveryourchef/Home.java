package com.team1.discoveryourchef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.android.volley.Response;
import com.team1.discoveryourchef.Favorites.FavoritesPage;
import com.team1.discoveryourchef.HomePageRecyclerView.RecyclerAdapter;
import com.team1.discoveryourchef.HomePageRecyclerView.RecyclerCallback;
import com.team1.discoveryourchef.JsonHandler.JsonResponse;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener, RecyclerCallback {

    private DrawerLayout drawer;
    TextView welcome;
    List<String> names = new ArrayList<>();
    List<Integer> calories = new ArrayList<>();
    List<String> ingredients = new ArrayList<>();
    List<String> images = new ArrayList<>();
    List<String> links = new ArrayList<>();
    RecyclerView recyclerView;
    CardView clickItem1, clickItem2, clickItem3, clickItem4, clickItem5, clickItem6, clickItem7, clickItem8;
    TextView foodLabel;
    AlertDialog dialog;
    GridLayoutManager gridLayoutManager;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        String fullName = getIntent().getExtras().getString("fullName");
        String email = getIntent().getExtras().getString("email");
        welcome = findViewById(R.id.welcome);
        welcome.setText("Welcome " + fullName);
        profile = findViewById(R.id.account_circle);
        //Create the loading window//
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog, null));
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //End of loading window//


        foodLabel = findViewById(R.id.food_header);

        recyclerView = findViewById(R.id.recycler_menu);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Initiate the clicks on the categories//
        clickItem1 = findViewById(R.id.cat_menu_1);
        clickItem2 = findViewById(R.id.cat_menu_2);
        clickItem3 = findViewById(R.id.cat_menu_3);
        clickItem4 = findViewById(R.id.cat_menu_4);
        clickItem5 = findViewById(R.id.cat_menu_5);
        clickItem6 = findViewById(R.id.cat_menu_6);
        clickItem7 = findViewById(R.id.cat_menu_7);
        clickItem8 = findViewById(R.id.cat_menu_8);
        drawer = findViewById(R.id.drawer_layout);
        //Disable left to right swipe to open navigation drawer
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        clickItem1.setOnClickListener(this);
        clickItem2.setOnClickListener(this);
        clickItem3.setOnClickListener(this);
        clickItem4.setOnClickListener(this);
        clickItem5.setOnClickListener(this);
        clickItem6.setOnClickListener(this);
        clickItem7.setOnClickListener(this);
        clickItem8.setOnClickListener(this);
        profile.setOnClickListener(this);
        //Finish initiating the clicks//

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.profile_item:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.favorites_item:
                        Intent i = new Intent(Home.this, FavoritesPage.class);
                        startActivity(i);
                        break;
                    case R.id.logout_item:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(Home.this, MainActivity.class ));
                        break;
                }
                return false;
            }
        });

        //Load random pizza recipies//
        loadRecipies("&q=pizza");

        /*
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, FavoritesPage.class);
                startActivity(intent);
            }
        });
         */
    }

    private void clearLast() {
        //Used to clear the arrays containing the recycler view items, so new items can load//
        names.clear();
        calories.clear();
        ingredients.clear();
        images.clear();
    }

    //Handle the orientation changes//
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(Home.this, 2, GridLayoutManager.VERTICAL, false); //This is used to create 2 collumns to the recyclerview//
        } else {
            gridLayoutManager = new GridLayoutManager(Home.this, 4, GridLayoutManager.VERTICAL, false); //This is used to create 4 collumns to the recyclerview//
        }
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    //Basic function to handle the recipe load

    private void loadRecipies(String queue) {

        String tempUrl = "";
        dialog.show(); //Show the loading animation

        tempUrl = "https://api.edamam.com/api/recipes/v2?type=public" + queue + "&app_id=04d3b2e6&app_key=e6bcb688109bd063ca951d0f9f1834ad&random=true";//The API call link

        StringRequest stringRequest = new StringRequest(Request.Method.GET, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class); //API response to JSON


                //For every JSON object found, add its name,calories,ingredients and image to an array//
                //The Lists names,calories,ingredients,images will include all the data//
                //Use them for intents//
                for (int i = 0; i < jsonResponse.getHits().size(); i++) {
                    names.add(jsonResponse.getHits().get(i).getRecipe().getLabel());
                    int a = (int) Math.round(jsonResponse.getHits().get(i).getRecipe().getCalories());
                    calories.add(a);
                    ingredients.add(jsonResponse.getHits().get(i).getRecipe().getIngredientLines().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
                    images.add(jsonResponse.getHits().get(i).getRecipe().getImage());
                    links.add(jsonResponse.getHits().get(i).getRecipe().getUrl());
                }
                //If there are JSON objects in the query//
                if (!names.isEmpty()) {

                    RecyclerAdapter adapter = new RecyclerAdapter(names, calories, ingredients, images, links,Home.this);  //Create a new adapter with the items added above//
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { //Handle the orientation changes//
                        gridLayoutManager = new GridLayoutManager(Home.this, 2, GridLayoutManager.VERTICAL, false); //This is used to create 2 collumns to the recyclerview//

                    } else {
                        gridLayoutManager = new GridLayoutManager(Home.this, 4, GridLayoutManager.VERTICAL, false); //This is used to create 4 collumns to the recyclerview//

                    }
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter); //Add the adapter to the view//
                } else { //If there are no JSON objects in the query
                    Toast.makeText(Home.this, "Our fridge is empty, try another category", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss(); //Remove the loading animation
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //This handles any errors sent from the API call
                Toast.makeText(getApplicationContext(), "Slow down, our cooks are tired", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


    @Override
    public void onClick(View v) { //Handle the clicks of the Category items//
        switch (v.getId()) {
            case R.id.cat_menu_1:
                refreshView("Starter Food","&dishType=Starter");
                break;
            case R.id.cat_menu_2:
                refreshView("Breads","&dishType=Bread");
                break;
            case R.id.cat_menu_3:
                refreshView("Cereals","&dishType=Cereals");
                break;
            case R.id.cat_menu_4:
                refreshView("Side Dishes","&dishType=Side Disk");
                break;
            case R.id.cat_menu_5:
                refreshView("Soups","&dishType=Soup");
                break;
            case R.id.cat_menu_6:
                refreshView("Main Course","&dishType=Main Course");
                break;
            case R.id.cat_menu_7:
                refreshView("Pancakes","&dishType=Pancakes");
                break;
            case R.id.cat_menu_8:
                refreshView("Sweets","&dishType=Sweets");
                break;
            case R.id.account_circle:
                /*
                String fullName = getIntent().getExtras().getString("fullName");
                String email = getIntent().getExtras().getString("email");
                 */
                String [] mainActExtra = getIntent().getStringArrayExtra("fnem");
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_username);
                TextView txtEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email);
                txtProfileName.setText(mainActExtra[0]);
                txtEmail.setText(mainActExtra[1]);
                drawer.openDrawer(Gravity.LEFT);
                break;
        }
    }

    private void refreshView(String food_type, String s) {
        clearLast();
        foodLabel.setText(food_type);
        loadRecipies(s);
    }


    //On every item click send data to recipe activity//
    //Uncomment the next lines and add your class to the intent//
    @Override
    public void onItemClicked(View view, String name, int calories, String image, String ingredients, String links) {
        //Toast.makeText(this, "Clicked: "+ name + calories + image + ingredients, Toast.LENGTH_SHORT).show();
        Intent gotoRecipe = new Intent(Home.this,RecipesPage.class);
        gotoRecipe.putExtra("recipeName",name);
        gotoRecipe.putExtra("recipeCalories",calories);
        gotoRecipe.putExtra("recipeImage",image);
        gotoRecipe.putExtra("recipeIngredients",ingredients);
        gotoRecipe.putExtra("recipeLink", links);
        startActivity(gotoRecipe);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Home.this, "Use the \"Logout\" option from the user menu to logout", Toast.LENGTH_SHORT).show();
    }
}