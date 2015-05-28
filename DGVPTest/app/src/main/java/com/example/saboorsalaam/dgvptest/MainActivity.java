package com.example.saboorsalaam.dgvptest;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saboorsalaam.dgvptest.DatabaseHandler.DatabaseHandler;
import com.example.saboorsalaam.dgvptest.ParseClasses.Channel;
import com.example.saboorsalaam.dgvptest.ParseClasses.ParseDBCommunicator;
import com.example.saboorsalaam.dgvptest.ParseClasses.Video;

import java.nio.channels.Channels;
import java.util.List;


public class MainActivity extends Activity {

    private static final String TAG = "DraggableGridViewPagerTestActivity";

    private DraggableGridViewPager mDraggableGridViewPager;
    private Button mAddButton;
    private Button mRemoveButton;
    private GridviewLoader gridviewLoader;

    private DraggableGridAdapter mAdapter;

    private int mGridCount;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        mDraggableGridViewPager = (DraggableGridViewPager) findViewById(R.id.draggable_grid_view_pager);


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

        ParseDBCommunicator parseDBCommunicator = new ParseDBCommunicator(this);
        gridviewLoader = new GridviewLoader();
        gridviewLoader.execute();


        //mAdapter = new DraggableGridAdapter(channels, this, 2, height/3);
       // mDraggableGridViewPager.setAdapter(mAdapter);
        mDraggableGridViewPager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v(TAG, "onPageScrolled position=" + position + ", positionOffset=" + positionOffset
                        + ", positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected position=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged state=" + state);
            }
        });
        mDraggableGridViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString());
            }
        });
        mDraggableGridViewPager.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString() + " long clicked!!!");
                return true;
            }
        });

        mDraggableGridViewPager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                Object channel = mAdapter.getItems().get(oldIndex);
                mAdapter.remove(channel);
                mAdapter.add(newIndex,channel);
                mAdapter.notifyDataSetChanged();
                databaseHandler.saveOrder(mAdapter.getItems());
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    public class GridviewLoader extends AsyncTask<Void, Void, Void> {

        final static int NO_NETWORK_CONNECTION = 0;
        final static int CANNOT_CONNECT_TO_PARSE = 1;
        int error_code = 0;
        boolean taskcancelled;

        @Override
        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
//            swipeLayout.setVisibility(View.GONE);
            //draggableGridViewPager.setVisibility(View.GONE);
            //error_container.setVisibility(View.GONE);
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
                    //GridviewLoader.setTaskcancelled(true);
                    cancel(true);
                }

                @Override
                public void onConnectionSuccessful() {
                    //GridviewLoader.setTaskcancelled(false);
                }

                @Override
                public void onCannotConnectToParse() {
                    //Toast.makeText(getApplicationContext(), R.string.no_connection_to_parse, Toast.LENGTH_LONG).show();
                    error_code = CANNOT_CONNECT_TO_PARSE;
                    Log.d("Network Error", "Cannot connect");
                    //GridviewLoader.setTaskcancelled(true);
                    cancel(true);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

            mAdapter = new DraggableGridAdapter(channels, MainActivity.this, 2, height/3);
            mDraggableGridViewPager.setAdapter(mAdapter);
            mDraggableGridViewPager.setVisibility(View.VISIBLE);
            //progressBar.setVisibility(View.GONE);
            //error_container.setVisibility(View.GONE);
//                 swipeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onCancelled() {
            Log.d("Network Error", "Post Cancelled");
           // error_container.setVisibility(View.VISIBLE);
            //progressBar.setVisibility(View.GONE);

            //if(error_code == NO_NETWORK_CONNECTION){Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_SHORT).show();}
            //else if(error_code == CANNOT_CONNECT_TO_PARSE){Toast.makeText(getApplicationContext(), R.string.no_connection_to_parse, Toast.LENGTH_SHORT).show();}
        }

        public boolean isTaskcancelled() {
            return taskcancelled;
        }

        public void setTaskcancelled(boolean taskcancelled) {
            this.taskcancelled = taskcancelled;
        }


    }

}