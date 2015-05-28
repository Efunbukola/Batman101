package Extras;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ParseDBCommunicator.Event;
import ParseDBCommunicator.Job;
import ParseDBCommunicator.ParseDBCommunicator;
import app.cedar.cedarconnect.LogInActivity;
import app.cedar.cedarconnect.R;

/**
 * Created by Saboor Salaam on 3/22/2015.
 */
public class HomeUserEventsFragment extends Fragment {

    ParseDBCommunicator parseDBCommunicator;
    MainListAdapter mainListAdapter;
    List<Event> events;
    List<Job> jobs;
    ListView listView;
    ImageView progressBar;
    ParseUser currentUser;
    public static final int TYPE_EVENT = 0;
    private static final int TYPE_JOB = 1;
    private static final int TYPE_ERROR = 2;
    private static final int TYPE_VERIFY_BLURB = 3;
    private static final int TYPE_BLURB = 4;


    String title, major, description, date, location, company, deadline_date, type;


    public HomeUserEventsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.home_main_list_fragment_layout, container, false);


        listView = (ListView) rootView.findViewById(R.id.listView);
        progressBar = (ImageView) rootView.findViewById(R.id.progressBar);

        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "rotationY", 0.0f, 360f);
        animation.setDuration(500);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();


        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<Event> events = databaseHandler.getEvents();
        mainListAdapter = new MainListAdapter(events,new ArrayList<Job>(), getActivity());
        listView.setAdapter(mainListAdapter);

        listView.setAdapter(mainListAdapter);
        mainListAdapter.setListView(listView);
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mainListAdapter.getItemViewType(position) == TYPE_EVENT ) {
                    MainListDialogFragment mainListDialogFragment = new MainListDialogFragment();


                    Field field = null;

                    //name
                    try {
                        field = mainListAdapter.getItem(position).getClass().getField("name");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    title = "error";

                    try {
                        title = (String) field.get(mainListAdapter.getItem(position));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    //major
                    try {
                        field = mainListAdapter.getItem(position).getClass().getField("major");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    major = "error";

                    try {
                        major = (String) field.get(mainListAdapter.getItem(position));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    //description
                    try {
                        field = mainListAdapter.getItem(position).getClass().getField("description");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    description = "error";

                    try {
                        description = (String) field.get(mainListAdapter.getItem(position));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    //date
                    try {
                        field = mainListAdapter.getItem(position).getClass().getField("date");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    date = "error";

                    try {
                        date = (String) field.get(mainListAdapter.getItem(position));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    //location
                    try {
                        field = mainListAdapter.getItem(position).getClass().getField("location");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    location = "error";

                    try {
                        location = (String) field.get(mainListAdapter.getItem(position));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
                    df.setTimeZone(TimeZone.getTimeZone("EST"));

                    Date from = null;
                    try {
                        from = df.parse(df.format(new Date()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date to = null;
                    try {
                        Log.d("Parse event date", "Date: " + date);
                        to = df.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                    DateCalculator dateCalculator = new DateCalculator();
                    TimeSpan ts = dateCalculator.difference(to, from);


                    //mainListDialogFragment.instantiate(title,description, company, timeUntil(ts), location);
                    //mainListDialogFragment.show(getFragmentManager(), "main_list_dialog");
                    //mainListDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
                }
            }
        });
        return  rootView;
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

                double time_until_event1 = dateCalculator.difference(to, from).months * 1000 + dateCalculator.difference(to, from).days * 100 + dateCalculator.difference(to, from).hours * 10 + dateCalculator.difference(to, from).minutes * .1;
                double time_until_event2 = dateCalculator.difference(to2, from).months * 1000 + dateCalculator.difference(to2, from).days * 100 + dateCalculator.difference(to2, from).hours * 10 + dateCalculator.difference(to2, from).minutes * .1;


                return Double.valueOf(time_until_event1).compareTo(time_until_event2);
            }
        });
        return events;
    }



    public class ListViewLoader extends AsyncTask<Void, Void, Void> {

        final static int NO_NETWORK_CONNECTION = 0;
        final static int CANNOT_CONNECT_TO_PARSE = 1;
        int error_code = 0;
        boolean taskcancelled;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            //error_container.setVisibility(View.GONE);
            boolean taskcancelled = false;
        }

        @Override
        protected Void doInBackground(Void... params) {

            parseDBCommunicator = new ParseDBCommunicator(getActivity());

            currentUser = ParseUser.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(getActivity(), "Error logging in! Try again", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
            }

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



            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            listView.setAdapter(mainListAdapter);
            mainListAdapter.setListView(listView);
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);


            //error_container.setVisibility(View.GONE);
        }

        @Override
        protected void onCancelled() {
            Log.d("Network Error", "Post Cancelled");
            //error_container.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            if(error_code == NO_NETWORK_CONNECTION){Toast.makeText(getActivity(), R.string.no_network, Toast.LENGTH_SHORT).show();}
            else if(error_code == CANNOT_CONNECT_TO_PARSE){Toast.makeText(getActivity(), R.string.no_connection_to_parse, Toast.LENGTH_SHORT).show();}
        }

        public boolean isTaskcancelled() {
            return taskcancelled;
        }

        public void setTaskcancelled(boolean taskcancelled) {
            this.taskcancelled = taskcancelled;
        }
    }

}

