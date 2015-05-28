package Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saboorsalaam.veed10.InsideChannelActivity;
import com.example.saboorsalaam.veed10.R;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import DatabaseHandler.DatabaseHandler;
import DraggableGridViewPager.DraggableGridViewPager;
import DraggableGridViewPager.DraggableGridAdapter;
import ParseClasses.Channel;

/**
 * Created by Saboor Salaam on 4/27/2015.
 */
public class MyChannelsFragment extends Fragment {

    private static final String TAG = "MGF";
    public static final int TYPE_CHANNEL = 0;
    private static final int TYPE_FOOTER = 1;
    int width, height;
    DatabaseHandler databaseHandler;
    DraggableGridAdapter draggableGridAdapter;
    DraggableGridViewPager draggableGridViewPager;
    ProgressBar progressBar;
    GridViewLoader gridViewLoader;
    LinearLayout error_container, overlay;
    RelativeLayout video_actionbar;
    TextView try_agian_button;
    List<Channel> channels;

    public void initialize(int width, int height){this.width = width; this.height = height;};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_my_channels, container, false);

        databaseHandler = new DatabaseHandler(getActivity());
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        draggableGridViewPager = (DraggableGridViewPager) rootView.findViewById(R.id.dynamic_grid_view);
        error_container = (LinearLayout) rootView.findViewById(R.id.error_container);
        try_agian_button = (TextView) rootView.findViewById(R.id.try_again_button);
        channels = new ArrayList<Channel>();
        gridViewLoader = new GridViewLoader();


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

        gridViewLoader.execute();

        try_agian_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //error_container.setVisibility(View.GONE);
                new GridViewLoader().execute();
            }
        });

        draggableGridViewPager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (draggableGridAdapter.getItemViewType(position)) {
                    case TYPE_CHANNEL:
                        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        Field a = null;
                        try {
                            a = draggableGridAdapter.getItem(position).getClass().getField("channel_id");
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        String _id = "error";

                        try {
                            _id = (String) a.get(draggableGridAdapter.getItem(position));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        Field b = null;
                        try {
                            b = draggableGridAdapter.getItem(position).getClass().getField("channel_name");
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        String name = "error";
                        try {
                            name = (String) b.get(draggableGridAdapter.getItem(position));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                        Log.d("Channel:", "Channel loaded: " + name + ", " + id);
                        Intent intent = new Intent(getActivity(), InsideChannelActivity.class);
                        intent.putExtra("channel_id", _id);
                        intent.putExtra("channel_name", name);
                        startActivity(intent);
                        break;

                    case TYPE_FOOTER:
                }
            }
        });

        draggableGridViewPager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //swipeLayout.setEnabled(false);
                //dynamicGridView.startEditMode();
                //view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });


        draggableGridViewPager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                Object channel = draggableGridAdapter.getItem(oldIndex);
                draggableGridAdapter.remove(channel);
                draggableGridAdapter.add(newIndex,channel);
                draggableGridAdapter.notifyDataSetChanged();
                databaseHandler.saveOrder(draggableGridAdapter.getItems());

            }
        });

        draggableGridViewPager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
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



        return rootView;
    }


    public class GridViewLoader extends AsyncTask<Void, Void, Void> {

        final static int NO_NETWORK_CONNECTION = 0;
        final static int CANNOT_CONNECT_TO_PARSE = 1;
        int error_code = 0;
        boolean taskcancelled;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
//            swipeLayout.setVisibility(View.GONE);
            draggableGridViewPager.setVisibility(View.GONE);
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

                    cancel(true);
                }

                @Override
                public void onConnectionSuccessful() {
                }

                @Override
                public void onCannotConnectToParse() {
                    //Toast.makeText(getApplicationContext(), R.string.no_connection_to_parse, Toast.LENGTH_LONG).show();
                    error_code = CANNOT_CONNECT_TO_PARSE;
                    Log.d("Network Error", "Cannot connect");
                    cancel(true);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

            draggableGridAdapter = new DraggableGridAdapter(channels, getActivity(), 2, height / 3);
            draggableGridViewPager.setAdapter(draggableGridAdapter);
            draggableGridViewPager.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            error_container.setVisibility(View.GONE);
//                 swipeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onCancelled() {
            Log.d("Network Error", "Post Cancelled");
            error_container.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            if (error_code == NO_NETWORK_CONNECTION) {
                Toast.makeText(getActivity(), R.string.no_network, Toast.LENGTH_SHORT).show();
            } else if (error_code == CANNOT_CONNECT_TO_PARSE) {
                Toast.makeText(getActivity(), R.string.no_connection_to_parse, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
