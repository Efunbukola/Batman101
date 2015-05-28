package saboor.testexlist;


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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class video_fragment extends Fragment {

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    FragmentManager fm;
    ProgressBar pb;
    ImageView cover;


    static private final String DEVELOPER_KEY = "AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";

    public video_fragment newInstance(Video v) {

        video_fragment vf = new video_fragment();
        String uri = v.getVideoThumbnail();


        String id = v.getVideoId();
        String info = v.getVideoDescription();
        String title = v.getVideoTitle();
        String date = v.getDatePublished();
        String channel = v.getChannelTitle();
        String type = v.getType();
        //currentVideoID = id;

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("info", info);
        bundle.putString("thumb", uri);
        bundle.putString("date", date);
        bundle.putString("channel", channel);
        bundle.putString("title", title);
        bundle.putString("type", type);

        vf.setArguments(bundle);


        return vf;
    }





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
    final String type;
    String url = "";

     description = (String) getArguments().getString("info");
     dateinfo = (String) getArguments().getString("date");
     channel = (String) getArguments().getString("channel");
     titleinfo = (String) getArguments().getString("title");
     type = (String) getArguments().getString("type");
    url = (String) getArguments().getString("thumb");

if(dateinfo.length() > 9) {
    day = dateinfo.substring(8, 10);
    month = dateinfo.substring(5, 7);
}

     title.setText(titleinfo);

    String timePassedString = getTimeAgo(dateinfo);

    date.setText(timePassedString + " from: ");
    channelttext.setText(channel);

    pb = (ProgressBar) rootView.findViewById(R.id.pb);
    cover = (ImageView) rootView.findViewById(R.id.cover);
    Picasso.with(getActivity()).load(url).centerCrop().fit().into(cover, new Callback() {
        @Override
        public void onSuccess() {
            cover.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);

        }

        @Override
        public void onError() {

        }
    });
      cover.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(type.equals("v"))
            {
                Intent intent = new Intent(getActivity() , vimeo_player_activity.class);
                intent.putExtra("video_id",(String) getArguments().get("id"));
                startActivity(intent);

            }
            else if (type.equals("yt")) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                        getActivity(), DEVELOPER_KEY, (String) getArguments().get("id"), 0, true, false);
                startActivityForResult(intent, 1);
            }
        }
    });


       return rootView;
    }
}
