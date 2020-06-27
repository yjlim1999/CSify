package com.example.networkingapp.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.networkingapp.fragment.ProfileFragment;

public class ProfileViewPagerAdapter extends FragmentPagerAdapter {

    //Tutorial 8
    String uid = "0";
    String current_state = "0";
    //Tutorial 8

    int size = 0;

    public ProfileViewPagerAdapter(FragmentManager fm, int size, String uid, String current_state) {
        super(fm);
        this.size = size;
        //Tutorial 8
        this.uid =uid;
        this.current_state =current_state;
        //Tutorial 8
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            //Tutorial 8
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putString("uid",uid);
                bundle.putString("current_state",current_state);
                profileFragment.setArguments(bundle);

                return profileFragment;

            //Tutorial 8
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return size;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Posts";
            default:
                return null;
        }
    }




}
