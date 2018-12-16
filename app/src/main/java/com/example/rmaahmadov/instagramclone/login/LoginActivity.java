package com.example.rmaahmadov.instagramclone.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mContex;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private TextView pleaceWait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressBar = findViewById(R.id.loginReguestLoadingProgressBar);
        pleaceWait = findViewById(R.id.pleaceWaitTextView);
        mEmail = findViewById(R.id.inputEmail);
        mPassword = findViewById(R.id.inputPassword);
        mContex = LoginActivity.this;
        pleaceWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        setupFirabaseAuth();
        init();
    }

    private boolean checkStringNull(String string) {
        if (string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    private void init() {
        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (checkStringNull(email) && checkStringNull(password)) {
                    Toast.makeText(mContex, "You must to fill all fealds", Toast.LENGTH_LONG).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    pleaceWait.setVisibility(View.VISIBLE);


                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        try {
                                            if (user.isEmailVerified()) {
                                                Log.d(TAG, "onComplete: successfull email verificate...");
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(mContex, "Email is not verified \n check your email inbox", Toast.LENGTH_LONG).show();
                                                mProgressBar.setVisibility(View.GONE);
                                                pleaceWait.setVisibility(View.GONE);
                                                mAuth.signOut();
                                            }

                                        } catch (Exception e) {
                                            Log.e(TAG, "onComplete: Exception to mail verification " + e.getMessage());
                                        }

                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success login  fafasfasfasfasfafa .          . . .  . .. . . . . .  . ..  .");
                                        mProgressBar.setVisibility(View.GONE);
                                        pleaceWait.setVisibility(View.GONE);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure  Authentication failed.     .. . .  . . . . .  . . . . . . . .  . . .", task.getException());
                                        Toast.makeText(LoginActivity.this, "Email adress invalid!Please enter valide email.     .. . .  . . . . .  . . . . . . . .  . . .",
                                                Toast.LENGTH_SHORT).show();
                                        mProgressBar.setVisibility(View.GONE);
                                        pleaceWait.setVisibility(View.GONE);
                                    }

                                    // ...
                                }
                            });

                    if (mAuth.getCurrentUser() != null) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }

        });
        TextView linkSignUp = (TextView) findViewById(R.id.signUpTextView);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigate to registration PAGE");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }



    /*
     *
     * -----------------------------------FireBase-----------------------------------------*/

    private void setupFirabaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "updateUI: User in signed in" + user.getUid());
                } else {
                    Log.d(TAG, "updateUI: signed out ");
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}