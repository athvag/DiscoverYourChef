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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.team1.discoveryourchef.Favorites.FavoritesPage;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView gotoregister;
    AlertDialog dialog;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog, null));
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            dialog.show();
            Intent login = new Intent(MainActivity.this, Home.class);
            DocumentReference user = db.collection("Users").document(currentUser.getUid());
            user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    dialog.dismiss();
                    DocumentSnapshot document = task.getResult();
                    login.putExtra("fullName", document.getString("fullName"));
                    login.putExtra("email", document.getString("email"));
                    login.putExtra("password", document.getString("password"));

                    startActivity(login);
                }
            });
        }
        else{

        }


        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        gotoregister = findViewById(R.id.login_gotoregister);

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregister = new Intent(MainActivity.this,Register.class);
                startActivity(gotoregister);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });



    }

    private void login() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();


        if(email.isEmpty()){
            loginEmail.setError("Email is Required");
            loginEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.setError("Please enter a valid email");
            loginEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            loginPassword.setError("Password is required");
            loginPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            loginPassword.setError("Min length 6 characters");
            loginPassword.requestFocus();
            return;
        }
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    Intent login = new Intent(MainActivity.this,Home.class);


                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    DocumentReference user = db.collection("Users").document(currentFirebaseUser.getUid());
                    user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            dialog.dismiss();
                            DocumentSnapshot document = task.getResult();
                            login.putExtra("fullName", document.getString("fullName"));
                            login.putExtra("email", document.getString("email"));
                            startActivity(login);
                        }
                    });




                }

                   else {

                    String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                    switch (errorCode) {


                        case "ERROR_INVALID_CREDENTIAL":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_INVALID_EMAIL":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                            loginEmail.setError("The email address is badly formatted.");
                            loginEmail.requestFocus();
                            break;

                        case "ERROR_WRONG_PASSWORD":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                            loginPassword.setError("Password is incorrect ");
                            loginPassword.requestFocus();
                            loginPassword.setText("");
                            break;

                        case "ERROR_USER_MISMATCH":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_USER_DISABLED":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_USER_TOKEN_EXPIRED":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_USER_NOT_FOUND":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_INVALID_USER_TOKEN":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                            break;

                        case "ERROR_OPERATION_NOT_ALLOWED":
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                            break;


                    }
                }
            }
        });
    }
}