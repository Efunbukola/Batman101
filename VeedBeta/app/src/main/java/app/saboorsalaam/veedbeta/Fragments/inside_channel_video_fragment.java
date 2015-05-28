package app.saboorsalaam.veedbeta.Fragments;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.saboorsalaam.veedbeta.ParseClasses.Video;
import app.saboorsalaam.veedbeta.R;
import app.saboorsalaam.veedbeta.vimeo_player_activity;
import app.saboorsalaam.veedbeta.youtube_player_activity;


/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class inside_channel_video_fragment extends Fragment {

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    FragmentManager fm;
    ProgressBar pb;
    ImageView cover;


    static private final String DEVELOPER_KEY = "AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";

    public inside_channel_video_fragment newInstance(Video v) {

        inside_channel_video_fragment vf = new inside_channel_video_fragment();
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


    public String getTimeAgo(String dateinfo) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

        Date datez = null;
        dateinfo = dateinfo.substring(0, 10) + "" + dateinfo.substring(11, 19);
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


    View rootView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.video_player_fragment_layout, container, false);


            //Set video information
            TextView title_textview = (TextView) rootView.findViewById(R.id.title);
            TextView date_textview = (TextView) rootView.findViewById(R.id.date);
            TextView channel_textview = (TextView) rootView.findViewById(R.id.ch);
            TextView video_description_textview = (TextView) rootView.findViewById(R.id.video_desc);

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

            if (dateinfo.length() > 9) {
                day = dateinfo.substring(8, 10);
                month = dateinfo.substring(5, 7);
            }

            title_textview.setText(titleinfo);

            String timePassedString = getTimeAgo(dateinfo);

            date_textview.setText(timePassedString);
            channel_textview.setText(channel);
            video_description_textview.setText(Html.fromHtml(description));

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

                    if (type.equals("v")) {
                        Intent intent = new Intent(getActivity(), vimeo_player_activity.class);
                        intent.putExtra("video_id", (String) getArguments().get("id"));
                        startActivity(intent);

                    } else if (type.equals("yt")) {
                        Intent intent = new Intent(getActivity(), youtube_player_activity.class);

                        intent.putExtra(youtube_player_activity.EXTRA_VIDEO_ID, (String) getArguments().get("id"));

//This Flag might cause the video to turned off automatically on phonecall
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                        startActivity(intent);

                         //Intent intentz = YouTubeStandalonePlayer.createVideoIntent(
                         //       getActivity(), DEVELOPER_KEY, (String) getArguments().get("id"), 0, true, false);
                         //startActivityForResult(intentz, 1);
                    }
                }
            });
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove _rootView from the existing parent view group
            // (it will be added back).
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        return rootView;
    }
}
