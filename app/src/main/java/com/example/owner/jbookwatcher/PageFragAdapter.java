package com.example.owner.jbookwatcher;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageFragAdapter extends FragmentPagerAdapter {

    public PageFragAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                return new GoalFragment();

            case 1:
                return new SearchFragment();

            default:
                return new ListsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Goal";

            case 1:
                return "Search";

            default:
                return "Book Lists";
        }
    }
}
