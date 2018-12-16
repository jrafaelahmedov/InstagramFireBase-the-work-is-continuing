package com.example.rmaahmadov.instagramclone.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private Context mcontex=SearchActivity.this;
    private static final int ACTIVIT_NUMBER=1;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate: started.");
        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        Log.d(TAG,"sETTING UP BOTTOM NAGIVATION VIEW ");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mcontex,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVIT_NUMBER);
        menuItem.setChecked(true);
    }
}
