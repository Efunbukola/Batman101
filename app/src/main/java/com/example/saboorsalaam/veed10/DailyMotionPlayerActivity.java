package com.example.saboorsalaam.veed10;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import UtilityClasses.DMWebVideoView;


public class DailyMotionPlayerActivity extends Activity {

    private DMWebVideoView mVideoView;

    @Override
    public void onBackPressed() {
        mVideoView.handleBackPress(this);
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mVideoView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mVideoView.onResume();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailymotion_player);

        mVideoView = ((DMWebVideoView) findViewById(R.id.dmWebVideoView));
            Intent intent = getIntent();
            mVideoView.setVideoId(intent.getStringExtra("video_id"));
            mVideoView.setAutoPlay(true);

        }



        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
