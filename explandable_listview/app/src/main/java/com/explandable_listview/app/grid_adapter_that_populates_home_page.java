package com.explandable_listview.app;

/**
 * Created by Saboor Salaam on 6/15/2014.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class grid_adapter_that_populates_home_page extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    List<YouTubeChannel> mChannels = new ArrayList<YouTubeChannel>();
    ArrayList<Integer> mColors = new ArrayList<Integer>();
    RestHandler restHandler = new RestHandler();
    DatabaseHandler db;
    String jsonString;


    public grid_adapter_that_populates_home_page(Context c){

        mContext = c;
        this.inflater = LayoutInflater.from(c);
         db = new DatabaseHandler(c);
        mChannels = db.getUsersChannels();



    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;//squares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewFlipper vi=(ViewFlipper) convertView;
        //if the view doesnt already exist
        if (convertView==null){
            vi = new ViewFlipper(mContext);

        }

        AbsListView.LayoutParams params = (AbsListView.LayoutParams) vi.getLayoutParams();

        if (params == null) {
            params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT ,550);
        } else {
            params.height = 550;
        }

        vi.setLayoutParams(params);

        Thread client = new Thread(new Runnable() {
            public void run() {

                 jsonString = restHandler.httpGet("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + mChannels.get(position).getId() + "&maxResults=4&order=date&type=video&key=AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo");

            }
        });

        client.start();

        //wait for background thread to finish
        try {
            client.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<YouTubeVideo> videos  = restHandler.makeYouTubeList(jsonString);


        View view1;
        view1 = LayoutInflater.from(mContext).inflate(R.layout.box, null, false);
        TextView text1 = (TextView) view1.findViewById(R.id.title);
        text1.setText(mChannels.get(position).getName());
        ImageView image = (ImageView) view1.findViewById(R.id.imageView);
        new DownloadImageTask(image).execute(videos.get(0).getVideoThumbnail());
        //image.setScaleType(ImageView.ScaleType.CENTER_CROP);

        View view2;
        view2 = LayoutInflater.from(mContext).inflate(R.layout.box, null, false);
        TextView text2 = (TextView) view2.findViewById(R.id.title);
        text2.setText(videos.get(2).getVideoTitle().substring(0,9));

        ImageView image2 = (ImageView) view2.findViewById(R.id.imageView);
        //new DownloadImageTask(image2).execute(videos.get(2).getVideoThumbnail());

        vi.setInAnimation(mContext, R.anim.in_from_left);
        vi.setOutAnimation(mContext, R.anim.out_to_right);

        vi.addView(view1);
        //vi.addView(view2);
        //vi.setFlipInterval(9000 + (position*10));
        //vi.setAutoStart(true);
        return vi;
    }



}

