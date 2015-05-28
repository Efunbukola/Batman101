package beta.veed.com.veed;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class video_fragment extends Fragment {

    private static final int PORTRAIT_ORIENTATION = Build.VERSION.SDK_INT < 9
            ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            : ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
    FragmentManager fm;
    ProgressBar pb;
    ImageView cover;
    Integer position;


    static private final String DEVELOPER_KEY = "AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";

    public video_fragment newInstance(YouTubeVideo v, Integer color, Integer position) {

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
        bundle.putInt("color", color);
        bundle.putInt("position", position);

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
                R.layout.layout_video_fragment, container, false);


        //Set video information
        TextView video_title = (TextView) rootView.findViewById(R.id.video_title);
        TextView time_ago = (TextView) rootView.findViewById(R.id.time_ago);
        RelativeLayout back = (RelativeLayout) rootView.findViewById(R.id.back);


        String titleinfo = "";
        String description = " ";
        String dateinfo = " ";
        String channel = " ";
        String month, day;
        String url = "";
        Integer color = 0;
        Integer position = 0;
        this.position = (Integer) getArguments().get("position");
        color = (Integer) getArguments().getInt("color");

        description = (String) getArguments().getString("info");
        dateinfo = (String) getArguments().getString("date");
        channel = (String) getArguments().getString("channel");
        titleinfo = (String) getArguments().getString("title");
        url = (String) getArguments().getString("thumb");

        day = dateinfo.substring(8,10);
        month = dateinfo.substring(5,7);

        time_ago.setText(getTimeAgo(dateinfo)); //+ " from: ");
        video_title.setText(titleinfo);


        pb = (ProgressBar) rootView.findViewById(R.id.progressBar);
        cover = (ImageView) rootView.findViewById(R.id.video_cover);
        //back.setBackgroundColor(color);
        cover.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);

        Picasso.with(getActivity()).load(url).into(cover, new Callback() {
            @Override
            public void onSuccess() {
                //cover.setVisibility(View.VISIBLE);
                //pb.setVisibility(View.GONE);

            }

            @Override
            public void onError() {

            }
        });

        ImageView play = (ImageView) rootView.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                        getActivity(), DEVELOPER_KEY, (String) getArguments().get("id"), 0, true, false);
                startActivityForResult(intent, 1);
            }
        });

        if(position == 1) {
            back.setOnTouchListener(new View.OnTouchListener() {

                float endpoint = 0 ;
                float startPoint=0;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                        {
                            startPoint=event.getX();
                            //System.out.println("Action down,..."+event.getX());
                        }

                        break;
                        case MotionEvent.ACTION_MOVE:
                        {

                        }
                        break;
                        case MotionEvent.ACTION_UP:
                        {

                            endpoint=event.getX();
                            if(endpoint > startPoint + startPoint/7 ){
                                Log.d("Swipe", "Right swipe");

                            }else if (endpoint < startPoint - startPoint/7 ){
                                Log.d("Swipe", "Left swipe");
                            getActivity().finish();
                            }

                        }
                        break;

                    }
                    return true;
                }
            });
        }




        return rootView;
    }
}
