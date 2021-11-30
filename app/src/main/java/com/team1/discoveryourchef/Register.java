package com.team1.discoveryourchef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {



    EditText registerEmail,registerFullName,registerPassword;
    Button registerButton;
    TextView gotologin;

    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        registerEmail = findViewById(R.id.register_email);
        registerFullName = findViewById(R.id.register_name);
        registerPassword = findViewById(R.id.register_password);

        registerButton = findViewById(R.id.register_button);

        gotologin = findViewById(R.id.register_gotologin);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin = new Intent(Register.this,MainActivity.class);
                startActivity(gotologin);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = registerEmail.getText().toString().trim();
        String fullName = registerFullName.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();


        if(email.isEmpty()){
            registerEmail.setError("Email is Required");
            registerEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerEmail.setError("Please enter a valid email");
            registerEmail.requestFocus();
            return;
        }

        if(fullName.isEmpty()){
            registerFullName.setError("Full Name is Required");
            registerFullName.requestFocus();
            return;
        }

        if(password.isEmpty()){
            registerPassword.setError("Password is required");
            registerPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            registerPassword.setError("Min length 6 characters");
            registerPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userID = mAuth.getCurrentUser().getUid();
                            User user = new User(fullName,email);
                            db.collection("Users").document(userID).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this,MainActivity.class));
                                }
                            });


                    }
                }
    });

}
}