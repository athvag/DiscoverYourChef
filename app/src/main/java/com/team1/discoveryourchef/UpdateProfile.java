package com.team1.discoveryourchef;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateProfile extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button BtUpdate, BtDelete;
    ImageView back;
    EditText EtEmail, EtFullname, EtPassword;
    String userEmail, userFullname, userPassword;


    //Global variables to keep user data inside this activity
    String _Email, _FullName, _Password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        EtEmail= findViewById(R.id.register_email);
        EtFullname = findViewById(R.id.register_name);
        EtPassword = findViewById(R.id.register_password);

        _FullName = getIntent().getExtras().getString("FULL_NAME");
        _Email = getIntent().getExtras().getString("E_MAIL");

        EtEmail.setHint(_Email);
        EtEmail.setEnabled(false);
        EtFullname.setHint(_FullName);
        EtFullname.setEnabled(false);



    }

}
