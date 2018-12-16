package com.example.rmaahmadov.instagramclone.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.login.LoginActivity;
import com.example.rmaahmadov.instagramclone.utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private static final int ACTIVIT_NUMBER = 4;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private TextView mPosts,mFollowers,mFollowing,mDisplayName,mUsername,mWebsite,mDescription;
    private ProgressBar mProgressbar;
    private CircleImageView mProfileImage;
    private GridView mGridView;
    private Toolbar mToolbar;
    private ImageView mProfileMenu;
    private BottomNavigationViewEx mBottomNavigationView;
    private Context mContex;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        mDisplayName=view.findViewById(R.id.display_name);
        mUsername=view.findViewById(R.id.username);
        mPosts=view.findViewById(R.id.tvPosts);
        mFollowing=view.findViewById(R.id.tvFollowing);
        mFollowers=view.findViewById(R.id.tvFollowers);
        mWebsite=view.findViewById(R.id.website);
        mDescription=view.findViewById(R.id.description);
        mProgressbar=view.findViewById(R.id.profileProgressBar);
        mProfileImage=view.findViewById(R.id.profile_photo);
        mGridView=view.findViewById(R.id.gridView);
        mToolbar=view.findViewById(R.id.profileToolBar);
        mProfileMenu=view.findViewById(R.id.profileMenu);
        mBottomNavigationView=view.findViewById(R.id.bottomNavViewBar);
        mContex=getActivity();
        Log.d(TAG, "onCreateView: Started");
        setupBottomNavigationView();
        setupToolBar();
        return view;
    }


    private void setupBottomNavigationView() {
        Log.d(TAG, "sETTING UP BOTTOM NAGIVATION VIEW ");
        BottomNavigationViewHelper.setupBottomNavigationView(mBottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContex, mBottomNavigationView);
        Menu menu = mBottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVIT_NUMBER);
        menuItem.setChecked(true);
    }


        private void setupToolBar() {

            ((ProfileActivity)getActivity()).setSupportActionBar(mToolbar);
        mProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Nagigatin to account settings");
                Intent intent = new Intent(mContex, AccountSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     *
     * -----------------------------------FireBase-----------------------------------------*/

    private void setupFirabaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user1 != null) {
                    Log.d(TAG, "updateUI: User in signed in" + user1.getUid());
                } else {
                    Log.d(TAG, "updateUI: signed out ");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
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
