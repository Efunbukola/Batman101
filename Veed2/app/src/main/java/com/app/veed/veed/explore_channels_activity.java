package com.app.veed.veed;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.app.veed.veed.Adapters.CategoriesViewPagerAdapter;
import com.app.veed.veed.DatabaseHandler.DatabaseHandler;
import com.app.veed.veed.PagerSlidingTabStrip.PagerSlidingTabStrip;
import com.app.veed.veed.ParseClasses.Category;
import com.app.veed.veed.ParseClasses.ParseDBCommunicator;
import com.app.veed.veed.UtilityClasses.ParallaxPagerTransformer;

import java.util.ArrayList;
import java.util.List;

public class explore_channels_activity extends FragmentActivity {

    ViewPager viewPager;
    LinearLayout linearLayout;
    Animation fadeIn, fadeOut2, fadeOut;
    int position;


    DatabaseHandler databaseHandler;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_explore_channels_activity);


        Display display = getWindowManager().getDefaultDisplay();
        Point sz = new Point();
        display.getSize(sz);
        int width = sz.x;
        int height = sz.y;


        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(300);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(300);

        fadeOut2 = new AlphaAnimation(1, 0);
        fadeOut2.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut2.setDuration(300);


        viewPager = (ViewPager) findViewById(R.id.pager);






        ListView listView = (ListView) findViewById(R.id.listView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        ParseDBCommunicator parseDBCommunicator = new ParseDBCommunicator(getApplicationContext());
        List<Category> categories =  parseDBCommunicator.getAllCategories(new ParseDBCommunicator.connectionFailedListener() {
            @Override
            public void onConnectionFailed() {
                Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectionSuccessful() {

            }

            @Override
            public void onCannotConnectToParse() {
                Toast.makeText(getApplicationContext(), R.string.no_connection_to_parse, Toast.LENGTH_LONG).show();
            }
        });


        List<String> items = new ArrayList<String>();
        for(int i = 0; i < categories.size(); i++)
        {
            items.add("" + categories.get(i).getName());
        }




        int GridSpacing = (int) this.getResources().getDimensionPixelSize(R.dimen.gridview_spacing_integer);
        Log.e("GetGridSpacing", "GridSpacing " + GridSpacing);

        CategoriesViewPagerAdapter categoriesViewPagerAdapter = new CategoriesViewPagerAdapter(getSupportFragmentManager(), (height/3) - GridSpacing*4, (width/3) - GridSpacing*5, this);
        Log.d("Height", "Final Height EXC activity: "+ height/2 + "");
        categoriesViewPagerAdapter.setList(categories);
        viewPager.setAdapter(categoriesViewPagerAdapter);
        ParallaxPagerTransformer parallaxPagerTransformer = new ParallaxPagerTransformer(R.id.gridView);
        parallaxPagerTransformer.setBorder(20);
        //viewPager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.gridView));

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, R.layout.list_categories_row, items);
        listView.setAdapter(itemsAdapter);



        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setTextSize(20);
        tabs.setTextColor(Color.BLACK);
        tabs.setViewPager(viewPager);
        tabs.setAllCaps(false);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                position = i;

                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        linearLayout.setVisibility(View.GONE);
                        listmode = false;
                        viewPager.setCurrentItem(position);


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


                linearLayout.startAnimation(fadeOut);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.explore_channels_activity, menu);
        return true;
    }

    Boolean listmode = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        int id = item.getItemId();
        if (id == R.id.listed) {

            if(!listmode) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout.startAnimation(fadeIn);

                listmode = true;
            }else {
                linearLayout.startAnimation(fadeOut2);
                linearLayout.setVisibility(View.GONE);
                listmode = false;
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (listmode) {
            linearLayout.startAnimation(fadeOut2);
            linearLayout.setVisibility(View.GONE);
            listmode = false;
        } else {
            super.onBackPressed();
        }
    }
}
