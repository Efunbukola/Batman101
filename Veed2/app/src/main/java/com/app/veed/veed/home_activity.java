package com.app.veed.veed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.veed.veed.Adapters.ChannelViewPagerAdapter;
import com.app.veed.veed.DynamicGridView.DynamicGridAdapter;
import com.app.veed.veed.CustomSwipeToRefresh.CustomSwipeToRefresh;
import com.app.veed.veed.DatabaseHandler.DatabaseHandler;
import com.app.veed.veed.DynamicGridView.DynamicGridView;
import com.app.veed.veed.Fragments.dialog_fragment;
import com.app.veed.veed.ParseClasses.Channel;
import com.app.veed.veed.ParseClasses.ParseDBCommunicator;
import com.app.veed.veed.ParseClasses.Video;

import java.util.ArrayList;
import java.util.List;


public class home_activity extends FragmentActivity {

    //Set tittle out side click listener


    ChannelViewPagerAdapter channelViewPagerAdapter;
    DynamicGridAdapter dynamicGridAdapter;
    DynamicGridView dynamicGridView;
    ParseDBCommunicator parseDBCommunicator;
    ProgressBar progressBar;
    CustomSwipeToRefresh swipeLayout;
    DynamicGridviewLoader dynamicGridviewLoader;
    LinearLayout error_container, overlay;
    RelativeLayout video_actionbar;
    TextView try_agian_button;
    MediaPlayer open, close;

    ArrayAdapter<String> mAdapter;


    List<Channel> channels;
    List<Video> videos;
    private int mPhotoSize, mPhotoSpacing;

    private int draggedIndex = -1;
    PointF start = new PointF();
    PointF mid = new PointF();
    int width, height, I;
    DatabaseHandler databaseHandler;
    Display display;
    Boolean new_channels_is_null, video_actionbar_animated_already;
    String id, name, _id, _name;

    public static final int TYPE_CHANNEL = 0;
    private static final int TYPE_FOOTER= 1;
    private static final int ACTION_BAR_SHOWN = 1;
    private static final int ACTION_BAR_HIDDEN = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_activity);

            open = MediaPlayer.create(getApplicationContext(), R.raw.open);
            close = MediaPlayer.create(getApplicationContext(), R.raw.close_slide_up_menu);


            display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
            video_actionbar_animated_already = false;
            _name = "Error...";

            parseDBCommunicator = new ParseDBCommunicator(this);


            //swipeLayout = (CustomSwipeToRefresh) findViewById(R.id.swipe_container);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            dynamicGridView = (DynamicGridView) findViewById(R.id.dynamic_grid_view);
            error_container = (LinearLayout) findViewById(R.id.error_container);
            try_agian_button = (TextView) findViewById(R.id.try_again_button);
            channels = new ArrayList<Channel>();
            dynamicGridviewLoader = new DynamicGridviewLoader();
            video_actionbar = (RelativeLayout) findViewById(R.id.video_action_bar);

            overlay = (LinearLayout) findViewById(R.id.overlay);

            channelViewPagerAdapter = new ChannelViewPagerAdapter(getSupportFragmentManager());

            Display display = getWindowManager().getDefaultDisplay();
            Point sz = new Point();
            display.getSize(sz);
            int width = sz.x;
            int height = sz.y;


