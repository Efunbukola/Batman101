package Extras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.alamkanak.weekview.WeekViewEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ParseDBCommunicator.Event;

/**
 * Created by Saboor Salaam on 7/23/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "user_schedule";

    // Channel table name
    private static final String TABLE_EVENTS = "events";
    final static String EVENT_STRING = "event";
    final static String JOB_STRING = "job";

    // Channel Table Columns names
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_DESC = "event_description";
    private static final String EVENT_DATE = "event_date";
    private static final String EVENT_LOCATION = "event_location";
    private Context mContext;


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + EVENT_NAME + " TEXT, " +
                EVENT_DESC + " TEXT, " +
                EVENT_DATE + " TEXT, " +
                EVENT_LOCATION + " TEXT )";
        db.execSQL(CREATE_EVENTS_TABLE);
    }


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }


    public void saveEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, event.getName());
        values.put(EVENT_DESC, event.getDescription());
        values.put(EVENT_DATE, event.getDate());
        values.put(EVENT_LOCATION, event.getLocation());
        db.insert(TABLE_EVENTS, null, values);
        db.close(); // Closing database connection
    }

    public List<WeekViewEvent> getEventsByYearAndMonth(int year, int month) {

        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        df.setTimeZone(TimeZone.getTimeZone("EST"));

        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Date date = null;
                try {
                    date = df.parse(cursor.getString(2));
                } catch (ParseException e) {
                    e.printStackTrace();
                    try {
                        date = df.parse("2015-04-05 10:10 AM ");
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }

                Calendar startTime = Calendar.getInstance();
                startTime.setTime(date);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.add(Calendar.HOUR, 1);
                int event_year = startTime.get(Calendar.YEAR);
                int event_month = startTime.get(Calendar.MONTH);

                if (year == event_year && month == event_month) {
                   events.add(new WeekViewEvent(1, cursor.getString(0) + " " + cursor.getString(1), startTime, endTime));
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    List<Event> getEvents(){
        List<Event> events = new ArrayList<Event>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        df.setTimeZone(TimeZone.getTimeZone("EST"));

        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                    events.add(new Event(cursor.getString(0),"null",cursor.getString(1), cursor.getString(2),cursor.getString(3),EVENT_STRING));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

}