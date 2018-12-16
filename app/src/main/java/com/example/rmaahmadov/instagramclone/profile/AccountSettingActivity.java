package com.example.rmaahmadov.instagramclone.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.rmaahmadov.instagramclone.R;
import com.example.rmaahmadov.instagramclone.utils.BottomNavigationViewHelper;
import com.example.rmaahmadov.instagramclone.utils.SectionsStatePageAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;


public class AccountSettingActivity extends AppCompatActivity {
    private static final String TAG = "AccountSettingActivity";
    private Context mContex;
    private SectionsStatePageAdapter pageAdapter;
    private ViewPager viewPager;
    private RelativeLayout relativeLayout;
    private static final int ACTIVIT_NUMBER = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        mContex = AccountSettingActivity.this;
        Log.d(TAG, "onCreate: Started");
        setupSettingList();
        setupBottomNavigationView();
        viewPager = (ViewPager) findViewById(R.id.container);
        relativeLayout = findViewById(R.id.relLayout1);
        //setup the backarrow for navicating back to "ProfileActivity"
        ImageView backarrow = findViewById(R.id.backArrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Click backArrow to move ProfileActivity");
                finish();
            }
        });
        setupFragments();
    }

    private void setViewPager(int fragmentNuber) {
        relativeLayout.setVisibility(View.GONE);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(fragmentNuber);
    }


    private void setupFragments() {
        pageAdapter = new SectionsStatePageAdapter(getSupportFragmentManager());
        pageAdapter.addFagment(new EditProfileFragment(), getString(R.string.edit_profile));
        pageAdapter.addFagment(new SignOutFragment(), getString(R.string.sign_out));
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "sETTING UP BOTTOM NAGIVATION VIEW ");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContex, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVIT_NUMBER);
        menuItem.setChecked(true);
    }


    private void setupSettingList() {
        Log.d(TAG, "setupSettingList: Initializing 'Account Seyttings list");
        ListView listView = findViewById(R.id.listViewAccountSettings);
        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString(R.string.sign_out));

        ArrayAdapter adapter = new ArrayAdapter(mContex, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setViewPager(position);
            }
        });
    }
}
