package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PagerAdapter(@Nullable FragmentManager fm, int behavior){
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0: new ContactFragment();

            case 1: new null_Fragment();
        }
        return new ContactFragment();
    }

    @Override
    public int getCount() {
        return tabcount;
    }

}
