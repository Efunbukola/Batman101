package beta.veed.com.veed;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;


public class main extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    RelativeLayout drawer_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getActionBar().setCustomView(R.layout.actionbar);
        //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));


        setContentView(R.layout.layout_main);

        final TextView name = (TextView) findViewById(R.id.name);






        VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.pager);
        VerticalParallaxPagerTransformer parallaxPagerTransformer = new VerticalParallaxPagerTransformer(R.id.channel_cover);
        parallaxPagerTransformer.setSpeed(.3f);
        parallaxPagerTransformer.setBorder(4);

        viewPager.setPageTransformer(false, parallaxPagerTransformer);

        final channel_vp_adapter channel_vp_adapter = new channel_vp_adapter(getSupportFragmentManager());
        List<channel> mChannels = new ParseDBCommunicator(getApplicationContext()).getChannelsWithVideos(new ParseDBCommunicator.connectionFailedListener() {
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

        channel_vp_adapter.setList(mChannels);
        viewPager.setAdapter(channel_vp_adapter);
        name.setText(channel_vp_adapter.getTitle(0));

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.sim_actionbar);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                name.setText(channel_vp_adapter.getTitle(position));
                relativeLayout.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView drawer = (ImageView) findViewById(R.id.drawer);
        drawer_view = (RelativeLayout) findViewById(R.id.drawer_view);

        drawer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mDrawerLayout.isDrawerOpen(drawer_view))
                {
                    mDrawerLayout.closeDrawers();
                }else
                {
                    //mDrawerLayout.openDrawer(mDrawerLayout);
                    mDrawerLayout.openDrawer(drawer_view);
                }
                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void openDrawer(){

    }

    public void closeDrawer(){
        mDrawerLayout.closeDrawer(drawer_view);
    }
}
