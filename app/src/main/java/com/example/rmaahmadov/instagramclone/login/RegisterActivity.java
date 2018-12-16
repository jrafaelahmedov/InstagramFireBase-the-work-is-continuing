package com.example.rmaahmadov.instagramclone.login;

import android.content.Context;
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
import com.example.rmaahmadov.instagramclone.utils.FireBaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mContex;
    private ProgressBar mProgressBar;
    private String email, password, phonenumber, username;
    private EditText mEmail, mPassword, mPhoneNumber, mUsername;
    private TextView pleaceWait;
    private Button btnRegister;
    private FireBaseMethods fireBaseMethods;
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference myRef;
    private String append = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initWidgets();
        setupFirabaseAuth();
        init();
    }


    private void initWidgets() {
        Log.d(TAG, "initWidgets: initialazing init WINGETS");
        mProgressBar = findViewById(R.id.registerReguestLoadingProgressBar);
        pleaceWait = findViewById(R.id.pleaceWaitTextView);
        mEmail = findViewById(R.id.inputEmail);
        mPassword = findViewById(R.id.inputPassword);
        mUsername = findViewById(R.id.inputUserName);
        mPhoneNumber = findViewById(R.id.inputPhoneNumber);
        btnRegister = findViewById(R.id.btnRegister);
        pleaceWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mContex = RegisterActivity.this;
        fireBaseMethods = new FireBaseMethods(mContex);
    }

    private void init() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                username = mUsername.getText().toString();
                phonenumber = mPhoneNumber.getText().toString();


                if (checkInit(email, password, phonenumber, username)) {
                    pleaceWait.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    fireBaseMethods.registerNewAccount(email, password, phonenumber, username);
                }
            }
        });
    }


    private boolean checkInit(String email, String password, String phonenumber, String fullname) {
        Log.d(TAG, "checkInit: checking input........................");
        if (email.isEmpty() || password.isEmpty() || phonenumber.isEmpty() || fullname.isEmpty()) {
            Toast.makeText(mContex, "All fealds must be fill!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean checkStringNull(String string) {
        if (string.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     *
     * -----------------------------------FireBase-----------------------------------------*/

    public void setupFirabaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFireBaseDatabase.getReference();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //1st check username is alredy is used or not
                            if (fireBaseMethods.checkingIfUsernameExsists(username, dataSnapshot)) {
                                append = myRef.push().getKey().substring(3, 10);
                                Log.d(TAG, "onDataChange: username alredy exsist randot to suggest new usernames .............................................");
                            }
                            username = username + append;
                            //2 add new user to database
                            fireBaseMethods.addNewUser(email, username, "", "", "");
                            Log.d(TAG, "SignUp susccessfull seding verificdation email .....................................................");
                            Toast.makeText(mContex, "SignUp susccessfull seding verificdation email . ........", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            //add user_account_setting in database
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    finish();
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
