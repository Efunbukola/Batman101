package saboor.testexlist;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Saboor Salaam on 7/13/2014.
 */
public class movableGridAdapter extends BaseDynamicGridAdapter {

    private int mItemHeight = 0;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;
    Context context;
    private SparseBooleanArray mSelectedItemsIds = new SparseBooleanArray();

    public boolean isDeleteMode() {
        return deleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    boolean deleteMode = false;





    public movableGridAdapter(List<?> items, Context context, int mNumColumns,int item_height) {
        super(context, items, mNumColumns);
        this.context = context;
        this.mItemHeight = item_height;
        this.mImageViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        //notifyDataSetChanged();
    }

    public static void setGrayScale(ImageView v){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0); //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

          Field a = null;
            try {
                a = getItem(position).getClass().getField("channel_name");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
        }
        String title = "error";

        try {
            title = (String) a.get(getItem(position));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //*****************************************************

        Field b  = null;
        try {
            b = getItem(position).getClass().getField("size");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        int size = 0;

        try {
            size = (Integer) b.getInt(getItem(position));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //*****************************************************

        Field c = null;
        try {
            c = getItem(position).getClass().getField("vid1thumb");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String thumb = null;

        try {
            thumb = (String) c.get(getItem(position));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //*****************************************************

        Field d = null;
        try {
            d = getItem(position).getClass().getField("vid2thumb");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String thumb2 = null;

        try {
            thumb2 = (String) d.get(getItem(position));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field f = null;
        try {
            f = getItem(position).getClass().getField("cover_title");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String cover_title = "error";

        try {
            cover_title = (String) f.get(getItem(position));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Field g = null;
        try {
            g = getItem(position).getClass().getField("cover2_title");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String cover2_title = "error";

        try {
            cover2_title = (String) g.get(getItem(position));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //*****************************************************************************

        ChannelViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item2, null);
            holder = new ChannelViewHolder();

            convertView.setTag(holder);

        } else {
            holder = (ChannelViewHolder) convertView.getTag();
        }

        holder.build(convertView, context, position, title, size, thumb, thumb2, cover_title, cover2_title);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database_handler db = new database_handler(context);
                Field a = null;
                try {
                    a = getItem(position).getClass().getField("channel_id");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                String id = "error";

                try {
                    id = (String) a.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                db.deleteChannel(id);
                remove(getItem(position));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public void set(List<?> items) {   //Refreshing the list
        super.set(items);
        notifyDataSetChanged();
    }

    public boolean isDataSetNew(List<?> items) {
        return (items == getItems());
    }


    private class ChannelViewHolder {
        private ImageView cover;
        private TextView channel_title;
        private TextView channel_size;
        private TextView cover_titleTV;
        private LinearLayout size_holder, cover_title_holder, title_holder;
        private  RelativeLayout cover_holder;
        private ImageView delete;
        private ProgressBar pb;



        public void build(View view, Context mContext, final int position, String title, final int size, String thumb, String thumb2, final String cover_title, final String cover2_title) {


            channel_title = (TextView) view.findViewById(R.id.title);
            channel_size = (TextView) view.findViewById(R.id.size);
            size_holder = (LinearLayout) view.findViewById(R.id.size_holder);
            delete = (ImageView) view.findViewById(R.id.delete_button);
            pb = (ProgressBar) view.findViewById(R.id.progbar);
            cover = (ImageView) view.findViewById(R.id.cover);
            cover_titleTV = (TextView) view.findViewById(R.id.cover_title);
            cover_holder = (RelativeLayout) view.findViewById(R.id.cover_holder);
            cover_title_holder = (LinearLayout) view.findViewById(R.id.cover_title_holder);
            title_holder = (LinearLayout) view.findViewById(R.id.title_holder);

            cover.setLayoutParams(mImageViewLayoutParams);
            delete.setLayoutParams(new RelativeLayout.LayoutParams( mItemHeight/5, mItemHeight/4));


            if(isDeleteMode())
            {
                delete.setVisibility(View.VISIBLE);
            }
            if (!isDeleteMode())
            {
                delete.setVisibility(View.GONE);
            }




            channel_title.setText(title);

            if (size > 0) {  //If channel has new images
                size_holder.setVisibility(View.VISIBLE);  //Show amount of new images in right corner
                channel_size.setText(" " + size);
            } else {
                size_holder.setVisibility(View.INVISIBLE);
                //setGrayScale(cover);
            }



            Picasso.with(mContext).load(thumb).centerCrop().fit().into(cover, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("Image Loaded", "Image Loaded:  " + position);
                    cover.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                    cover_titleTV.setText(cover_title);
                    cover_title_holder.setVisibility(View.VISIBLE);
                    cover_holder.setBackgroundResource(R.drawable.empty_channel_place_holder);
                    title_holder.setBackground(null);
                    pb.setVisibility(View.GONE);
                    /*
                    channel_title.setTextColor(Color.parseColor("#000000"));
                    cover_titleTV.setTextColor(Color.parseColor("#000000"));
                    channel_size.setTextColor(Color.parseColor("#000000"));
                    channel_title.setShadowLayer(0,0,0,0);
                    channel_size.setShadowLayer(0,0,0,0);
                    cover_titleTV.setShadowLayer(0,0,0,0);
                    */

                }
            });
            }

            private ChannelViewHolder() { }
}

    public List<Object> getChannels() {
        return super.getItems();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}