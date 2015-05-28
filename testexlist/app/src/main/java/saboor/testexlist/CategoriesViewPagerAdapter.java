package saboor.testexlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Saboor Salaam on 8/17/2014.
 */
public class CategoriesViewPagerAdapter extends FragmentStatePagerAdapter {

        List<category> categories;

        public CategoriesViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<category> categories)
        {

            this.categories = categories;
            //notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            //notifyDataSetChanged();
            return new category_fragment().newInstance( categories.get(position));
        }

        @Override
        public int getCount() {
            return categories.size();
        }

    @Override
    public CharSequence getPageTitle(int position) {
        return "#" + categories.get(position).getName();
    }
}
