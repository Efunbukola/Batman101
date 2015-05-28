package com.test_vertical_view_pager.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Saboor Salaam on 6/12/2014.
 */
public class page_adapter extends FragmentStatePagerAdapter {


    int NUM_PAGES = 5;


    public page_adapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return new simple_frag();
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}

