package com.videoswipper.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends FragmentActivity {

    List<VimeoVideo> videos;
    String jsonString;

    String url = "https://www.googleapis.com/youtube/v3/channels?part=id&&order=date&forUsername=" + "channel_name" + "&key=AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
                */
        setContentView(R.layout.activity_main);


        final RestHandler restHandler = new RestHandler();

        Thread client = new Thread(new Runnable() {
            public void run() {

                jsonString = restHandler.httpGet("http://vimeo.com/api/v2/channel/1nspirational/videos.json");

            }
        });

        client.start();

        //wait for background thread to finish
        try {
            client.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView test = (TextView) findViewById(R.id.test);
        videos = restHandler.makeVimeoList(jsonString);
        test.setText(videos.get(6).getTitle() + "by: " + videos.get(6).getDate());




        final VerticalPager mVerticalPager;
        mVerticalPager = (VerticalPager) findViewById(R.id.activity_main_vertical_pager);
        mVerticalPager.setPagingEnabled(true);



        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(mVerticalPager.getId(), new channel_fragment().newInstance("NBA BEST PLAYS", "UCvfQhxWOrXK6SFDBzj8ft0w"), null);
        fragmentTransaction.add(mVerticalPager.getId(), new channel_fragment().newInstance("SMASH BROS IGN", "UCMI-zYunAHPszjQOEzzXoQQ"), null);
        fragmentTransaction.add(mVerticalPager.getId(), new channel_fragment().newInstance("LAUGH FACTORY", "UCxyCzPY2pjAjrxoSYclpuLg"), null);

        fragmentTransaction.commit();





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
}

/*
    public void onBackPressed() {
        if (mVerticalPager.getCurrentPage(). mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    */

