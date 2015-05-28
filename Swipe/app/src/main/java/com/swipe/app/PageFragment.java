package com.swipe.app;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


public class PageFragment extends YouTubePlayerSupportFragment {
    public static String Index = "Index";

    public static PageFragment createFragment (int p)
    {
        Bundle passedData = new Bundle();
        passedData.putSerializable(Index, p + "");
        PageFragment a = new PageFragment();
        a.setArguments(passedData);
        return a;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_page_fragment, container, false);
        TextView text = (TextView)rootView.findViewById(R.id.tV);
        //YouTubePlayer ytube = (YouTubePlayer)rootView.findViewById(R.id.youtube_view);

        String words = (String) getArguments().getSerializable(Index);
        text.setText(words);


        return rootView;
    }
}
