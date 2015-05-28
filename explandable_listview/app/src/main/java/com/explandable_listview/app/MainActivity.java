package com.explandable_listview.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;




public class MainActivity extends ActionBarActivity {

    private boolean mSelectMode;
    String jsonString;
    List<YouTubeVideo> videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    Parse.initialize(this, "4WShWGs2N5eF0WL3qBj3Gbqm61JyY3tPzSzVU6Q0", "TLe2dAXt8RUiYA1IwUmnm2He2DO0j12qq7bx02lu");

        ParseObject channel = new ParseObject("Channel");
        channel.put("channel_name", "BuzzFeed");
        channel.put("channel_id", "UCpko_-a4wgz2u_DgDgd9fqA");
        channel.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(),
                            "Channel Added Succesfully!",
                            Toast.LENGTH_SHORT)
                            .show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }

        });






        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
         WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


        /*
        final RestHandler restHandler = new RestHandler();



         client = new Thread(new Runnable() {
            public void run() {

                jsonString = restHandler.httpGet("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCKy1dAqELo0zrOtPkf0eTMw&maxResults=30&order=viewCount&type=video&key=AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo");

            }
        });

        client.start();

        //wait for background thread to finish
        try {
            client.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        videos = restHandler.makeYouTubeList(jsonString);

        */


        //TextView date = (TextView) findViewById(R.id.date);

        final TextView date = (TextView) findViewById(R.id.date);

        ParseCloud.callFunctionInBackground("hello", new HashMap<String, Object>(), new FunctionCallback<String>() {
            public void done(String result, ParseException e) {
                if (e == null) {

                    date.setText(result);
                    // result is "Hello world!"
                }
            }
        });

        

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String now = df.format(cal.getTime());

        //DatabaseHandler db = new DatabaseHandler(this);
        //db.followChannel(new YouTubeChannel("IGN","UCKy1dAqELo0zrOtPkf0eTMw", now));
        //db.followChannel(new YouTubeChannel("CNN", "UCupvZG-5ko_eiXAupbDfxWw", now));
        //db.followChannel(new YouTubeChannel("BUZZFEED", "UCpko_-a4wgz2u_DgDgd9fqA", now));
        //db.followChannel(new YouTubeChannel("COLLEGEHUMOR", "UCPDXXXJj9nax0fr0Wfc048g", now));
        //db.followChannel(new YouTubeChannel("/DRIVE", "UC5rBpVgv83gYPZ593XwQUsA", now));





        //last_updated = last_updated.substring(0,10)+"T"+last_updated.substring(10,12)+"%3A"+last_updated.substring(13,15)+"%3A"+last_updated.substring(16,18)+"Z";
        //date.setText(now);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        //gridView.setMinimumWidth(gridView.getWidth() + gridView.getPaddingLeft() + gridView.getPaddingRight());
        final grid_adapter_that_populates_home_page ld = new grid_adapter_that_populates_home_page(this);
        gridView.setAdapter(ld);




/*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                if (mSelectMode) {
                    ld.toggleSelected(position);
                } else {
                    Toast.makeText(MainActivity.this, "Clicked item: " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                mSelectMode = true;
                invalidateOptionsMenu();
                ld.toggleSelected(position);
                return true;
            }
        });

        */
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





