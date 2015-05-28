package app.saboorsalaam.veedbeta.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

;

import java.util.List;

import app.saboorsalaam.veedbeta.Fragments.category_fragment;
import app.saboorsalaam.veedbeta.ParseClasses.Category;

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
