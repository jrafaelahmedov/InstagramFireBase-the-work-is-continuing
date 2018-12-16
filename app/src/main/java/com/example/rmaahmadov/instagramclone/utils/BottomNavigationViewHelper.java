package com.example.rmaahmadov.instagramclone.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.rmaahmadov.instagramclone.likes.LikesActivity;
import com.example.rmaahmadov.instagramclone.home.HomeActivity;
import com.example.rmaahmadov.instagramclone.profile.ProfileActivity;
import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.search.SearchActivity;
import com.example.rmaahmadov.instagramclone.share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "Setting up BottomNavigationView...");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context contex, BottomNavigationViewEx viewEx) {
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_house:
                        Intent intent1 = new Intent(contex, HomeActivity.class);//ACTIVITY NUMBER 0
                        contex.startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(contex, SearchActivity.class);//ACTIVITY NUMBER 1
                        contex.startActivity(intent2);
                        break;
                    case R.id.ic_alert:
                        Intent intent3 = new Intent(contex, LikesActivity.class);//ACTIVITY NUMBER 2
                        contex.startActivity(intent3);
                        break;
                    case R.id.ic_android:
                        Intent intent4 = new Intent(contex, ProfileActivity.class);//ACTIVITY NUMBER 3
                        contex.startActivity(intent4);
                        break;
                    case R.id.ic_circle:
                        Intent intent5 = new Intent(contex, ShareActivity.class);//ACTIVITY NUMBER 4
                        contex.startActivity(intent5);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
