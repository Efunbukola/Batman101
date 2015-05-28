package saboor.testexlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

import java.util.List;

public class inside_channel extends FragmentActivity {
    ParseDBCommunicator parseDBCommunicator;
    ViewPager mPager;
    database_handler db;
    List<Video> mVideos;
    channel_vp_adapter mChannel_VP_Adapter;
    String id, name;
    MenuItem fav_item;
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_channel);



        this.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);

        parseDBCommunicator = new ParseDBCommunicator(getApplicationContext());
       db = new database_handler(getApplicationContext());

        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            id = extras.getString("channel_id");
            name = extras.getString("channel_name");

        setTitle(name);
        }
                mVideos = parseDBCommunicator.getVideosofAChannel(id, name, new ParseDBCommunicator.connectionFailedListener() {
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
                Log.d("Size", "Retrieved from id: "+ id + ": " + mVideos.size() + " videos");

       mPager = (ViewPager) findViewById(R.id.pager);

        mChannel_VP_Adapter = new channel_vp_adapter(getSupportFragmentManager());
        mChannel_VP_Adapter.setList(mVideos);
        Log.d("Size", "Retrieved: " + mVideos.size() + " videos");
        mPager.setAdapter(mChannel_VP_Adapter);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inside_channel, menu);
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();
        mShareActionProvider.setShareIntent(getDefaultShareIntent(mVideos.get(mPager.getCurrentItem())));


        fav_item = menu.findItem(R.id.add_to_favorites);

        if(db.isVideoAFavorite(mVideos.get(mPager.getCurrentItem()).getVideoId()))
        {
            fav_item.setIcon(R.drawable.fav_selected_state);
        }

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {



                /** Setting a share intent */
                mShareActionProvider.setShareIntent(getDefaultShareIntent(mVideos.get(mPager.getCurrentItem())));

                if(db.isVideoAFavorite(mVideos.get(mPager.getCurrentItem()).getVideoId()))
                {
                    fav_item.setIcon(R.drawable.fav_selected_state);
                }
                else
                {
                    fav_item.setIcon(R.drawable.fav_unselected_state);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });





        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.add_to_favorites:

                if(!db.isVideoAFavorite(mVideos.get(mPager.getCurrentItem()).getVideoId()))//If its not already a favorite set icon red and add to favs
                {
                    db.addFavorite(mVideos.get(mPager.getCurrentItem()));
                    fav_item.setIcon(R.drawable.fav_selected_state);
                }
                else //if is already a ready a fav set icon back to grey and remove from favs
                {
                    fav_item.setIcon(R.drawable.fav_unselected_state);
                    db.deleteFavorite(mVideos.get(mPager.getCurrentItem()));
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Intent getDefaultShareIntent(Video video){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        if(video.type.equals("yt")) {
            intent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" +  video.getVideoTitle() + "\" on YouTube");
            intent.putExtra(Intent.EXTRA_TEXT,"https://www.youtube.com/watch?v=" + video.getVideoId());
        }

        if(video.type.equals("v"))
        {
            intent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" +  video.getVideoTitle() + "\" on Vimeo");
            intent.putExtra(Intent.EXTRA_TEXT, "https://vimeo.com/" + video.getVideoId());
        }

        return intent;
    }
}
