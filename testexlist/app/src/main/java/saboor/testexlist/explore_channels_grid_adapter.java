package saboor.testexlist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Saboor Salaam on 7/3/2014.
 */
public class explore_channels_grid_adapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;
    List<channel> mChannels = new ArrayList<channel>();
    List<category> mCategories; //= new ArrayList<category>();
    ArrayList<Integer> mColors = new ArrayList<Integer>();
    ParseDBCommunicator parseDBCommunicator;

    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;



    public explore_channels_grid_adapter(Context c){

        mContext = c;
        this.inflater = LayoutInflater.from(c);
        parseDBCommunicator = new ParseDBCommunicator(c);

        mImageViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);



        Thread client = new Thread(new Runnable() {
            public void run() {
                mChannels = parseDBCommunicator.getChannelsWithVideos(new ParseDBCommunicator.connectionFailedListener() {
                    @Override
                    public void onConnectionFailed() {

                    }

                    @Override
                    public void onConnectionSuccessful() {

                    }

                    @Override
                    public void onCannotConnectToParse() {

                    }
                });
            }
        });
        client.start();
        //wait for background thread to finish
        try {
            client.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Log.d("mCateroies", "mCateroies is: " + mCategories.size());

        for(int i = 0; i < mChannels.size(); i++)
        {
            Random random = new Random();
            mColors.add(Color.rgb(random.nextInt(0xff), random.nextInt(0xff), random.nextInt(0xff)));
        }
    }

    public void setNumColumns(int numColumns) {
        mNumColumns = numColumns;
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    // set photo item height
    public void setItemHeight(int height) {
        if (height == mItemHeight) {
            return;
        }
        mItemHeight = height;
        mImageViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        notifyDataSetChanged();
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

        View v;
        //if the view doesnt already exist

        if(convertView==null)
        {
            LayoutInflater li = LayoutInflater.from(mContext);
            v = li.inflate(R.layout.grid_item2, null);
        }else{
            v = convertView;
        }


        ViewFlipper cover = (ViewFlipper) v.findViewById(R.id.cover);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView channel_size = (TextView) v.findViewById(R.id.size);
        LinearLayout size_holder = (LinearLayout) v.findViewById(R.id.size_holder); //Define view objects



        cover.setLayoutParams(mImageViewLayoutParams);
// Check the height matches our calculated column width
        if (cover.getLayoutParams().height != mItemHeight) {
            cover.setLayoutParams(mImageViewLayoutParams);
        }


        ImageView one,two; //define two cover images for each grid cell
        one = new ImageView(mContext);
        two = new ImageView(mContext);

        Picasso.with(mContext).load(mChannels.get(position).getVid1thumb()).centerCrop().fit().into(one);//put image inside view flipper
        title.setText(mChannels.get(position).getChannel_name());


        cover.setInAnimation(mContext, R.anim.in_from_bottom);
        cover.setOutAnimation(mContext, R.anim.out_to_right);
        cover.addView(one); //Add first image to slide show
        cover.stopFlipping();


        if(mChannels.get(position).getSize() != 0) {  //If channel has new images

            size_holder.setVisibility(View.VISIBLE);  //Show amount of new images in right corner
            channel_size.setText(" "+mChannels.get(position).getSize()+"");

            Picasso.with(mContext).load(mChannels.get(position).getVid2thumb()).centerCrop().fit().into(two);//Add second view
            cover.addView(two);

            cover.setFlipInterval(10000 - position*150);// Start flipping between views
            cover.startFlipping();

            //Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.scale);
            //setGrayScale(cover);
            //cover.startAnimation(anim);
        }
        else{}

        return v;
    }

    public static void setGrayScale(ImageView v){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0); //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

}

