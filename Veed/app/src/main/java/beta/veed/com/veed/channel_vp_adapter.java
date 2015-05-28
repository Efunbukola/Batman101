package beta.veed.com.veed;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class channel_vp_adapter extends FragmentStatePagerAdapter {

    List<channel> mChannels;
    List<Integer> mColors;

    public channel_vp_adapter(FragmentManager fm) {super(fm);}

        public void setList(List<channel> mChannels)
        {

            this.mChannels = mChannels;
            notifyDataSetChanged();

            mColors = new ArrayList<Integer>();
            Random random = new Random();
            for(int i = 0; i < mChannels.size();i++) {
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);
                mColors.add(Color.rgb(r, g, b));
            }
        }

        @Override
        public Fragment getItem(int position) {
            //notifyDataSetChanged();
            return new channel_fragment().newInstance( mChannels.get(position), mColors.get(position));
        }

        @Override
        public int getCount() {
            return mChannels.size();
        }

        public String getTitle(int position)
        {
            int size = getItem(position).getArguments().getInt("size");
            if(size != 0) {
                return getItem(position).getArguments().getString("name") + " " + size;
            }else
            {
                return getItem(position).getArguments().getString("name");
            }

        }
}

