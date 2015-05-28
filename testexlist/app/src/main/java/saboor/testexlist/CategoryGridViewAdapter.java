package saboor.testexlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 8/18/2014.
 */
public class CategoryGridViewAdapter extends BaseAdapter {

        Context mContext;
        List<channel> mChannels = new ArrayList<channel>();
        database_handler database_handler;

        public CategoryGridViewAdapter(Context context, List<channel> channels) {
            this.mContext = context;
            this.mChannels = channels;
            this.database_handler = new database_handler(mContext);
        }

        @Override
        public int getCount() {
            return mChannels.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        ImageView thumb_image;
        ProgressBar progressBar;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View vi=convertView;
            if(convertView==null)
                vi = inflater.inflate(R.layout.ex_list_item, null);

            TextView title = (TextView)vi.findViewById(R.id.title); // title
            TextView description = (TextView)vi.findViewById(R.id.description); // artist name
            final TextView follow = (TextView)vi.findViewById(R.id.follow); // duration
            thumb_image=(ImageView)vi.findViewById(R.id.thumb); // thumb image
            progressBar = (ProgressBar) vi.findViewById(R.id.progressBar);



            title.setText(mChannels.get(position).getChannel_name());



            if(database_handler.isUserFollowingChannel(mChannels.get(position).getChannel_id()))
            {
                follow.setText("UNFOLLOW");
            } else{
                follow.setText("FOLLOW");
            }

            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(database_handler.isUserFollowingChannel(mChannels.get(position).getChannel_id()))
                    {
                        database_handler.deleteChannel(mChannels.get(position).getChannel_id());
                        follow.setText("FOLLOW");
                    }else{
                        database_handler.addChannel(mChannels.get(position).getChannel_id());
                        follow.setText("UNFOLLOW");
                    }

                }
            });

            Picasso.with(mContext).load(mChannels.get(position).getVid1thumb()).centerCrop().fit().into(thumb_image,new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                    thumb_image.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {

                }
            });

                return vi;
        }
}
