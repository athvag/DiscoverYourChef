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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView gotoregister;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        //
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent login = new Intent(MainActivity.this,Home.class);
            DocumentReference user = db.collection("Users").document(currentUser.getUid());
            user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    login.putExtra("fullName", document.getString("fullName"));
                    startActivity(login);
                }
            });
        }
        else{

        }
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

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
                            DocumentSnapshot document = task.getResult();
                            login.putExtra("fullName", document.getString("fullName"));
                            startActivity(login);
                        }
                    });




                }
                else
                {
                    Toast.makeText(MainActivity.this, "Failed to login, check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}