/*
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { //When user swipes down to refresh home page
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("Current page: ", draggableGridViewPager.getCurrentItem() + "");
                            final int current_item = draggableGridViewPager.getCurrentItem();

                            new_channels_is_null = true;

                            //Attempt to reload channels from database and parse
                            channels = databaseHandler.getAllLocalChannels(new DatabaseHandler.connectionFailedListener() {
                                @Override
                                public void onConnectionFailed() {
                                    Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onConnectionSuccessful() {
                                    new_channels_is_null = false;
                                }

                                @Override
                                public void onCannotConnectToParse() {
                                    Toast.makeText(getApplicationContext(), R.string.no_connection_to_parse, Toast.LENGTH_SHORT).show();

                                }
                            });
                            boolean isDataSetNew = movableGridAdapter.isDataSetNew(channels); //if data is new data
                            if (isDataSetNew && !new_channels_is_null) //reload the grid
                            {
                                movableGridAdapter.set(channels);
                                /*
                                draggableGridViewPager.setAdapter(movableGridAdapter, new DraggableGridViewPager.adapterSetListener() {
                                    @Override
                                    public void onAdapterSet() {
                                        draggableGridViewPager.setCurrentItem(current_item);
                                    }
                                });
                                */
        /*
                            } else if (!isDataSetNew && !new_channels_is_null)//tell user there's nothing new
                            {
                                Toast.makeText(getApplicationContext(), R.string.sorry_nothing_new, Toast.LENGTH_SHORT).show();

                            }


                            swipeLayout.setRefreshing(false);
                        }
                    }, 1000);

                }
            });
*/

            databaseHandler = new DatabaseHandler(this);


       databaseHandler.addChannel("1341");
        databaseHandler.addChannel("filmschool");
        databaseHandler.addChannel("UC8Su5vZCXWRag13H53zWVwA");
        databaseHandler.addChannel("UC5RwNJQSINkzIazWaM-lM3Q");
        databaseHandler.addChannel("UCjBp_7RuDBUYbd1LegWEJ8g");
        databaseHandler.addChannel("UCg7lal8IC-xPyKfgH4rdUcA");
        databaseHandler.addChannel("UC5RwNJQSINkzIazWaM-lM3Q");
        databaseHandler.addChannel("staffpicks");
        databaseHandler.addChannel("photographyschool");
        databaseHandler.addChannel("everythinganimated");
        databaseHandler.addChannel("UCIHBybdoneVVpaQK7xMz1ww");
        databaseHandler.addChannel("UCbQhVRG7B-LmpTM1lCSrg7A");
        databaseHandler.addChannel("UCuS96jkLKpTaGB_OWnwZV_A");
        databaseHandler.addChannel("UCpko_-a4wgz2u_DgDgd9fqA");
        databaseHandler.addChannel("UCqFMzb-4AUf6WAIbl132QKA");
        databaseHandler.addChannel("UCWJ2lWNubArHWmf3FIHbfcQ");
        databaseHandler.addChannel("UCSZbXT5TLLW_i-5W8FZpFsg");
        databaseHandler.addChannel("UCoLrcjPV5PbUrUyXq5mjc_A");
        databaseHandler.addChannel("UC16niRr50-MSBwiO3YDb3RA");
        databaseHandler.addChannel("UCZaT_X_mc0BI-djXOlfhqWQ");
        databaseHandler.addChannel("UCKy1dAqELo0zrOtPkf0eTMw");
        databaseHandler.addChannel("UC5rBpVgv83gYPZ593XwQUsA");
        databaseHandler.addChannel("UCg7lal8IC-xPyKfgH4rdUcA");
        databaseHandler.addChannel("UCxyCzPY2pjAjrxoSYclpuLg");
        databaseHandler.addChannel("UCPDXXXJj9nax0fr0Wfc048g");
        databaseHandler.addChannel("UCUsN5ZwHx2kILm84-jPDeXw");
        databaseHandler.addChannel("2Y8dQb0S6DtpxNgAKoJKA");
        databaseHandler.addChannel("UCpko_-a4wgz2u_DgDgd9fqA");





        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        I = preferences.getInt("Channel_Position", 999);
        int actionBarState = preferences.getInt("ActionBar_Status",999);

        if(I == 999)
        {
            I = 0;
        }







            dynamicGridviewLoader.execute();

            try_agian_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //error_container.setVisibility(View.GONE);
                    new DynamicGridviewLoader().execute();
                }
            });


            dynamicGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    //swipeLayout.setEnabled(false);
                    dynamicGridView.startEditMode(position);
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                }
            });


            dynamicGridView.setOnDragListener(new DynamicGridView.OnDragListener() {
                @Override
                public void onDragStarted(int position) {

                }

                @Override
                public void onDragPositionsChanged(int oldPosition, int newPosition) {

                    dynamicGridView.stopEditMode();

                }
            });

/*
            draggableGridViewPager.setOnDragEventListener(new DraggableGridViewPager.OnDragEventListener() {
                @Override
                public void onDragStarted() {

                }

                @Override
                public void onDragStopped() {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeLayout.setEnabled(true);
                        }
                    }, 1500);

                }
            });
*/

        /*
            dynamicGridView.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
                @Override
                public void onRearrange(int oldIndex, int newIndex) {
                    Channel channel = movableGridAdapter.getItem(oldIndex);
                    movableGridAdapter.remove(channel);
                    movableGridAdapter.add(newIndex,channel);
                    movableGridAdapter.notifyDataSetChanged();
                    databaseHandler.saveOrder(movableGridAdapter.getItems());

                }
            });
            */


/*
            dynamicGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch (movableGridAdapter.getItemViewType(position)) {
                        case TYPE_CHANNEL:
                            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                           //draggableGridViewPager.setLoading(position);
                            Intent intent = new Intent(getApplicationContext(), inside_channel_activity.class);
                            I = position;
                            intent.putExtra("channel_id", movableGridAdapter.getItem(position).getChannel_id());
                            intent.putExtra("channel_name", movableGridAdapter.getItem(position).getChannel_name());
                            startActivity(intent);
                            break;
                        case TYPE_FOOTER:
                    }
                }
            });
            */




