package Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import CustomYouTubePlayer.Orientation;

import com.example.saboorsalaam.veed10.DailyMotionPlayerActivity;
import com.example.saboorsalaam.veed10.R;
import com.example.saboorsalaam.veed10.VimeoPlayerActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ParseClasses.ParseDBCommunicator;
import ParseClasses.Video;
import UtilityClasses.MyCalculator;
import com.example.saboorsalaam.veed10.YouTubePlayerActivity;


public class HomeListViewAdapter extends ArrayAdapter<Video>{

    Context context;
    List<Video> videos = new ArrayList<Video>();
    ViewHolder viewHolder;
    private int[] empty_channel_views = {R.drawable.empty_channel_place_holder_blue, R.drawable.empty_channel_place_holder_green, R.drawable.empty_channel_place_holder_grey};
    private int counter = 0;
    private int color = 0;


    public HomeListViewAdapter(Context context, List<Video> videos)
    {
        super(context, R.layout.home_list_item, videos);
        this.context = context;
        this.videos = videos;
        for (int z = 0; z < videos.size(); z++) {
            Log.d("Home List Adapter", "Added video from " + videos.get(z).getProvider_name() + " of type " + videos.get(z).getType());

        }
    }

    static class ViewHolder {
        TextView title_textview, date_textview, channel_textview, provider_textview;
        ProgressBar progressBar;
        ImageView cover;
        RelativeLayout cover_holder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item, null);
        }
            viewHolder = new ViewHolder();

            //Set video information
            viewHolder.title_textview = (TextView) convertView.findViewById(R.id.title);
            viewHolder.date_textview = (TextView) convertView.findViewById(R.id.date);
            viewHolder.channel_textview = (TextView) convertView.findViewById(R.id.channel);
            viewHolder.provider_textview = (TextView) convertView.findViewById(R.id.provider);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.pb);
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.cover);
            viewHolder.cover_holder = (RelativeLayout) convertView.findViewById(R.id.cover_holder);

            final String dateinfo = videos.get(position).getDatePublished();
            final String channel = videos.get(position).getChannel_name();
            final String provider = videos.get(position).getProvider_name();
            final String titleinfo = videos.get(position).getVideoTitle();
            final String type = videos.get(position).getType();
            String url = videos.get(position).getVideoThumbnail();

            if(type.equals("yt")) {
                url = "http://img.youtube.com/vi/" + videos.get(position).getVideoId() + "/maxresdefault.jpg";
            }


            if (dateinfo.length() > 9) {
                String day = dateinfo.substring(8, 10);
                String month = dateinfo.substring(5, 7);
            }

            String timePassedString = MyCalculator.getTimeAgo(dateinfo);

            viewHolder.date_textview.setText(timePassedString);
            viewHolder.channel_textview.setText(channel);
            viewHolder.provider_textview.setText(provider);
            viewHolder.title_textview.setText(titleinfo);

        switch (counter) {
            case 0:
                viewHolder.cover_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_blue);
                color = R.drawable.empty_channel_place_holder_blue;
                counter++;
                break;
            case 1:
                viewHolder.cover_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_green);
                color = R.drawable.empty_channel_place_holder_green;
                counter++;
                break;
            case 2:
                viewHolder.cover_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_grey);
                color = R.drawable.empty_channel_place_holder_grey;
                counter=0;
                break;
            default:
                viewHolder.cover_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_blue);
                color = R.drawable.empty_channel_place_holder_blue;
                counter=1;
                break;
        }


            Picasso.with(context).load(url).centerCrop().fit().into(viewHolder.cover, new Callback() {
                @Override
                public void onSuccess() {
                    viewHolder.cover.setVisibility(View.VISIBLE);
                    viewHolder.progressBar.setVisibility(View.GONE);
                }
                @Override
                public void onError() {

                }
            });

            viewHolder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, "Clicked on "+ type  +" video", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    switch(type){
                        case "v":
                            intent = new Intent(context, VimeoPlayerActivity.class);
                            intent.putExtra("video_id", videos.get(pos).getVideoId());
                            context.startActivity(intent);
                            break;
                        case "yt":
                            intent = new Intent(context, YouTubePlayerActivity.class);
                            intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, YouTubePlayer.PlayerStyle.DEFAULT);
                            intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.AUTO);
                            intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, true);
                            intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);
                            intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.modal_close_enter);
                            intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.modal_close_exit);
                            intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, videos.get(pos).getVideoId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            break;
                        case "dm":
                            intent = new Intent(context, DailyMotionPlayerActivity.class);
                            intent.putExtra("video_id", videos.get(pos).getVideoId());
                            context.startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            });
        return convertView;
    }

}
