/**
 * make a selected mode and every time you click on one item it getts selected
 * make a trash can appear in the corner
 * when you long click while already in select mode views can be dragged around!
 * look into existing gridview select functionality
 * possibly in the same way gmail list view are selected with the pic flipped around
 *
 * https://github.com/Comcast/FreeFlow
 */



package saboor.testexlist;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

public class main extends FragmentActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<channel> mChannels;
    HashMap<String, List<channel>> listDataChild;
    private int mPhotoSize, mPhotoSpacing;
    ParseDBCommunicator parseDBCommunicator;
    movableGridAdapter MGA;
    DraggableGridViewPager gridView;
    private int draggedIndex = -1;
    ImageView trash_icon;
    RelativeLayout lr;
    RelativeLayout trash_view;
    PointF start = new PointF();
    PointF mid = new PointF();
    TextView rh;
    int width;
    database_handler db;
    FrameLayout frame_container;

    private GoogleNavigationDrawer mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<category> navDrawerItems;
    private nd_list_adapter adapter;



    SwipeRefreshLayout swipeLayout;
    private PullToRefreshLayout mPullToRefreshLayout;









    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.overridePendingTransition(R.anim.in_from_left,
                R.anim.out_to_right);
        Parse.initialize(this, "4WShWGs2N5eF0WL3qBj3Gbqm61JyY3tPzSzVU6Q0", "TLe2dAXt8RUiYA1IwUmnm2He2DO0j12qq7bx02lu");
        super.onCreate(savedInstanceState);

       ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //Instance the GoogleNavigationDrawer and set the LayoutParams
        mDrawerLayout = new GoogleNavigationDrawer(getApplication());
        mDrawerLayout.setLayoutParams(params);
        parseDBCommunicator = new ParseDBCommunicator(getApplicationContext());





        List<category> list = parseDBCommunicator.getAllCategories(new ParseDBCommunicator.connectionFailedListener() {
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

        String[] secondarySections = new String[list.size()];

        for(int i = 0; i < list.size(); i++)
        {
            secondarySections[i] = list.get(i).getName();
        }

        // Here we are providing data to the adapter of the ListView
        String[] mainSections = getResources().getStringArray(R.array.navigation_main_sections);

        int[] mainSectionDrawables = new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher, R.drawable.ic_launcher };


        mDrawerLayout.setListViewSections(mainSections, // Main sections
                secondarySections, // Secondary sections
                mainSectionDrawables, // Main sections icon ids
                null, Color.parseColor("#000000"), Color.parseColor("#000000")); // Secondary sections icon ids


        // Now we add the content to the drawer since the menu is already there
        LayoutInflater inflater = getLayoutInflater();
        View contentView = inflater.inflate(R.layout.activity_main, null);

        //Replace frame_layout with home_fragment
        frame_container = (FrameLayout) contentView.findViewById(R.id.frame_container);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, new home_fragment()).commit();



        mDrawerLayout.addView(contentView, 0);

        float density = getResources().getDisplayMetrics().density;
        int padding = (int) (8*density);

        // Now we add a clickable header and an unclickable footer to the menu
        TextView header = new TextView(this);
        header.setGravity(Gravity.LEFT);
        header.setText("Veed");
        header.setTextSize(25);
        header.setTextColor(Color.parseColor("#000000"));
        header.setPadding(padding, padding, padding, padding);

        TextView footer = new TextView(this);
        footer.setText("Copyright 2014");
        footer.setTextColor(Color.parseColor("#000000"));
        footer.setPadding(padding, padding, padding, padding);
        footer.setGravity(Gravity.CENTER_HORIZONTAL);
        footer.setBackgroundColor(Color.BLUE);

        mDrawerLayout.setMenuHeaderAndFooter(header,footer,true,false);
        mDrawerLayout.setListBackgroundColor(Color.WHITE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        int height = size.y;

        mDrawerLayout.setWidth(width);
        setContentView(mDrawerLayout);

        //mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

/*
        // Now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(this)

                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set a OnRefreshListener
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        new Handler().postDelayed(new Runnable() {
                            @Override public void run() {
                                        mChannels = db.getAllLocalChannels();
                                MGA.set(mChannels);
                                mPullToRefreshLayout.setRefreshComplete();
                            }
                        }, 2000);

                    }
                }).options(Options.create().refreshOnUp(true).build())
        // Finally commit the setup to our PullToRefreshLayout
        .setup(mPullToRefreshLayout);

*/
              // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        mDrawerLayout.setOnNavigationSectionSelected(new GoogleNavigationDrawer.OnNavigationSectionSelected() {

            @Override
            public void onSectionSelected(View view, int i, long l) {
                Intent intent2 = new Intent(getApplicationContext(), favorites_activity.class);
                startActivity(intent2);
                Log.d("Selected", "Item selected!!");
                //displayView(i);

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerMenuOpen();
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.addmore:
                Intent intent = new Intent(getApplicationContext(), explore_channels.class);
                startActivity(intent);
                return true;
            case R.id.favorites:
                Intent intent2 = new Intent(getApplicationContext(), favorites_activity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if(gridView.isDeleteMode())
        {
            gridView.deleteModeOff();
            MGA.setDeleteMode(false);
        }
        else {
            super.onBackPressed();
        }
    }



    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(main.this, toast, Toast.LENGTH_SHORT).show();
            }
        });



    }

/* *
    * Diplaying fragment view for selected nav drawer list item
* */

     private void displayView(int position) {
        // update the main content by replacing fragments
       android.support.v4.app.Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new home_fragment();
                break;
            case 1:
                fragment = new favorites_fragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
//            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

}





