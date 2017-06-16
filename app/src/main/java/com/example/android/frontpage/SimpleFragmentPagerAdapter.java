package com.example.android.frontpage;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Gavin on 11-Mar-17.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] story = {"World", "Politics", "Technology", "Science", "Sports", "Food", "Travel", "Movies", "Fashion", "Opinion"};


    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        if (position == 0) {
            return new WorldFragment();
        } else if (position == 1) {
            return new PoliticsFragment();
        } else if (position == 2) {
            return new TechnologyFragment();
        } else if (position == 3) {
            return new ScienceFragment();
        } else if (position == 4) {
            return new SportsFragment();
        } else if (position == 5) {
            return new FoodFragment();
        } else if (position == 6) {
            return new TravelFragment();
        } else if (position == 7) {
            return new MoviesFragment();
        } else if (position == 8) {
            return new FashionFragment();
        } else {
            return new OpinionFragment();
        }
    }


    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return story[0];
        } else if (position == 1) {
            return story[1];
        } else if (position == 2) {
            return story[2];
        } else if (position == 3) {
            return story[3];
        } else if (position == 4) {
            return story[4];
        } else if (position == 5) {
            return story[5];
        } else if (position == 6) {
            return story[6];
        } else if (position == 7) {
            return story[7];
        } else if (position == 8) {
            return story[8];
        } else {
            return story[9];
        }
    }
}

