package com.videoswipper.app;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.ocpsoft.pretty.time.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class video_fragment extends Fragment implements CustomYouTubePlayer.OnInitializedListener {

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;




    FragmentManager fm;


    static private final String DEVELOPER_KEY = "AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";
    //String currentVideoID = "jT77uP2ct1w";
    private YouTubePlayer activePlayer;

    public video_fragment newInstance(YouTubeVideo v) {

        video_fragment vf = new video_fragment();
        String uri = v.getVideoThumbnail();


        String id = v.getVideoId();
        String info = v.getVideoDescription();
        String title = v.getVideoTitle();
        String date = v.getDatePublished();
        String channel = v.getChannelTitle();
        //currentVideoID = id;

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("info", info);
        bundle.putString("thumb", uri);
        bundle.putString("date", date);
        bundle.putString("channel", channel);
        bundle.putString("title", title);

        vf.setArguments(bundle);


        return vf;
    }

    YouTubePlayerSupportFragment llf;



    public String getTimeAgo(String dateinfo)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

        Date datez = null;
        dateinfo = dateinfo.substring(0,10) +""+ dateinfo.substring(11,19);
        //2014-06-02T21:58:18.000Z



        //parse date string to get Data object
        try {
            datez = df.parse(dateinfo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Use pretty time to format date
        PrettyTime ptime = new PrettyTime();
        String timePassedString = (ptime.format(datez));
        return timePassedString;
    }


@Override
    public void onCreate (Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    {

        llf = new YouTubePlayerSupportFragment();
        //llf.initialize(DEVELOPER_KEY, this);



    }
}

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.video_player_fragment, container, false);



    //replace frame with YouTubeFragment

/*
    fm = getChildFragmentManager();
    f = new test_image_fragment();
    FragmentTransaction ft = fm.beginTransaction().replace(R.id.yt_frag, llf);
    ft.commit();
 */



    //Set video information
    TextView title = (TextView) rootView.findViewById(R.id.title);
    //TextView info = (TextView) rootView.findViewById(R.id.info);
    TextView date = (TextView) rootView.findViewById(R.id.date);
    TextView channelttext = (TextView) rootView.findViewById(R.id.ch);

     String titleinfo = "";
     String description = " ";
     String dateinfo = " ";
     String channel = " ";
     String month, day;
    String url = "";

     description = (String) getArguments().getString("info");
     dateinfo = (String) getArguments().getString("date");
     channel = (String) getArguments().getString("channel");
     titleinfo = (String) getArguments().getString("title");
    url = (String) getArguments().getString("thumb");

     day = dateinfo.substring(8,10);
     month = dateinfo.substring(5,7);

     title.setText(titleinfo);

    String timePassedString = getTimeAgo(dateinfo);

    date.setText(timePassedString + " from: ");
    channelttext.setText(channel);


    ImageView yt = (ImageView) rootView.findViewById(R.id.yt_frag);

    int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
    int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();

    new DownloadImageTask(yt, screenWidth, screenHeight).execute(url);

    //yt.setScaleType(ImageView.ScaleType.FIT_XY);



    yt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                    getActivity(), DEVELOPER_KEY, (String) getArguments().get("id"), 0, true, true);
                startActivityForResult(intent, 1);



        }
    });


       return rootView;
    }






    @Override
    public void onStart() {

            //llf.initialize(DEVELOPER_KEY, this);
        super.onStart();
    }

    public void onInitializationSuccess(CustomYouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {



        youTubePlayer.cueVideo((String) getArguments().get("id"));

        youTubePlayer.setFullscreenControlFlags(CustomYouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        youTubePlayer.addFullscreenControlFlag(CustomYouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
    }

    @Override
    public void onInitializationFailure(CustomYouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getActivity(), "Oh no! ",
                Toast.LENGTH_LONG).show();
    }
}
/*
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mContent = getFragmentManager().getFragment(
                    savedInstanceState, "mContent");
          }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getFragmentManager().putFragment(outState, "mContent", mContent);

//Save the fragment's state here


    }

    Fragment mContent = null;
}

/*
    @Override
    public void onYouTubeVideoPaused() {
        activePlayer.pause();
    }
*/