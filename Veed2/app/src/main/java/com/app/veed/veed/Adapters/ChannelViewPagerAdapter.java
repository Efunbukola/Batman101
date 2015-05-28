package com.app.veed.veed.Adapters;







import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;


import com.app.veed.veed.Fragments.inside_channel_video_fragment;
import com.app.veed.veed.ParseClasses.Video;
import com.ocpsoft.pretty.time.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class ChannelViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Video> videos;
    SparseArray<inside_channel_video_fragment> registeredFragments = new SparseArray<inside_channel_video_fragment>();





    public ChannelViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<Video> v)
        {
            //notifyDataSetChanged();
            videos = v;
            Collections.sort(videos, new CustomComparator());

        }

        @Override
        public Fragment getItem(int position) {
            //notifyDataSetChanged();
            return new inside_channel_video_fragment().newInstance( videos.get(position));
        }

        @Override
        public int getCount() {
            return videos.size();
        }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inside_channel_video_fragment fragment = (inside_channel_video_fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public inside_channel_video_fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }



    public class CustomComparator implements Comparator<Video> {
        @Override
        public int compare(Video v1, Video v2) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
            Date v1_date = null;
            Date v2_date = null;
            String v1_raw_date_string = v1.getDatePublished().substring(0,10) +""+ v1.getDatePublished().substring(11,19);
            String v2_raw_date_string= v2.getDatePublished().substring(0,10) +""+ v2.getDatePublished().substring(11,19);
            //2014-06-02T21:58:18.000Z

            //parse date string to get Data object
            try {
                v1_date = simpleDateFormat.parse(v1_raw_date_string);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                v2_date = simpleDateFormat.parse(v2_raw_date_string);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return v2_date.compareTo(v1_date);
        }
    }
}

