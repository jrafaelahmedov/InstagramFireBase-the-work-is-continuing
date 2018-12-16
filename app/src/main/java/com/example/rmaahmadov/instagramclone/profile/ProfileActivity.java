package com.example.rmaahmadov.instagramclone.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.utils.BottomNavigationViewHelper;
import com.example.rmaahmadov.instagramclone.utils.GridImageAdapter;
import com.example.rmaahmadov.instagramclone.utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private Context mcontex = ProfileActivity.this;
    private static final int ACTIVIT_NUMBER = 4;
    private ProgressBar mprogressbar;
    private ImageView profileimage;
    private static final int NUM_GRID_COLUMS =3;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");
//        setupBottomNavigationView();
//        setupToolBar();
//        setupActivitysWidgets();
//        setProfileImage();
//        tempGridSetup();
        init();
    }

    private void init(){
        Log.d(TAG, "init: inflating "+getString(R.string.profile_fragment));
        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }


//    private void setupToolBar() {
//        Toolbar toolbar = findViewById(R.id.profileToolBar);
//        setSupportActionBar(toolbar);
//        ImageView profileMenu = findViewById(R.id.profileMenu);
//        profileMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: Nagigatin to account settings");
//                Intent intent = new Intent(mcontex, AccountSettingActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void setupBottomNavigationView() {
//        Log.d(TAG, "sETTING UP BOTTOM NAGIVATION VIEW ");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mcontex, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVIT_NUMBER);
//        menuItem.setChecked(true);
//    }
//
//    private void setProfileImage() {
//        String imageUrl = "drawable://" + R.drawable.profile;
//        UniversalImageLoader.setImage(imageUrl, profileimage, mprogressbar, "");
//    }
//
//    private void setupActivitysWidgets() {
//        profileimage = findViewById(R.id.profileImage);
//        mprogressbar = findViewById(R.id.profileProgressBar);
//        mprogressbar.setVisibility(View.GONE);
//
//    }
//
//    private void tempGridSetup(){
//        ArrayList<String> imgUrls=new ArrayList<>();
//        imgUrls.add("drawable://" + R.drawable.image1);
//        imgUrls.add("drawable://" + R.drawable.image2);
//        imgUrls.add("drawable://" + R.drawable.image3);
//        imgUrls.add("drawable://" + R.drawable.image4);
//        imgUrls.add("drawable://" + R.drawable.image5);
//        imgUrls.add("drawable://" + R.drawable.image7);
//        setImageGrid(imgUrls);
//        System.out.println("MY IMAGESSSSSSSSSSSSSSSSSSSSSSS"+imgUrls.toString());
//    }
//
//    private void setImageGrid(ArrayList<String> imgUrls){
//        GridView gridView=findViewById(R.id.gridView);
//        int gridWith=getResources().getDisplayMetrics().widthPixels;
//        int imageWith=gridWith/NUM_GRID_COLUMS;
//        gridView.setColumnWidth(imageWith);
//        GridImageAdapter gridImageAdapter = new GridImageAdapter(mcontex,R.layout.layout_grid_imageview,"",imgUrls);
//        gridView.setAdapter(gridImageAdapter);
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.profile_menu,menu);
////        return true;
////    }
}
