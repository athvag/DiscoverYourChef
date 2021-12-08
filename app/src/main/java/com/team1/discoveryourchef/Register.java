package com.team1.discoveryourchef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {



    EditText registerEmail,registerFullName,registerPassword;
    Button registerButton;
    TextView gotologin;
    AlertDialog dialog;

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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog, null));
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();
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
                        else {

                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorCode) {

                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                    registerEmail.setError("The email address is badly formatted.");
                                    registerEmail.requestFocus();
                                    break;


                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    registerEmail.setError("The email address is already in use by another account.");
                                    registerEmail.requestFocus();
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                    break;


                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    dialog.dismiss();
                                    Toast.makeText(Register.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                    registerPassword.setError("The password is invalid it must 6 characters at least");
                                    registerPassword.requestFocus();
                                    break;

                            }
                        }
                }
    });

}
}