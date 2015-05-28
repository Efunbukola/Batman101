package Extras;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import app.cedar.cedarconnect.R;

/**
 * Created by Saboor Salaam on 12/3/2014.
 */

public class MainListAdapter extends BaseAdapter {

   Context mContext;
    private List<Object> objects = new ArrayList<>();
    DateCalculator dateCalculator;
    public static final int TYPE_EVENT = 0;
    private static final int TYPE_JOB = 1;
    private static final int TYPE_BLURB = 2;
    private static final int TYPE_VERIFY_EMAIL_BLURB = 3;
    private static final int TYPE_ERROR = 4;
    final static String EVENT_STRING = "event";
    final static String JOB_STRING = "job";
    final static String ERROR_STRING = "error";
    String name, major, description, date, location, company, deadline_date, type;
    Number gpa, salary;
    DatabaseHandler databaseHandler;
    int blurbs = 0;
    int pos = 0;
    boolean verified = false;

    AlertHandler alertHandler;
    ListView listView;



    public void setListView(ListView listView)
    {
      this.listView = listView;
    }


    public MainListAdapter(List<?> events, List<?> jobs, Context mContext) {

        objects.addAll(events);
        objects.addAll(jobs);
        this.objects = new ItemsSorter().sortItems(objects);
        this.mContext = mContext;
        this.databaseHandler = new DatabaseHandler(mContext);
        alertHandler = new AlertHandler(mContext);
        Log.d("Alerts", alertHandler.alerts.size() + " alerts retrieved");
    }

