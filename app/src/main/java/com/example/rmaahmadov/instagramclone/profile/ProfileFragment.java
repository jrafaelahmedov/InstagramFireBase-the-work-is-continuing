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
import android.widget.Toast;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.models.User;
import com.example.rmaahmadov.instagramclone.utils.BottomNavigationViewHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private static final int ACTIVIT_NUMBER = 4;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private ProgressBar mProgressbar;
    private CircleImageView mProfileImage;
    private GridView mGridView;
    private Toolbar mToolbar;
    private ImageView mProfileMenu;
    private BottomNavigationViewEx mBottomNavigationView;
    private Context mContex;
    String userID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        myRef = FirebaseDatabase.getInstance().getReference();
        mDisplayName = view.findViewById(R.id.display_name);
        mUsername = view.findViewById(R.id.username);
        mPosts = view.findViewById(R.id.tvPosts);
        mFollowing = view.findViewById(R.id.tvFollowing);
        mFollowers = view.findViewById(R.id.tvFollowers);
        mWebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mEmail = view.findViewById(R.id.email);
        mProgressbar = view.findViewById(R.id.profileProgressBar);
        mPhoneNumber = view.findViewById(R.id.phone_number);
        mProfileImage = view.findViewById(R.id.profile_photo);
        mGridView = view.findViewById(R.id.gridView);
        mToolbar = view.findViewById(R.id.profileToolBar);
        mProfileMenu = view.findViewById(R.id.profileMenu);
        mBottomNavigationView = view.findViewById(R.id.bottomNavViewBar);
        mContex = getActivity();
        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        Log.d(TAG, "onCreateView: Started");
        setupBottomNavigationView();
        setupToolBar();
        setupFirabaseAuth();
        userSetting();
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

        ((ProfileActivity) getActivity()).setSupportActionBar(mToolbar);
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


    public void userSetting() {
        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference("users/" + userID);
        Map messagesMap = new HashMap();
        messagesMap.get(User.class);
        
        
        Map messageBody = new HashMap();
        messageBody.put(mdatabase, messagesMap);

        mdatabase.updateChildren(messageBody);
        mdatabase.updateChildren(messageBody).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Register user succesfully inserted............................");
                    Toast.makeText(mContex, "Register user succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {

                    Log.d(TAG, "Register user failed............................");
                    Toast.makeText(mContex, "Register user failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e == null) {
                    Log.d(TAG, "Register user succesfully inserted............................");
                    Toast.makeText(mContex, "Register user succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Register user failed............................");
                    Toast.makeText(mContex, "Register user failed", Toast.LENGTH_SHORT).show();
                    System.out.println("Register user failed.............." + e.getLocalizedMessage().toString());
                }
            }
        });
        


//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                User value = dataSnapshot.getValue(User.class);
//                Log.d(TAG, "Value is: " + value);
//                mUsername.setText(value.getUsername().toString());
//                System.out.println("My username..............................................."+value.getUsername().toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("users/"+userID);
//
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User post = dataSnapshot.getValue(User.class);
//                    dataSnapshot.hasChild("username");
//                     String username=dataSnapshot.getValue().toString();
//                    post.setUsername(username);
//                    mUsername.setText(post.getUsername());
//                    
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        ref.addValueEventListener(postListener);

//        Query q = ref.child(userID);
//
//
//        q.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String username = dataSnapshot.child("username").getValue().toString();
//                String email = dataSnapshot.child("email").getValue().toString();
//                String phone_number = dataSnapshot.child("phone_number").getValue().toString();
//                mUsername.setText(username);
//                mEmail.setText(email);
//                mPhoneNumber.setText(phone_number);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//         ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            
//                            String username =dataSnapshot.child("username").getValue().toString();
//                            String email =dataSnapshot.child("email").getValue().toString();
//                            String phone_number =dataSnapshot.child("phone_number").getValue().toString();
//                            mUsername.setText(username);
//                            mEmail.setText(email);
//                            mPhoneNumber.setText(phone_number);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//        myRef.child("user_account_settings").child(userID)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            String username =dataSnapshot.child("username").getValue().toString();
//                            String display_name =dataSnapshot.child("display_name").getValue().toString();
//                            String website =dataSnapshot.child("website").getValue().toString();
//                            Bitmap profile_photo =(Bitmap)dataSnapshot.child("profile_photo").getValue();
//                            String followers =dataSnapshot.child("followers").getValue().toString();
//                            String following =dataSnapshot.child("following").getValue().toString();
//                            String posts =dataSnapshot.child("posts").getValue().toString();
//                            String description =dataSnapshot.child("description").getValue().toString();
//                            mUsername.setText(username);
//                            mDisplayName.setText(display_name);
//                            mWebsite.setText(website);
//                            mProfileImage.setImageBitmap(profile_photo);
//                            mFollowers.setText(followers);
//                            mFollowing.setText(following);
//                            mPosts.setText(posts);
//                            mDescription.setText(description);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

    }


    @Override
    public void onStart() {
        super.onStart();
        if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
