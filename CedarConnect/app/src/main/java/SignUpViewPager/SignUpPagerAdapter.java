package SignUpViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by Saboor Salaam on 3/1/2015.
 */
public class SignUpPagerAdapter extends FragmentStatePagerAdapter {

    final static int AMOUNT_OF_SLIDES = 3;


    public SignUpPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return new SignUpScreenPagerFragment().newInstance(position);
    }

    @Override
    public int getCount() {
        return AMOUNT_OF_SLIDES;
    }
}
