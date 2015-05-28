package DraggableGridViewPager;

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
import android.widget.Toast;

import com.example.saboorsalaam.veed10.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.List;

import DatabaseHandler.DatabaseHandler;
import DynamicGridView.BaseDynamicGridAdapter;

/**
 * Created by Saboor Salaam on 1/24/2015.
 */




public class DraggableGridAdapter extends BaseDynamicGridAdapter {

    public static final int TYPE_CHANNEL = 0;
    private static final int TYPE_FOOTER= 1;
    private int ItemHeight = 0;
    private Context context;
    private RelativeLayout.LayoutParams ImageViewLayoutParams;
    private SparseBooleanArray SelectedItemsIds = new SparseBooleanArray();
    private boolean color_set = false;
    private int color = 0;

    public boolean isDeleteMode() {
        return deleteMode;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    boolean deleteMode = false;

    private int[] empty_channel_views = {R.drawable.empty_channel_place_holder_blue, R.drawable.empty_channel_place_holder_green, R.drawable.empty_channel_place_holder_grey};
    private int counter = 0;





    public DraggableGridAdapter(List<?> items, Context context, int mNumColumns,int item_height) {
        super(context, items, mNumColumns);
        this.ItemHeight = item_height;
        this.context = context;
        this.ImageViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ItemHeight);
    }



    @Override
    public void remove(Object item) {
        super.remove(item);
    }

    @Override
    public int getCount() {
        return getItems().size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        return (position >= getItems().size()) ? TYPE_FOOTER : TYPE_CHANNEL;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }






    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        switch (getItemViewType(position)) {
            case TYPE_CHANNEL:

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

                Field b = null;
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

                final ChannelViewHolder holder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.draggable_gridview_cell_layout, null);
                    holder = new ChannelViewHolder();
                    convertView.setTag(holder);

                } else {
                    holder = (ChannelViewHolder) convertView.getTag();
                }

                holder.build(convertView, context, position, title, size, thumb, thumb2, cover_title, cover2_title);


                break;
            case TYPE_FOOTER:
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.draggable_gridview_addmore_cell, null);
                    convertView.findViewById(R.id.image).setLayoutParams(ImageViewLayoutParams);
                }
                break;
            default:
                break;
        }
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


            //if(color_set && color != 0)

            switch (counter) {
                case 0:
                    cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_blue);
                    color = R.drawable.empty_channel_place_holder_blue;
                    color_set = true;
                    counter++;
                    break;
                case 1:
                    cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_green);
                    color = R.drawable.empty_channel_place_holder_green;
                    color_set = true;
                    counter++;
                    break;
                case 2:
                    cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_grey);
                    color = R.drawable.empty_channel_place_holder_grey;
                    color_set = true;
                    counter=0;
                    break;
                default:
                    cover_image_holder.setBackgroundResource(R.drawable.empty_channel_place_holder_blue);
                    color = R.drawable.empty_channel_place_holder_blue;
                    color_set = true;
                    counter=1;
                    break;
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


                }
            });

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getItems().size() >= 5) {

                        DatabaseHandler databaseHandler = new DatabaseHandler(mContext);
                        Field b = null;
                        try {
                            b = getItem(position).getClass().getField("channel_name");
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        String title = "error";

                        try {
                            title = (String) b.get(getItem(position));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }


                        Field a = null;
                        try {
                            a = getItem(position).getClass().getField("channel_id");
                            Log.d("Position being deleted", "Item " + title + " at position " + position + "/" + getItems().size() + " being deleted");
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        String id = "error";

                        try {
                            id = (String) a.get(getItem(position));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }


                        databaseHandler.deleteChannel(id);
                        remove(getItem(position));
                        notifyDataSetInvalidated();
                        //callingActivity.invalidateGridViews(position);

                    } else {
                        Toast.makeText(getContext(), "You must follow atleast 5 channels", Toast.LENGTH_LONG);
                    }
                }
            });

        }

        private ChannelViewHolder() { }
    }

    public List<Object> getChannels() {
        return super.getItems();
    }

    public void toggleSelection(int position) {
        selectView(position, !SelectedItemsIds.get(position));
    }

    public void removeSelection() {
        SelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            SelectedItemsIds.put(position, value);
        else
            SelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return SelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return SelectedItemsIds;
    }


    public static void setGrayScale(ImageView v, boolean gs){
        if(gs) {
            ColorMatrix gs_matrix = new ColorMatrix();
            gs_matrix.setSaturation(0); //0 means grayscale
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(gs_matrix);
            v.setColorFilter(cf);
        }
        else{
            ColorMatrix gs_matrix = new ColorMatrix();
            gs_matrix.setSaturation(100); //0 means grayscale
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(gs_matrix);
            v.setColorFilter(cf);
            //v.clearColorFilter();
        }
    }
}