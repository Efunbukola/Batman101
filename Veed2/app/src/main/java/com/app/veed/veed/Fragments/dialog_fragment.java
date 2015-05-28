package com.app.veed.veed.Fragments;

import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ShareActionProvider;

import com.app.veed.veed.Adapters.ChannelViewPagerAdapter;
import com.app.veed.veed.DatabaseHandler.DatabaseHandler;
import com.app.veed.veed.ParseClasses.ParseDBCommunicator;
import com.app.veed.veed.ParseClasses.Video;
import com.app.veed.veed.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Saboor Salaam on 12/19/2014.
 */
public class dialog_fragment extends DialogFragment {

        ParseDBCommunicator parseDBCommunicator;
        ViewPager viewPager;
        DatabaseHandler databaseHandler;
        List<Video> videos;
        ChannelViewPagerAdapter channelViewPagerAdapter;
        String id, name;
        MenuItem fav_item;
        private ShareActionProvider mShareActionProvider;

        public void instantiate(String name, String id) {

            this.name = name;
            this.id = id;

        }

    public dialog_fragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_inside_channel_activity, container);

        parseDBCommunicator = new ParseDBCommunicator(getActivity().getApplicationContext());
        databaseHandler = new DatabaseHandler(getActivity());

        videos = parseDBCommunicator.getVideosofAChannel(id, name, new ParseDBCommunicator.connectionFailedListener() {
            @Override
            public void onConnectionFailed() {

            }

            @Override
            public void onConnectionSuccessful() {

            }

            @Override
            public void onCannotConnectToParse() {

            }
        });
        Log.d("Size", "Retrieved from id: " + id + ": " + videos.size() + " videos");

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        channelViewPagerAdapter = new ChannelViewPagerAdapter(getChildFragmentManager());
        channelViewPagerAdapter.setList(videos);
        Log.d("Size", "Retrieved: " + videos.size() + " videos");
        viewPager.setAdapter(channelViewPagerAdapter);

        return view;
    }
}
