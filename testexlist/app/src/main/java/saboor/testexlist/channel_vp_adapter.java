package saboor.testexlist;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class channel_vp_adapter extends FragmentStatePagerAdapter {

    List<Video> videos;

        public channel_vp_adapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<Video> v)
        {
            //notifyDataSetChanged();
            videos = v;
        }

        @Override
        public Fragment getItem(int position) {
            //notifyDataSetChanged();
            return new video_fragment().newInstance( videos.get(position));
        }

        @Override
        public int getCount() {
            return videos.size();
        }
}