/*
        draggableGridViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int action = event.getAction() & MotionEventCompat.ACTION_MASK;

                int x = Math.round(event.getX());
                int y = Math.round(event.getY());
                for (int i=0; i<draggableGridViewPager.getChildCount(); i++){
                    View child = (View) draggableGridViewPager.getChildAt(i);
                    View shade = (View) child.findViewById(R.id.shade);
                    if(x > child.getLeft() && x < child.getRight() && y > child.getTop() && y < child.getBottom()){
                        //touch is within this child

                        shade.setVisibility(View.VISIBLE);

                        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
                            //touch has ended

                            shade.setVisibility(View.GONE);
                        }
                    }
                }

                return false;
            }
        });
*/
/*
            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final int action = event.getAction() & MotionEventCompat.ACTION_MASK;

                    if (action == MotionEvent.ACTION_MOVE) {
                        //video_actionbar.setAlpha(0f);
                        if (!video_actionbar_animated_already) {
                            ObjectAnimator fadeOut = ObjectAnimator.ofFloat(video_actionbar, "alpha", video_actionbar.getAlpha(), 0f);
                            fadeOut.setDuration(400);
                            fadeOut.start();
                            video_actionbar_animated_already = true;
                        }
                    }

                    if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                        //video_actionbar.setAlpha(1f);

                        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(video_actionbar, "alpha", video_actionbar.getAlpha(), 1f);
                        fadeIn.setDuration(1000);
                        fadeIn.start();
                        video_actionbar_animated_already = false;
                    }

                    return false;
                }
            });
            */

    }

    protected void showTheDialog(String name, String id) {
        dialog_fragment f = new dialog_fragment();
        //f.setStyle();
        f.instantiate(name, id);
        f.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFragmentStyle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.show(f);
        ft.add(f,"dialog_fragment");

        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_activity, menu);
        return true;
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

    @Override
    protected void onPause() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Channel_Position", I);
        editor.apply();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(dynamicGridView.isEditMode())
        {
            dynamicGridView.stopEditMode();
        }

    }

    public class DynamicGridviewLoader extends AsyncTask<Void, Void, Void> {

        final static int NO_NETWORK_CONNECTION = 0;
        final static int CANNOT_CONNECT_TO_PARSE = 1;
        int error_code = 0;
        boolean taskcancelled;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
//            swipeLayout.setVisibility(View.GONE);
            dynamicGridView.setVisibility(View.GONE);
            error_container.setVisibility(View.GONE);
            boolean taskcancelled = false;
        }

        @Override
        protected Void doInBackground(Void... params) {

            channels = databaseHandler.getAllLocalChannels(new DatabaseHandler.connectionFailedListener() {
                @Override
                public void onConnectionFailed() {
                    //Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                    //error_image.setVisibility(View.VISIBLE);
                    error_code = NO_NETWORK_CONNECTION;
                    Log.d("Network Error", "Cannot connect");
                    dynamicGridviewLoader.setTaskcancelled(true);
                    cancel(true);
                }

                @Override
                public void onConnectionSuccessful() {
                    dynamicGridviewLoader.setTaskcancelled(false);
                }

                @Override
                public void onCannotConnectToParse() {
                    //Toast.makeText(getApplicationContext(), R.string.no_connection_to_parse, Toast.LENGTH_LONG).show();
                    error_code = CANNOT_CONNECT_TO_PARSE;
                    Log.d("Network Error", "Cannot connect");
                    dynamicGridviewLoader.setTaskcancelled(true);
                    cancel(true);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

                 dynamicGridAdapter = new DynamicGridAdapter(channels, home_activity.this, 2, height/3);
                 dynamicGridView.setAdapter(dynamicGridAdapter);
                dynamicGridView.setVisibility(View.VISIBLE);
                 progressBar.setVisibility(View.GONE);
                 error_container.setVisibility(View.GONE);
//                 swipeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onCancelled() {
            Log.d("Network Error", "Post Cancelled");
            error_container.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            if(error_code == NO_NETWORK_CONNECTION){Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_SHORT).show();}
            else if(error_code == CANNOT_CONNECT_TO_PARSE){Toast.makeText(getApplicationContext(), R.string.no_connection_to_parse, Toast.LENGTH_SHORT).show();}
        }

        public boolean isTaskcancelled() {
            return taskcancelled;
        }

        public void setTaskcancelled(boolean taskcancelled) {
            this.taskcancelled = taskcancelled;
        }


    }

    private Intent getDefaultShareIntent(Video video){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        if(video.getType().equals("yt")) {
            //intent.putExtra(Intent.EXTRA_SUBJECT, "YouTube");
            intent.putExtra(Intent.EXTRA_TEXT,"Watch \"" +  video.getVideoTitle() + "\" on YouTube:" + "\n https://www.youtube.com/watch?v=" + video.getVideoId());
        }

        if(video.getType().equals("v"))
        {
            //intent.putExtra(Intent.EXTRA_SUBJECT, "Vimeo");
            intent.putExtra(Intent.EXTRA_TEXT, "Watch \"" +  video.getVideoTitle() + "\" on Vimeo:" +
                    "\n https://vimeo.com/" + video.getVideoId());
        }

        return intent;
    }

    public  void invalidateGridViews(int position)
    {
        dynamicGridView.removeView(dynamicGridView.getChildAt(position));
        Log.d("INVALIDATE", "DRAGGABLE GRID INVALIDATED!");
        Log.d("INVALIDATE", "DRAGGABLE GRID INVALIDATED!");
    }

}
