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
public class video_vp_adapter extends FragmentStatePagerAdapter {

    List<YouTubeVideo> videos;
    List<Integer> mColors;

        public video_vp_adapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<YouTubeVideo> v)
        {
            //notifyDataSetChanged();
            videos = v;

            mColors = new ArrayList<Integer>();
            Random random = new Random();
            for(int i = 0; i < videos.size();i++) {
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);
                mColors.add(Color.rgb(r, g, b));
            }
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            //notifyDataSetChanged();
            return new video_fragment().newInstance( videos.get(position), mColors.get(position), position);
        }

        @Override
        public int getCount() {
            return videos.size();
        }
}

