package com.example.hotelroombooking;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterAbout extends FragmentStatePagerAdapter {
    int tabs;

    public PagerAdapterAbout(FragmentManager fm, int tabs){
        super(fm);
        this.tabs=tabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return new TabFragmentAbout();
            case 1: return new TabFragmentContact();
            default: return null;
        }
    }

    @Override
    public int getCount() {return tabs;}
}