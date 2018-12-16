package com.example.rmaahmadov.instagramclone.utils;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionsStatePageAdapter extends FragmentStatePagerAdapter{
    
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final HashMap<Fragment,Integer> mFragments = new HashMap<>();
    private final HashMap<Integer,String> mFragmentsName=new HashMap<>();
    private final HashMap<String,Integer> mFragmentsNumber=new HashMap<>();
    
    public SectionsStatePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    
    public void addFagment(Fragment fragment,String fragmenName){
        mFragmentList.add(fragment);
        mFragments.put(fragment,mFragmentList.size()-1);
        mFragmentsNumber.put(fragmenName,mFragmentList.size()-1);
        mFragmentsName.put(mFragmentList.size()-1,fragmenName);
    }
    
    public Integer getFragentNumber(String fragmenName){
        if(mFragmentsNumber.containsKey(fragmenName)){
            return mFragmentsNumber.get(fragmenName);
        }else{
            return null;
        }
    }

    public Integer getFragentName(Fragment fragmen){
        if(mFragmentsNumber.containsKey(fragmen)){
            return mFragmentsNumber.get(fragmen);
        }else{
            return null;
        }
    }

    public String getFragentNumber(Integer fragmentNumber){
        if(mFragmentsName.containsKey(fragmentNumber)){
            return mFragmentsName.get(fragmentNumber);
        }else{
            return null;
        }
    }
    
}
