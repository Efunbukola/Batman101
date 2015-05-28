package com.swipe.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



/**
 * Created by Saboor Salaam on 5/31/2014.
 */
public class TabAdapter extends FragmentStatePagerAdapter {


    private static int NUM_PAGES = 20;

    protected void setAmount(int size)
    {
        NUM_PAGES = size;
    }

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new PageFragment().createFragment(position);
    }
    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
