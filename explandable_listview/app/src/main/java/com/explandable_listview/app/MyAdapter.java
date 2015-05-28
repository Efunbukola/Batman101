package com.explandable_listview.app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 6/16/2014.
 */
public class MyAdapter extends BaseAdapter {
    Context mContext;
    int mCount;
    ArrayList<Integer> mHeights = new ArrayList<Integer>();
    ArrayList<Integer> mColors = new ArrayList<Integer>();
    ArrayList<Boolean> mSelectedPositions = new ArrayList<Boolean>();
    List<YouTubeVideo> mVideos = new ArrayList<YouTubeVideo>();

    public MyAdapter(final Context context, int count, List<YouTubeVideo> mVideos) {
        this.mVideos = mVideos;
        mContext = context;
        mCount = count;
        for (int i = 0; i < mCount; i++) {
            mHeights.add(500);//(int) (Math.random() * 800 + 200));
            mColors.add(Color.rgb((int) (Math.random() * 0x80), (int) (Math.random() * 0x80), (int) (Math.random() * 0x80)));
            mSelectedPositions.add(false);
        }
    }

    @Override
    public int getCount() {
        return mVideos.size();
    }

    @Override
    public Object getItem(final int position) {
        return position;
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
       View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null, false);
        }
        AbsListView.LayoutParams params = (AbsListView.LayoutParams) view.getLayoutParams();

        if (params == null) {

            params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT , mHeights.get(position));
        } else {
            params.height = mHeights.get(position);
        }
        view.setLayoutParams(params);

        TextView text = (TextView)view.findViewById(R.id.text);
        text.setText(mVideos.get(position).getVideoTitle().substring(0, 10));
        ImageView image = (ImageView) view.findViewById(R.id.image);
        new DownloadImageTask(image).execute(mVideos.get(position).getVideoThumbnail());

        image.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setBackgroundColor(mSelectedPositions.get(position) ? 0xFF000000 : mColors.get(position));
        view.setLayoutParams(params);

        return view;
    }

    public void toggleSelected(final int position) {
        mSelectedPositions.set(position, !mSelectedPositions.get(position));
        notifyDataSetChanged();
    }

    public void clearSelectedPositions() {
        for (int i = 0; i < mSelectedPositions.size(); i++) {
            mSelectedPositions.set(i, false);
        }
        notifyDataSetChanged();
    }
}
