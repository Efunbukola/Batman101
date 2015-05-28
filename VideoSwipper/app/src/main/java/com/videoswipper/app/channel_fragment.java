package com.videoswipper.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 6/12/2014.
 */
public class channel_fragment extends Fragment{




    private final String DEVELOPER_KEY = "AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";
    String jsonString;

    public channel_fragment newInstance(String channel_name, String channel_id)
    {
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + channel_id + "&maxResults=50&order=rating&type=video&key=AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("name", channel_name);

        channel_fragment cf = new channel_fragment();
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.channel_fragment, container, false);

        final RestHandler restHandler = new RestHandler();

        //run httpGet on background thread
        Thread client = new Thread(new Runnable() {
            public void run() {
                jsonString = restHandler.httpGet((String) getArguments().getString("url"));

            }
        });

        client.start();

        //wait for background thread to finish
        try {
            client.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //compile list of videos from json
        //videos = new ArrayList<YouTubeVideo>();

        ViewPager mPager;
        page_adapter mPagerAdapter;

        mPager = (ViewPager) rootView.findViewById(R.id.pager);

        mPagerAdapter = new page_adapter(getChildFragmentManager());
        mPagerAdapter.setList(restHandler.makeYouTubeList(jsonString));
        mPager.setAdapter(mPagerAdapter);


        final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(800); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        //forward.startAnimation(animation);



        return rootView;
    }


}
