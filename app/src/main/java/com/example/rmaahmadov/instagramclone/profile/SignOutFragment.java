package com.example.rmaahmadov.instagramclone.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOutFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressBar mProgressBar;
    private TextView mTextView, textViewSignOut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signout, container, false);
        mProgressBar = view.findViewById(R.id.progressBarSignOut);
        mTextView = view.findViewById(R.id.ConfirmSignout);
        Button btnConfirmSignOut = view.findViewById(R.id.btnSignOut);
        textViewSignOut = view.findViewById(R.id.textViewConfirmSignOut);
        mProgressBar.setVisibility(View.GONE);
        textViewSignOut.setVisibility(View.GONE);
        setupFirabaseAuth();
        btnConfirmSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: signing out.................");
                mProgressBar.setVisibility(View.VISIBLE);
                textViewSignOut.setVisibility(View.VISIBLE);
                mAuth.signOut();
                getActivity().finish();
            }
        });
        return view;
    }

    /*
     * ----------------------------------------- FireBase Methods-------------------------------------------------------
     * */

    private void setupFirabaseAuth() {
        mAuth=FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 != null) {
                    Log.d(TAG, "updateUI: User in signed in" + user1.getUid());
                } else {
                    Log.d(TAG, "updateUI: signed out ");
                    Log.d(TAG, "onAuthStateChanged: navigation back to login screen");
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
