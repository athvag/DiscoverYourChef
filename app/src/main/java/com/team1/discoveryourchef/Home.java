package com.team1.discoveryourchef;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        String fullName = getIntent().getExtras().getString("fullName");
        welcome = findViewById(R.id.welcome);

        welcome.setText("Welcome "+ fullName);
    }
}