package com.example.rmaahmadov.instagramclone.home;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.login.LoginActivity;
import com.example.rmaahmadov.instagramclone.utils.BottomNavigationViewHelper;
import com.example.rmaahmadov.instagramclone.utils.SectionsPagerAdapter;
import com.example.rmaahmadov.instagramclone.utils.UniversalImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnmyLogin;
    private TextView myUsername;
    private TextView myPassword;
    DatabaseReference rootRef;
    private Context mcontex = HomeActivity.this;
    private static final int ACTIVIT_NUMBER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
//            ((ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE))
//                    .clearApplicationUserData();
//        } 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate starting...");
        initImageLoader();
        setupBottomNavigationView();
        setupViewPager();
        setupFirabaseAuth();
        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
//        testFirebaseDatabase();
//        mAuth.signOut();
    }


    public void testFirebaseDatabase() {
        DatabaseReference myuserkeyrefer = rootRef
                .child("Messages")
                .child(mAuth.getCurrentUser().getProviderId())
                .push();

        DatabaseReference myUserKeyReferId = rootRef
                .child(mAuth.getCurrentUser().getUid())
                .push();

        String messagePushId2 = myUserKeyReferId.getKey();


        String messageSenderRef = "Messages/" + mAuth.getCurrentUser().getUid();
        String messageSenderID = "Messages/" + mAuth.getCurrentUser().getUid();
        String messagePushid = myuserkeyrefer.getKey();


        Map messagesMap = new HashMap();
        messagesMap.put("message", "Salam necesen?");
        messagesMap.put("type", "text");
        messagesMap.put("from", mAuth.getCurrentUser().getPhoneNumber());


        Map messageBody = new HashMap();
        messageBody.put(messageSenderRef + "/" + messagePushid, messagesMap);

        rootRef.updateChildren(messageBody).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("My error............................................" + e.getLocalizedMessage().toString());
                    }
                });
                if (task.isSuccessful()) {
                    Toast.makeText(mcontex, "Data is succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(mcontex, "Data insert failed!!!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mcontex, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();

                        System.out.println("My error............................................" + e.getLocalizedMessage().toString());
                    }
                }
        );

    }


    /*
     *     Bottom navigation setup
     * */
    private void setupBottomNavigationView() {
        Log.d(TAG, "sETTING UP BOTTOM NAGIVATION VIEW ");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mcontex, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVIT_NUMBER);
        menuItem.setChecked(true);
    }


    /*
     * Responsible for adding the 3 tabs: Camera , Home ,Messages
     * */


    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mcontex);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    private void setupViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.addFragment(new CameraFragment());//0
        sectionsPagerAdapter.addFragment(new HomeFragment());//1
        sectionsPagerAdapter.addFragment(new MessagesFragment());//2
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.layout_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_instagram);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_messages);
    }


    /*
     *
     * -----------------------------------FireBase-----------------------------------------*/

    private void setupFirabaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                checkCurrentUser(user1);
                if (user1 != null) {
                    Log.d(TAG, "updateUI: User in signed in" + user1.getUid());
                } else {
                    Log.d(TAG, "updateUI: signed out ");
                }
            }
        };

    }

    private void checkCurrentUser(FirebaseUser user) {
        Log.d(TAG, "checkCurrentUser: cheking user is logged in  .....");
        if (user == null) {
            Intent intent = new Intent(mcontex, LoginActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
