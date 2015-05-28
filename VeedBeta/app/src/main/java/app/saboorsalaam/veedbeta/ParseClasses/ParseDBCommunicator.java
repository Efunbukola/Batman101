package app.saboorsalaam.veedbeta.ParseClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 6/6/2014.
 */

public class ParseDBCommunicator {

    private Context context;
    static DefaultHttpClient httpClient;




    public ParseDBCommunicator(Context c) {
        this.context = c;
        Parse.initialize(context, "4WShWGs2N5eF0WL3qBj3Gbqm61JyY3tPzSzVU6Q0", "TLe2dAXt8RUiYA1IwUmnm2He2DO0j12qq7bx02lu");
    }


    public List<Channel> getChannelsWithVideos(final connectionFailedListener cfl)
    {
        List<Channel> channels = new ArrayList<Channel>();
        objects = new ArrayList<ParseObject>();
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Channel"); // get all channels that mach that category

        if (haveNetworkConnection()) {   //If we can get a network connection

            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        objects = query.find();
                    } catch (ParseException e) {
                        cfl.onCannotConnectToParse();
                        e.printStackTrace();
                    }

                }
            });
            client.start();
            //wait for background thread to finish
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Log.d("Channel", "Retrieved " + objects.size() + " channels");
            for (int z = 0; z < objects.size(); z++) {
                channels.add(new Channel(objects.get(z).getString("channel_name"), objects.get(z).getString("channel_id"), objects.get(z).getString("cover_thumbnail"), objects.get(z).getString("cover_thumbnail2"),objects.get(z).getString("cover_title"),objects.get(z).getString("cover2_title"),objects.get(z).getInt("newVideos")));
            }
        }else{
            cfl.onConnectionFailed();
        }

        return channels;
    }

    ParseQuery<ParseObject> mainQuery; //Global Declarations***************
    List<ParseObject> objects; //Global Declarations***********************



    public List<Channel> getSelectedChannels(List<String> ids, final connectionFailedListener cfl) //********************************
    {
        /*List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

        for(int i = 0; i < ids.size(); i++ )
        {
            ParseQuery<ParseObject> matchId = ParseQuery.getQuery("Channel");
            matchId.whereEqualTo("channel_id", ids.get(i));
            queries.add(matchId);
        }


        if(queries != null) {
            mainQuery = ParseQuery.or(queries);// OR Each individual query
        }
        */

        final ParseQuery<ParseObject> matchId = ParseQuery.getQuery("Channel");
        matchId.whereContainedIn("channel_id", ids);


        objects = new ArrayList<ParseObject>();
        List<Channel> channels = new ArrayList<Channel>();

        List<Channel> orderedChannels = new ArrayList<Channel>();

        if (haveNetworkConnection()) {   //If we can get a network connection

            Thread client = new Thread(new Runnable() {
                public void run() {
                    //mChannels = parseDBCommunicator.getChannelsWithVideos();
                    //for(int i = 0; i < mChannels.size(); i++)
                    //{
                    //    db.addChannel(mChannels.get(i).getChannel_id());
                    // }
                    try {
                        objects = matchId.find();
                    } catch (ParseException e) {
                        cfl.onCannotConnectToParse();
                        e.printStackTrace();
                    }

                }
            });
            client.start();
            //wait for background thread to finish
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("Channel", "Retrieved " + objects.size() + " channels"); //Add channels to list!!
            for (int z = 0; z < objects.size(); z++) {
                channels.add(new Channel(objects.get(z).getString("channel_name"), objects.get(z).getString("channel_id"), objects.get(z).getString("cover_thumbnail"), objects.get(z).getString("cover_thumbnail2"),objects.get(z).getString("cover_title"),objects.get(z).getString("cover2_title"),objects.get(z).getInt("newVideos")));
            }

            boolean notDone = true;

            for (int z = 0; z < ids.size(); z++) {  //Reorder Channels
                for (int i = 0; i < channels.size() && notDone; i++) {
                    //Log.d("Reorderer", "Found Match Between " + ids.get(z) + " and " + channels.get(i).getChannel_name() + ", " + channels.get(i).getChannel_id());
                    if (ids.get(z).equals(channels.get(i).getChannel_id())) {
                        Log.d("Reorderer", "Found Match Between " + ids.get(z) + " and " + channels.get(i).getChannel_name() + ", " + channels.get(i).getChannel_id());
                        Channel ch = channels.get(i);
                        orderedChannels.add(ch);
                        notDone = false;
                    }
                }
                notDone = true;
            }
            cfl.onConnectionSuccessful(); //Connection is successful
        } else {
            cfl.onConnectionFailed(); // Connection failed
        }

        return orderedChannels;
    }




    public List<Video> getVideosofAChannel(String channel_id, String channel_title, final connectionFailedListener cfl)
    {
        List<Video> videos = new ArrayList<Video>();
        objects = new ArrayList<ParseObject>();
        query = ParseQuery.getQuery("Video"); // get all channels that mach that category
        query.whereEqualTo("channel_id", channel_id);

        if (haveNetworkConnection()) {   //If we can get a network connection

            Thread client = new Thread(new Runnable() {
                public void run() {

                    try {
                        objects = query.find();
                    } catch (ParseException e) {
                        cfl.onCannotConnectToParse();
                        e.printStackTrace();
                    }

                }
            });
            client.start();
            //wait for background thread to finish
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("Videos", "Retrieved " + objects.size() + " videos");


            for (int z = 0; z < objects.size(); z++) {
                videos.add(new Video(objects.get(z).getString("video_id"), objects.get(z).getString("date_published"), objects.get(z).getString("video_title"), objects.get(z).getString("video_description"), objects.get(z).getString("video_thumbnail"), channel_title, objects.get(z).getString("video_type") ));
            }
            cfl.onConnectionSuccessful();
        } else {
            cfl.onConnectionFailed();
        }

        return videos;
    }


    ParseQuery<ParseObject> query; //*********************************************************


    public List<Category> getAllCategories(final connectionFailedListener cfl){

        List<Category> categories = new ArrayList<Category>();
        objects = new ArrayList<ParseObject>();
        query = ParseQuery.getQuery("Category");//get all categories from database

        if(haveNetworkConnection()) {
            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        objects = query.find();
                    } catch (ParseException e) {
                        cfl.onCannotConnectToParse();
                        e.printStackTrace();
                    }
                }
            });
            client.start();
            //wait for background thread to finish
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < objects.size(); i++)//loop through each category
            {
                //int size = getAllChannelsOfACategory(objects.get(i).getString("name")).size();
                categories.add(new Category(objects.get(i).getString("name"), 10)); // add name to list of headers
                Log.d("Category", "Retrieved " + objects.get(i).getString("name"));
            }
            cfl.onConnectionSuccessful();
        }else{
            cfl.onConnectionFailed();
        }

        return categories;
    }

    public List<Channel> getAllChannelsOfACategory(String category, final connectionFailedListener cfl) {
        List<Channel> channels = new ArrayList<Channel>();
        query = ParseQuery.getQuery("Channel"); // get all channels that mach that category
        query.whereEqualTo("category", category);

        if(haveNetworkConnection())
        {
            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        objects = query.find();
                    } catch (ParseException e) {
                        cfl.onCannotConnectToParse();
                        e.printStackTrace();
                    }
                }
            });
            client.start();
            //wait for background thread to finish
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Log.d("Channel", "Retrieved " + objects.size() + " channels");
            for (int z = 0; z < objects.size(); z++) {
                channels.add(new Channel(objects.get(z).getString("channel_name"), objects.get(z).getString("channel_id"), objects.get(z).getString("cover_thumbnail"), objects.get(z).getString("cover_thumbnail2"),objects.get(z).getString("cover_title"),objects.get(z).getString("cover2_title"),objects.get(z).getInt("newVideos")));
            }
            cfl.onConnectionSuccessful();
        }else {
            cfl.onConnectionFailed();
        }
        return channels;
    }

    public interface connectionFailedListener {

        void onConnectionFailed();
        void onConnectionSuccessful();
        void onCannotConnectToParse();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
