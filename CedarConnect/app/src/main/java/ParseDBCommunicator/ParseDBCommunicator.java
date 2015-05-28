package ParseDBCommunicator;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import Extras.Alert;
import Extras.AlertHandler;
import Extras.AlertUtils;

/**
 * Created by Saboor Salaam on 6/6/2014.
 */

public class ParseDBCommunicator {

    private Context context;
    final static String EVENT_STRING = "event";
    final static String JOB_STRING = "job";




    public ParseDBCommunicator(Context c) {
        this.context = c;
        Parse.initialize(context, "tvUQBsqRrTFS6ozB4SljcJsj5vNci3uFdkqpEc5B", "gwyvAG08S3hsIre4YXDFRzKVFzgK1j6P1wJ2Ku4C");
    }

    public void Initialize()
    {
        Parse.initialize(context, "tvUQBsqRrTFS6ozB4SljcJsj5vNci3uFdkqpEc5B", "gwyvAG08S3hsIre4YXDFRzKVFzgK1j6P1wJ2Ku4C");
    }





    ParseQuery<ParseObject> mainQuery; //Global Declarations***************
    List<ParseObject> objects; //Global Declarations***********************

    public List<Job> getJobMatches(Number gpa, String major, final connectionFailedListener cfl) //********************************
    {
            final ParseQuery<ParseObject> query = ParseQuery.getQuery("Job");
            query.whereEqualTo("target_major", major);
            query.whereGreaterThanOrEqualTo("gpa_req", gpa.floatValue());


        List<Job> jobs = new ArrayList<Job>();
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

        Log.d("Jobs", "Retrieved " + objects.size() + " jobs"); //Add channels to list!!
        for (int z = 0; z < objects.size(); z++) {
            jobs.add(new Job(objects.get(z).getString("name"),objects.get(z).getString("description"),objects.get(z).getString("major"),objects.get(z).getString("company"),objects.get(z).getString("date"), objects.get(z).getNumber("gpa_req"),objects.get(z).getNumber("salary"),JOB_STRING));
            Log.d("Jobs added", z + ". " + jobs.get(z).getName() + "  date: " +jobs.get(z).getDate() + " type: " + jobs.get(z).getType());

        }
        cfl.onConnectionSuccessful(); //Connection is successful
    } else {
        cfl.onConnectionFailed(); // Connection failed
    }

    return jobs;
}

    List<ParseUser> returned_users;
    ParseQuery<ParseUser> users_query;

    public List<ParseUser> getUser(String id, final connectionFailedListener cfl)
    {
        users_query = ParseUser.getQuery();
        users_query.whereEqualTo("username", id);

        if (haveNetworkConnection()) {   //If we can get a network connection
            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        returned_users = users_query.find();
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
            cfl.onConnectionSuccessful(); //Connection is successful
        } else {
            cfl.onConnectionFailed(); // Connection failed
        }

        return returned_users;
    }

    List<ParseObject> objects2;
    List<Event> events;




    public List<Event> getEventMatches(String major, final connectionFailedListener cfl) //********************************
    {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Workshops");
        query.whereEqualTo("target_major", major);

        final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Information_Sessions");
        query.whereEqualTo("target_major", major);



        final List<Event> events = new ArrayList<Event>();
        if (haveNetworkConnection()) {   //If we can get a network connection

            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        objects = query.find();
                        objects2 = query2.find();

                        if(objects != null) {

                            for (int i = 0; i < objects.size(); i++) {
                                Event newevent = new Event();
                                newevent.setDate(objects.get(i).getString("date"));
                                newevent.setName(objects.get(i).getString("name"));
                                newevent.setDescription(objects.get(i).getString("description"));
                                newevent.setLocation(objects.get(i).getString("location"));
                                newevent.setMajor(objects.get(i).getString("major"));
                                newevent.setType(EVENT_STRING);
                                events.add(newevent);
                                    Log.d("Events added", i + ". " + events.get(i).getName() + "  date: " +events.get(i).getDate() + " type: " + events.get(i).getType());


                            }
                        }
                        if(objects2 != null) {

                            for (int i = 0; i < objects2.size(); i++) {

                                Event newevent = new Event();
                                newevent.setDate(objects2.get(i).getString("date"));
                                newevent.setName(objects2.get(i).getString("name"));
                                newevent.setDescription(objects2.get(i).getString("description"));
                                newevent.setLocation(objects2.get(i).getString("location"));
                                newevent.setMajor(objects2.get(i).getString("major"));
                                newevent.setType(EVENT_STRING);
                                events.add(newevent);

                                Log.d("Events added", i + ". " + events.get(i).getName() + "  date: " +events.get(i).getDate() + " type: " + events.get(i).getType());
                            }
                        }
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
            cfl.onConnectionSuccessful(); //Connection is successful
        } else {
            cfl.onConnectionFailed(); // Connection failed
        }
        return events;
    }

    public List<Alert> getAlerts() //********************************
    {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Alerts");
        List<Alert> alerts = new ArrayList<Alert>();
            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        objects = query.find();
                    } catch (ParseException e) {
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

            Log.d("Alerts", "Retrieved " + objects.size() + " alerts"); //Add channels to list!!
            for (int z = 0; z < objects.size(); z++) {
                alerts.add(new Alert(objects.get(z).getString("title"),objects.get(z).getString("body"), objects.get(z).getString("ok_message"),objects.get(z).getBoolean("isThereOtherMessage"),objects.get(z).getString("other_message"), objects.get(z).getString("alert_id"), objects.get(z).getInt("alert_type")));
            }

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String EMAIL_VERIFIED = "email_verified";
            if(!preferences.getBoolean(EMAIL_VERIFIED, false))
            {
                alerts.add(0, new Alert("Dont Forget!", "Verify your Howard email address to access full Cedar Connect features!", "Got it", true, "Verify now", "999", AlertUtils.SYSTEM_ALERT));
            }
        return alerts;
    }


    ParseQuery<ParseObject> query; //*********************************************************

    public void UpdatePassword(String id, final String newpassword, final connectionFailedListener cfl){

        final ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", id);

        if(haveNetworkConnection()) {
            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        returned_users = query.find();
                        if(returned_users.size() > 0) {
                            logIn(returned_users.get(0).getUsername(), "1234", new connectionFailedListener() {
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
                            ParseUser po = returned_users.get(0);
                            po.put("password", newpassword);
                            po.save();
                        }

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

            cfl.onConnectionSuccessful();
        }else{
            cfl.onConnectionFailed();
        }
    }
    ParseUser parseUser;
    public ParseUser logIn(final String username, final String password, final connectionFailedListener cfl){

        parseUser = null;
        if(haveNetworkConnection()) {
            Thread client = new Thread(new Runnable() {
                public void run() {
                    try {
                        parseUser = ParseUser.logIn(username, password);
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

            cfl.onConnectionSuccessful();
        }else{
            cfl.onConnectionFailed();
        }
    return parseUser;
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
