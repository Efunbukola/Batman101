package com.videoswipper.app;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class page_adapter extends FragmentStatePagerAdapter {

    List<YouTubeVideo> videos;
    int NUM_PAGES;


        public page_adapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<YouTubeVideo> v)
        {
            //notifyDataSetChanged();
            videos = v;
            NUM_PAGES = v.size();
        }

        @Override
        public Fragment getItem(int position) {
            //notifyDataSetChanged();
            return new video_fragment().newInstance( videos.get(position));
        }

        @Override
        public int getCount() {


            if(NUM_PAGES == 0)
            {
                return 4;
            }else {
                return videos.size();
            }
        }
    }

