package com.app.veed.veed.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.veed.veed.DatabaseHandler.DatabaseHandler;
import com.app.veed.veed.ParseClasses.Channel;
import com.app.veed.veed.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 8/18/2014.
 */
public class CategoryGridViewAdapter extends BaseAdapter {

        Context context;
        private List<Channel> channels = new ArrayList<Channel>();
        private DatabaseHandler databaseHandler;
        private AbsListView.LayoutParams ImageViewLayoutParams;
        ChannelViewHolder holder;

        public CategoryGridViewAdapter(Context context, List<Channel> channels, int itemHeight, int itemWidth) {
            this.context = context;
            this.channels = channels;
            this.databaseHandler = new DatabaseHandler(context);
            this.ImageViewLayoutParams = new AbsListView.LayoutParams(itemWidth, itemWidth);
            databaseHandler = new DatabaseHandler(context);
        }

        @Override
        public int getCount() {
            return channels.size();
        }

        @Override
        public Object getItem(int position) {
            return channels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {



            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.category_channel_grid_item, null);
                holder = new ChannelViewHolder();
                convertView.setTag(holder);
            } else {
                holder = (ChannelViewHolder) convertView.getTag();
            }

            holder.build(convertView, context, position, channels.get(position).getChannel_name(), channels.get(position).getVid1thumb(), channels.get(position).getVid2thumb());






                return convertView;
        }

    private class ChannelViewHolder {
        private ImageView cover_image, follow_button;
        private TextView channel_title_text_view;
        private RelativeLayout main, channel_title_text_view_holder;
        private ProgressBar progressBar;
        private View shade;



        public void build(View view, final Context mContext, final int position, String title, String thumb, final String thumb2) {

            cover_image = (ImageView) view.findViewById(R.id.cover);
            main = (RelativeLayout) view.findViewById(R.id.channel_item);
            follow_button = (ImageView) view.findViewById(R.id.follow_button);
            channel_title_text_view = (TextView) view.findViewById(R.id.title);
            channel_title_text_view_holder = (RelativeLayout) view.findViewById(R.id.title_holder);
            progressBar = (ProgressBar) view.findViewById(R.id.progbar);

            channel_title_text_view.setText(title);

            follow_button.setLayoutParams(new RelativeLayout.LayoutParams(ImageViewLayoutParams.width / 5, ImageViewLayoutParams.height / 4));
            main.setLayoutParams(ImageViewLayoutParams);

            cover_image.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);


            Picasso.with(context).load(thumb).centerCrop().fit().into(cover_image, new Callback() { //Load image
                @Override
                public void onSuccess() {
                    Log.d("Image Loaded", "Image Loaded:  " + position);
                    cover_image.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    Picasso.with(context).load(thumb2).centerCrop().fit().into(cover_image, new Callback() { //Load image
                        @Override
                        public void onSuccess() {
                            Log.d("Image Loaded", "Image Loaded:  " + position);
                            cover_image.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                        }
                    });
                }
            });

            if(databaseHandler.isUserFollowingChannel(channels.get(position).getChannel_id())) //Set follow icon to correct state
            {
                follow_button.setImageResource(R.drawable.followed);
            } else{
                follow_button.setImageResource(R.drawable.follow);
            }

            follow_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(databaseHandler.isUserFollowingChannel(channels.get(position).getChannel_id()))
                    {
                        databaseHandler.deleteChannel(channels.get(position).getChannel_id());
                        follow_button.setImageResource(R.drawable.follow);
                    }else{
                        databaseHandler.addChannel(channels.get(position).getChannel_id());
                        follow_button.setImageResource(R.drawable.followed);                    }

                }
            });

        }

        private ChannelViewHolder() { }
    }

}
