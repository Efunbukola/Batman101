package beta.veed.com.veed;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class Inside_channel extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_channel);

        this.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);


        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ParallaxPagerTransformer parallaxPagerTransformer = new ParallaxPagerTransformer(R.id.back);
        parallaxPagerTransformer.setSpeed(.1f);
        viewPager.setPageTransformer(false, parallaxPagerTransformer);

        final video_vp_adapter video_vp_adapter = new video_vp_adapter(getSupportFragmentManager());

        String id = "";
        String name = "";
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            id = extras.getString("channel_id");
            name = extras.getString("channel_name");
        }

        final List<YouTubeVideo> youTubeVideoList = new ParseDBCommunicator(getApplicationContext()).getVideosofAChannel(id, name, new ParseDBCommunicator.connectionFailedListener() {
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

        video_vp_adapter.setList(youTubeVideoList);
        viewPager.setAdapter(video_vp_adapter);

        TextView channel_title = (TextView) findViewById(R.id.channel_title);
        channel_title.setText(name);



/*
            String name = "";
            String thumb = "";
            Integer size = 0;
            Integer color = 000000;
            name = (String) getArguments().getString("name");
            size = (Integer) getArguments().getInt("size");
            color = (Integer) getArguments().getInt("color");
            thumb = (String) getArguments().getString("thumb");

            RelativeLayout relativeLayout = (RelativeLayout) rootView.findViewById(R.id.holder);
            TextView _name = (TextView) rootView.findViewById(R.id.name);
            TextView _size = (TextView) rootView.findViewById(R.id.size);
            ImageView cover = (ImageView) rootView.findViewById(R.id.imageView);

            _name.setText(name);
            _size.setText(size.toString());

            //Picasso.with(getActivity()).load(thumb).centerCrop().fit().into(cover);
            relativeLayout.setBackgroundColor(color);
            */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inside_channel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
