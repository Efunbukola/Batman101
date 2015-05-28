package com.app.cedar.cedarappbeta;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nirhart.parallaxscroll.views.ParallaxListView;
import com.parse.ParseUser;

import org.arasthel.googlenavdrawermenu.views.GoogleNavigationDrawer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Home_activity extends Activity {

    ParseDBCommunicator parseDBCommunicator;
    private GoogleNavigationDrawer mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    main_list_adapter mla;
    List<Event> events;
    List<Job> jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_activity);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        //Instance the GoogleNavigationDrawer and set the LayoutParams
        mDrawer = new GoogleNavigationDrawer(this);
        mDrawer.setLayoutParams(params);
        
        // Here we are providing data to the adapter of the ListView
        String[] mainSections = {"My Schedule", "Information", "Workshops", "Road Map", "Food?", "Jobs"};
        String[] secondarySections = {"Settings", "Sign Out"};
        int[] mainSectionDrawables = {R.drawable.schedule,R.drawable.info_ses, R.drawable.workshop, R.drawable.roadmap, R.drawable.food, R.drawable.job};
        mDrawer.setListViewSections(mainSections, // Main sections
                secondarySections, // Secondary sections
                mainSectionDrawables, // Main sections icon ids
                null); // Secondary sections icon ids


        // Now we add the content to the drawer since the menu is already there
        LayoutInflater inflater = getLayoutInflater();
        View contentView = inflater.inflate(R.layout.activity_home_activity, null);
        getActionBar().setDisplayShowHomeEnabled(false);


        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0033")));
        getActionBar().setDisplayShowHomeEnabled(false);

        Bundle extras = getIntent().getExtras();
        String name = "";
        if(extras !=null) {
            name = extras.getString("name");
        }
        parseDBCommunicator = new ParseDBCommunicator(this);

        final ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(getApplicationContext(), "Error logging in! Try agian", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), SignIn_activity.class);
            startActivity(intent);
        }


        final PullToRefreshListView listView = (PullToRefreshListView) contentView.findViewById(R.id.listView);

        events = parseDBCommunicator.getEventMatches(currentUser.getString("major"), new ParseDBCommunicator.connectionFailedListener() {
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

        jobs = parseDBCommunicator.getJobMatches(currentUser.getNumber("gpa"),currentUser.getString("major"), new ParseDBCommunicator.connectionFailedListener() {
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

        mla = new main_list_adapter(events,jobs, getApplicationContext());


        listView.setAdapter(mla);


        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        listView.onRefreshComplete();
                        //mp.start();
                    }
                }, 2000);
            }
        });






        //TextView welcome_message = (TextView) findViewById(R.id.welcome_message);
        //welcome_message.setText("Welcome, " + name + "!");

        mDrawer.addView(contentView, 0);

        float density = getResources().getDisplayMetrics().density;
        int padding = (int) (8*density);

        // Now we add a clickable header and an unclickable footer to the menu
        TextView header = new TextView(this);
        header.setTextSize(25);
        header.setText("Welcome, " + name);
        header.setTextColor(Color.parseColor("#FFFFFF"));
        header.setTypeface(null, Typeface.BOLD);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        header.setBackgroundColor(Color.parseColor("#ff0033"));
        header.setText(name);
        header.setPadding(padding, padding, padding, padding);

        TextView footer = new TextView(this);
        footer.setText("Cedar Connect 2014");
        footer.setTextColor(Color.WHITE);
        footer.setPadding(padding, padding, padding, padding);
        footer.setGravity(Gravity.LEFT);
        footer.setBackgroundColor(Color.parseColor("#ff0033"));
        mDrawer.setMenuHeaderAndFooter(header,footer,true,false);

        setContentView(mDrawer);

        mDrawer.setOnNavigationSectionSelected(new GoogleNavigationDrawer.OnNavigationSectionSelected() {
            @Override
            public void onSectionSelected(View v, int i, long l) {
                Toast.makeText(getBaseContext(), "Selected section: " + i, Toast.LENGTH_SHORT).show();
            }
        });

        //Prepare the drawerToggle in order to be able to open/close the drawer
        drawerToggle = new ActionBarDrawerToggle(this,
                mDrawer,
                R.drawable.ic_navigation_drawer,
                R.string.app_name,
                R.string.app_name);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //Attach the DrawerListener
        mDrawer.setDrawerListener(drawerToggle);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return true;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {

            ParseUser.logOut();
            finish();
            return true;
        }

        if (id == R.id.schedule) {
            Intent intent = new Intent(getApplicationContext(), Schedule_activity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //DO NOTHING
    }

    public String timeUntil(TimeSpan ts)
    {
        String final_string = "";

        if(ts.months > 0){final_string = ts.months + " months from now";}
        else if(ts.months <= 0 && ts.days > 0) {final_string = ts.days + " days from now";}
        else if(ts.months <= 0 && ts.days <= 0 && ts.hours > 0) {final_string = ts.hours + " hours from now";}
        else if(ts.months <= 0 && ts.days <= 0 && ts.hours <= 0) {final_string = "In less than an hour";}
        else if(ts.months <= 0 && ts.days <= 0 && ts.hours <= 0 && ts.minutes <= 10) {final_string = "Happening Now!";}
        return final_string;
    }
    Date from;



    public List<Event> sortEvents(final List<Event> events)
    {

        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        df.setTimeZone(TimeZone.getTimeZone("EST"));

        from = null; // get time now
        try {
            from = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final DateCalculator dateCalculator = new DateCalculator();

        Collections.sort(events, new Comparator<Event>() {
            public int compare(Event e1, Event e2) {

                Date to = null;
                try {
                    to = df.parse(e1.date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date to2 = null;
                try {
                    to2 = df.parse(e2.date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                double time_until_event1 = dateCalculator.difference(to, from).months*1000 + dateCalculator.difference(to, from).days*100 + dateCalculator.difference(to, from).hours*10 + dateCalculator.difference(to, from).minutes*.1;
                double time_until_event2 = dateCalculator.difference(to2, from).months*1000 + dateCalculator.difference(to2, from).days*100 + dateCalculator.difference(to2, from).hours*10 + dateCalculator.difference(to2, from).minutes*.1;


                return Double.valueOf(time_until_event1).compareTo(time_until_event2);
            }
        });
        return events;
    }



}
