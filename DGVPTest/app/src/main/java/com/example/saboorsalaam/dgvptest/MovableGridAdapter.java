package com.example.saboorsalaam.dgvptest;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.saboorsalaam.dgvptest.DatabaseHandler.DatabaseHandler;
import com.example.saboorsalaam.dgvptest.ParseClasses.Channel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 8/18/2014.
 */
public class MovableGridAdapter extends BaseAdapter {

    public static final int TYPE_CHANNEL = 0;
    private static final int TYPE_FOOTER= 1;
    private int ItemHeight = 0;
    private RelativeLayout.LayoutParams ImageViewLayoutParams;
    MainActivity callingActivity;
    private List<Channel> channels = new ArrayList<Channel>();

    public boolean isDeleteMode() {
        return deleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    boolean deleteMode = false;

    private int[] empty_channel_views = {R.drawable.empty_channel_place_holder_blue, R.drawable.empty_channel_place_holder_green, R.drawable.empty_channel_place_holder_grey};
    private int counter = 0;

    public MovableGridAdapter(List<Channel> channels, MainActivity callingActivity, int mNumColumns, int item_height) {
        this.callingActivity = callingActivity;
        this.ItemHeight = item_height;
        this.channels = channels;
        this.ImageViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ItemHeight);
    }

    public void set(List<Channel> channels)
    {
        this.channels = channels;
    }

    public List<Channel> getItems()
    {
        return this.channels;
    }

    public void add(int position,Channel item)
    {
        this.channels.add(position,item);
        notifyDataSetChanged();
    }

    public void add(List<Channel> items) {
        this.channels.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(Channel item)
    {
        this.channels.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.channels.size()+1;
    }

    @Override
    public Channel getItem(int position) {
        return this.channels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= this.channels.size()) ? TYPE_FOOTER : TYPE_CHANNEL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        switch (getItemViewType(position)) {
            case TYPE_CHANNEL:

                final ChannelViewHolder holder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(this.callingActivity).inflate(R.layout.draggable_gridview_cell_layout, null);
                    holder = new ChannelViewHolder();
                    convertView.setTag(holder);

                } else {
                    holder = (ChannelViewHolder) convertView.getTag();
                }

                holder.build(convertView, callingActivity, position, this.channels.get(position).getChannel_name(), this.channels.get(position).getSize(), this.channels.get(position).getVid1thumb(), this.channels.get(position).getVid2thumb(), this.channels.get(position).getCover_title(), this.channels.get(position).getCover2_title());

                break;
            case TYPE_FOOTER:
                if (convertView == null) {
                    convertView = LayoutInflater.from(this.callingActivity).inflate(R.layout.draggable_gridview_addmore_cell, null);
                }
                break;
            default:
                break;
        }
        return convertView;
    }


    public boolean isDataSetNew(List<Channel> items) {
        return (items == channels);
    }


    private class ChannelViewHolder {
        private ImageView cover_image;
        private TextView channel_title_text_view;
        private TextView channel_size_text_view;
        private TextView cover_title_text_view;
        private LinearLayout channel_size_text_view_holder, cover_title_text_view_holder;
        private RelativeLayout cover_image_holder,channel_title_text_view_holder;
        private ImageView delete_button;
        private ProgressBar progressBar;
        private View shade;



        public void build(View view, final Context mContext, final int position, String title, final int size, String thumb, String thumb2, final String cover_title, final String cover2_title) {

            cover_image = (ImageView) view.findViewById(R.id.cover);
            cover_image_holder = (RelativeLayout) view.findViewById(R.id.cover_holder);

            channel_title_text_view = (TextView) view.findViewById(R.id.title);
            channel_title_text_view_holder = (RelativeLayout) view.findViewById(R.id.title_holder);


            channel_size_text_view = (TextView) view.findViewById(R.id.size);
            channel_size_text_view_holder = (LinearLayout) view.findViewById(R.id.size_holder);

            delete_button = (ImageView) view.findViewById(R.id.delete_button);
            progressBar = (ProgressBar) view.findViewById(R.id.progbar);

            cover_title_text_view = (TextView) view.findViewById(R.id.cover_title);
            cover_title_text_view_holder = (LinearLayout) view.findViewById(R.id.cover_title_holder);

            shade = (View) view.findViewById(R.id.shade);

            shade.setVisibility(View.GONE);


            cover_image.setLayoutParams(ImageViewLayoutParams);
            delete_button.setLayoutParams(new RelativeLayout.LayoutParams(ItemHeight / 5, ItemHeight / 4));

            if(isDeleteMode())
            {
                delete_button.setVisibility(View.VISIBLE);
            }
            if (!isDeleteMode())
            {
                delete_button.setVisibility(View.GONE);
            }

            channel_title_text_view.setText(title);

            if (size > 0) {  //If channel has new images
                channel_size_text_view.setVisibility(View.VISIBLE);  //Show amount of new images in right corner
                channel_size_text_view.setText(" " + size);
            } else {
                channel_size_text_view_holder.setVisibility(View.INVISIBLE);
                //setGrayScale(cover);
            }





            Picasso.with(mContext).load(thumb).centerCrop().fit().into(cover_image, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("Image Loaded", "Image Loaded:  " + position);
                    cover_image.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    cover_title_text_view_holder.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                    cover_title_text_view.setText(cover_title);
                    cover_title_text_view_holder.setVisibility(View.VISIBLE);

                    channel_title_text_view_holder.setBackground(null);
                    progressBar.setVisibility(View.GONE);

                    channel_title_text_view.setShadowLayer(0,0,0,0);
                    //channel_size_text_view.setShadowLayer(0,0,0,0);
                    cover_title_text_view.setShadowLayer(0,0,0,0);

                    switch (counter) {
                        case 0:
                            cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_blue);
                            counter++;
                            break;
                        case 1:
                            cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_green);
                            counter++;
                            break;
                        case 2:
                            cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_grey);
                            counter=0;
                            break;
                        default:
                            cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_blue);
                            counter=1;
                            break;
                    }


                }
            });

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (channels.size() >= 5) {

                        DatabaseHandler databaseHandler = new DatabaseHandler(mContext);
                        String id = channels.get(position).getChannel_id();
                        databaseHandler.deleteChannel(id);
                        remove(getItem(position));
                        notifyDataSetInvalidated();
                        //callingActivity.invalidateGridViews(position);

                    } else {
                        Toast.makeText(callingActivity, "You must follow atleast 5 channels", Toast.LENGTH_LONG);
                    }
                }
            });

        }

        private ChannelViewHolder() { }
    }



}
