    public int getCount() {
        return objects.size() + alertHandler.alerts.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(position >= alertHandler.alerts.size()) {

            Field field = null;
            try {
                field = getItem(position).getClass().getField("type");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            String type = "error";

            try {
                type = (String) field.get(getItem(position));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            switch (type) {
                case EVENT_STRING:
                    return TYPE_EVENT;
                case JOB_STRING:
                    return TYPE_JOB;
                case "error":
                    return TYPE_ERROR;
                default:
                    return TYPE_ERROR;
            }
        }else{
            if(alertHandler.alerts.get(position).alert_type == AlertUtils.SYSTEM_ALERT)
            {
                return TYPE_VERIFY_EMAIL_BLURB;
            }else
                return  TYPE_BLURB;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    public Object getItem(int position) {
             return objects.get(position - alertHandler.alerts.size());
    }

    public void remove (int position){

        Animation anim = AnimationUtils.loadAnimation(
                mContext, android.R.anim.slide_out_right
        );
        anim.setDuration(500);
        anim.setInterpolator(new JellyBounceInterpolator());


        if(getItemViewType(position) == TYPE_VERIFY_EMAIL_BLURB)
        {
            alertHandler.alerts.remove(0);
        }else if(getItemViewType(position) == TYPE_BLURB) {
            alertHandler.alerts.remove(position);
        }else{
            objects.remove(position - alertHandler.alerts.size());
        }

        listView.getChildAt(position).startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public long getItemId(int position) {
        return position;
    }



    public View getView(final int position, View convertView, final ViewGroup parent) {
        View vi=convertView;


        pos = position;

        switch (getItemViewType(position)) {
            case TYPE_EVENT:
                Field field = null;

                //name
                try {
                    field = getItem(position).getClass().getField("name");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                name = "error";

                try {
                    name = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //major
                try {
                    field = getItem(position).getClass().getField("major");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                major = "error";

                try {
                    major = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //description
                try {
                    field = getItem(position).getClass().getField("description");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                description = "error";

                try {
                    description = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //date
                try {
                    field = getItem(position).getClass().getField("date");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                date = "error";

                try {
                    date = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //location
                try {
                    field = getItem(position).getClass().getField("location");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                location = "error";

                try {
                    location = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if(convertView==null)
                    vi = LayoutInflater.from(mContext).inflate(R.layout.event_list_row, null);

                TextView title_textview = (TextView)vi.findViewById(R.id.title); // title
                TextView desc_textview = (TextView)vi.findViewById(R.id.desc); // artist name
                TextView date_textview = (TextView)vi.findViewById(R.id.date); // duration
                final ImageView rsvp_button = (ImageView)vi.findViewById(R.id.rsvp_button);
                //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

                rsvp_button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

                            Intent calIntent = new Intent(Intent.ACTION_INSERT);
                            calIntent.setType("vnd.android.cursor.item/event");
                            calIntent.putExtra(CalendarContract.Events.TITLE, name);
                            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, description);
                            //events.get(pos).getDate().toString().substring(0, 4); yyyy-MM-dd HH:mm
                            GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(date.toString().substring(0, 3)), Integer.parseInt(date.toString().substring(5, 6)), Integer.parseInt(date.toString().substring(8, 9)));
                        //GregorianCalendar calDate = new GregorianCalendar(2015, 01, 13);

                            calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                                    calDate.getTimeInMillis());
                            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                                    calDate.getTimeInMillis());

                           // mContext.startActivity(calIntent);
                        calIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(calIntent);


                        //databaseHandler.saveEvent(new Event(name, major, description, date,location, type));

                        return false;
                    }
                });

                title_textview.setText(name);
                desc_textview.setText(description);
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



                dateCalculator = new DateCalculator();
                TimeSpan ts = dateCalculator.difference(to, from);
                date_textview.setText(timeUntil(ts));
                break;

            case TYPE_JOB:

                field = null;

                //name
                try {
                    field = getItem(position).getClass().getField("name");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                name = "error";

                try {
                    name = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //major
                try {
                    field = getItem(position).getClass().getField("major");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                major = "error";

                try {
                    major = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //description
                try {
                    field = getItem(position).getClass().getField("description");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                description = "error";

                try {
                    description = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //deadline_date
                try {
                    field = getItem(position).getClass().getField("date");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                deadline_date = "error";

                try {
                    deadline_date = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //company
                try {
                    field = getItem(position).getClass().getField("company");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                company = "error";

                try {
                    company = (String) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                //gpa
                try {
                    field = getItem(position).getClass().getField("gpa");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                gpa = 0.0;

                try {
                    gpa = (Number) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                //salary
                try {
                    field = getItem(position).getClass().getField("salary");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                salary = 0;

                try {
                    salary = (Number) field.get(getItem(position));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if(convertView==null)
                    vi = LayoutInflater.from(mContext).inflate(R.layout.job_list_row, null);

                TextView name_textview = (TextView)vi.findViewById(R.id.title); // title
                TextView description_textview = (TextView)vi.findViewById(R.id.desc); // artist name
                TextView deadline_textview = (TextView)vi.findViewById(R.id.date); // duration
                TextView company_textview = (TextView)vi.findViewById(R.id.company);
                TextView salary_textview = (TextView)vi.findViewById(R.id.salary);

                name_textview.setText(name);
                description_textview.setText(description);
                salary_textview.setText(salary.intValue()/1000 + "k");

                df = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
                df.setTimeZone(TimeZone.getTimeZone("EST"));

               from = null;
                try {

                    from = df.parse(df.format(new Date()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                to = null;
                try {
                    Log.d("Parse job date", "Date: " + deadline_date);
                    to = df.parse(deadline_date);
                } catch (ParseException e) {
                    e.printStackTrace();

                }
                dateCalculator = new DateCalculator();
                ts = dateCalculator.difference(to, from);
                deadline_textview.setText(timeUntilDeadline(ts)+ " to apply");
                company_textview.setText(company);
                break;
            case TYPE_ERROR:
                if(convertView==null)
                    vi = LayoutInflater.from(mContext).inflate(R.layout.error_list_row, null);
                break;
            case TYPE_VERIFY_EMAIL_BLURB:
                Alert alert = alert = alertHandler.alerts.get(position);
                if(convertView==null)
                    vi = LayoutInflater.from(mContext).inflate(R.layout.verify_email_blurb, null);
                Button verify_button = (Button) vi.findViewById(R.id.verify_button);
                verify_button.setVisibility(View.VISIBLE);
                Button ok_button = (Button) vi.findViewById(R.id.dismiss_button);
                ok_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(position);
                    }
                });


                break;
            case TYPE_BLURB:

                Alert alert1 = alertHandler.alerts.get(position);

                if(convertView==null)
                    vi = LayoutInflater.from(mContext).inflate(R.layout.general_list_view_blurb, null);

                TextView title_text_view = (TextView) vi.findViewById(R.id.blurb_tite_text_view);
                TextView body_text_view = (TextView) vi.findViewById(R.id.blurb_body_text_view);
                Button ok_button2 = (Button) vi.findViewById(R.id.dismiss_button);

                title_text_view.setText(alert1.title);
                body_text_view.setText(alert1.body);
                ok_button2.setText(alert1.ok_message);

                ok_button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(position);
                    }
                });

        }



        return vi;
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

    public String timeUntilDeadline(TimeSpan ts)
    {
        String final_string = "";

        if(ts.months > 0){final_string = ts.months + " months";}
        else if(ts.months <= 0 && ts.days > 0) {final_string = ts.days + " days";}
        else if(ts.months <= 0 && ts.days <= 0 && ts.hours > 0) {final_string = ts.hours + " hours";}
        else if(ts.months <= 0 && ts.days <= 0 && ts.hours <= 0) {final_string = "**Deadline Now**";}
        else if(ts.months <= 0 && ts.days <= 0 && ts.hours <= 0 && ts.minutes <= 10) {final_string = "**Deadline Now**";}
        return final_string;
    }


}


