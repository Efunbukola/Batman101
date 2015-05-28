package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saboorsalaam.veed10.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.HomeListViewAdapter;
import DatabaseHandler.DatabaseHandler;
import ObservableScrollView.BaseFragment;
import ObservableScrollView.ObservableScrollViewCallbacks;
import ObservableScrollView.ScrollUtils;
import ParseClasses.Channel;
import ParseClasses.ParseDBCommunicator;
import ParseClasses.Video;

/**
 * Created by Saboor Salaam on 4/27/2015.
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "MGF";
    public static final int TYPE_CHANNEL = 0;
    private static final int TYPE_FOOTER = 1;
    int width, height;
    DatabaseHandler databaseHandler;
    HomeListViewAdapter homeListViewAdapter;
    ObservableScrollView.ObservableListView listView;
    ProgressBar progressBar;
    GridViewLoader gridViewLoader;
    LinearLayout error_container, overlay;
    RelativeLayout video_actionbar;
    TextView try_agian_button;
    List<Channel> channels;
    ParseDBCommunicator parseDBCommunicator;
    Context context;

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);

        parseDBCommunicator = new ParseDBCommunicator(getActivity());
        databaseHandler = new DatabaseHandler(getActivity());
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        error_container = (LinearLayout) rootView.findViewById(R.id.error_container);
        try_agian_button = (TextView) rootView.findViewById(R.id.try_again_button);
        channels = new ArrayList<Channel>();
        gridViewLoader = new GridViewLoader();

        databaseHandler = new DatabaseHandler(getActivity());
        databaseHandler.addChannel("music");
        databaseHandler.addChannel("gaming");
        databaseHandler.addChannel("abstract");
        databaseHandler.addChannel("technology");
        databaseHandler.addChannel("news");
        databaseHandler.addChannel("sports");


        Activity parentActivity = getActivity();
        listView = (ObservableScrollView.ObservableListView) rootView.findViewById(R.id.scroll);
        setHeader(listView, inflater.inflate(R.layout.padding, null));
        gridViewLoader.execute();

        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            // Scroll to the specified position after layout
            Bundle args = getArguments();
            if (args != null && args.containsKey(ARG_INITIAL_POSITION)) {
                final int initialPosition = args.getInt(ARG_INITIAL_POSITION, 0);
                ScrollUtils.addOnGlobalLayoutListener(listView, new Runnable() {
                    @Override
                    public void run() {
                        // scrollTo() doesn't work, should use setSelection()
                        listView.setSelection(initialPosition);
                    }
                });
            }
            listView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
        }


        return rootView;
    }


    public class GridViewLoader extends AsyncTask<Void, Void, Void> {

        final static int NO_NETWORK_CONNECTION = 0;
        final static int CANNOT_CONNECT_TO_PARSE = 1;
        int error_code = 0;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            error_container.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<Video> videos = parseDBCommunicator.getHomeTabVideos(new ParseDBCommunicator.connectionFailedListener() {
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
            for (int z = 0; z < videos.size(); z++) {
                Log.d("Home Fragment", "Added video from " + videos.get(z).getProvider_name() + " of type " + videos.get(z).getType());
            }
            homeListViewAdapter = new HomeListViewAdapter(context, videos);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {

            listView.setAdapter(homeListViewAdapter);
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            error_container.setVisibility(View.GONE);
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
