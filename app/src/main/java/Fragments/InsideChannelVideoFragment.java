package Fragments;


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

import com.example.saboorsalaam.veed10.R;
import com.example.saboorsalaam.veed10.VimeoPlayerActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ParseClasses.Video;
import UtilityClasses.MyCalculator;
import com.example.saboorsalaam.veed10.YouTubePlayerActivity;


/**
 * Created by Saboor Salaam on 6/10/2014.
 */
public class InsideChannelVideoFragment extends Fragment {

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    FragmentManager fm;
    ProgressBar pb;
    ImageView cover;


    static private final String DEVELOPER_KEY = "AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";

    public InsideChannelVideoFragment newInstance(Video v) {

        InsideChannelVideoFragment vf = new InsideChannelVideoFragment();
        String uri = v.getVideoThumbnail();


        String id = v.getVideoId();
        String info = v.getVideoDescription();
        String title = v.getVideoTitle();
        String date = v.getDatePublished();
        String channel = v.getProvider_name();
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
            TextView channel_textview = (TextView) rootView.findViewById(R.id.provider);
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

            String timePassedString = MyCalculator.getTimeAgo(dateinfo);

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
                        Intent intent = new Intent(getActivity(), VimeoPlayerActivity.class);
                        intent.putExtra("video_id", (String) getArguments().get("id"));
                        startActivity(intent);

                    } else if (type.equals("yt")) {
                        Intent intent = new Intent(getActivity(), YouTubePlayerActivity.class);

                        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, (String) getArguments().get("id"));

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
