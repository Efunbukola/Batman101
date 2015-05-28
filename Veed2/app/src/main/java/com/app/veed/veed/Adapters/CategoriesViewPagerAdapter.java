package com.app.veed.veed.Adapters;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.Display;

import com.app.veed.veed.DatabaseHandler.DatabaseHandler;
import com.app.veed.veed.Fragments.category_fragment;
import com.app.veed.veed.ParseClasses.Category;

import java.util.List;

/**
 * Created by Saboor Salaam on 8/17/2014.
 */
public class CategoriesViewPagerAdapter extends FragmentStatePagerAdapter {

        List<Category> categories;
        int itemHeight = 0;
        int itemWidth = 0;
        Context context;

        public CategoriesViewPagerAdapter(FragmentManager fm, int itemHeight, int itemWidth, Context context) {
            super(fm);
            this.itemHeight = itemHeight;
            this.itemWidth = itemWidth;
            this.context = context;
        }

        public void setList(List<Category> categories)
        {
            this.categories = categories;
            //notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            //notifyDataSetChanged();
            return new category_fragment().newInstance( categories.get(position), itemHeight,itemWidth, context);


        }



        @Override
        public int getCount() {
            return categories.size();
        }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }
}
