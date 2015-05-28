package com.swipe.app;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.youtube.player.YouTubePlayer;


public class ViewPager extends FragmentActivity {

    private android.support.v4.view.ViewPager mPager;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private TabAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);



        //view.setTranslationX(-1 * view.getWidth() * position);

        Button button = (Button) findViewById(R.id.button);
        final EditText amount = (EditText) findViewById(R.id.amount);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(amount.getText().toString());
                mPager = (android.support.v4.view.ViewPager) findViewById(R.id.pager);
                //mPager.setPageTransformer(true, new ZoomOutPageTransformer());


                mPagerAdapter = new TabAdapter(getSupportFragmentManager());
                mPagerAdapter.setAmount(a);
                mPager.setAdapter(mPagerAdapter);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_pager, menu);
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

