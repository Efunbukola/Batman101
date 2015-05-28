package com.explandable_listview.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Saboor Salaam on 6/17/2014.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "app_database";

    private static final String CHANNEL_NAME = "channel_name";

    private static final String CHANNEL_ID = "channel_id";

    private static final String TABLE_CHANNELS = "Channels";

    private static final String LAST_UPDATED = "last_updated";

    private static final String TYPE = "type_of_video";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHANNELS_TABLE = "CREATE TABLE channels(" +
                "channel_name text, " +
                "last_updated text, " +
                "type_of_video text, " +
                "channel_id text primary key)";
        db.execSQL(CREATE_CHANNELS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS channels");
        // Create tables again
        onCreate(db);
    }

    public void followChannel(YouTubeChannel channel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CHANNEL_NAME, channel.getName()); // Contact Name
        values.put(CHANNEL_ID, channel.getId()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CHANNELS, null, values);
        db.close(); // Closing database connection
    }

    public void unfollowChannel(String channel_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHANNELS, CHANNEL_ID + " = ?",
                new String[]{channel_id});
        db.close();
    }

    public List<YouTubeChannel> getUsersChannels() {
        List<YouTubeChannel> channelList = new ArrayList<YouTubeChannel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHANNELS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()&& cursor != null) {
            do {
                YouTubeChannel channel = new YouTubeChannel();
                channel.setName(cursor.getString(0));
                channel.setId(cursor.getString(1));
                channelList.add(channel);
            } while (cursor.moveToNext());
        }
        return channelList;
    }

   public YouTubeChannel getChannel(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHANNELS, new String[] {CHANNEL_NAME,
                        CHANNEL_ID, LAST_UPDATED}, CHANNEL_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        YouTubeChannel channel = new YouTubeChannel(cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return channel;
    }

    public void updateChannel(String id) //Has to be called every time list of videos is gotten from a channel
    {

        // get current date format  and store it into database every time videos are retrieved from the channel
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        ContentValues values = new ContentValues();
        values.put("last_updated",date); // Contact Phone
        db.update(TABLE_CHANNELS, values,CHANNEL_ID + " = ?",
                new String[]{id});
        db.close();

    }


    public List<YouTubeVideo> getNewVideosFromAYouTubeChannel(String channel_id) // (Always has to be run on different thread) is going to be used to refresh videos and tell user whether there are new videos of not
    {
        RestHandler restHandler = new RestHandler();

        SQLiteDatabase db = this.getReadableDatabase();

        YouTubeChannel channel = this.getChannel(channel_id);
        String last_updated = channel.getLast_updated();
        last_updated = last_updated.substring(0,10)+"T"+last_updated.substring(10,12)+"%3A"+last_updated.substring(13,15)+"%3A"+last_updated.substring(16,18)+"Z";
        String jsonString = restHandler.httpGet("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + channel_id +"&maxResults=50&order=date&publishedAfter=" + last_updated + "&type=video&key=AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo");
      return restHandler.makeYouTubeList(jsonString);  // if it returns an array of length 0 there are no new channels
    }

    public List<YouTubeVideo> getRecentVideosFromAYouTubeChannel(String channel_id) // (Always has to be run on different thread) gets videos in the las 24 hours
    {
        RestHandler restHandler = new RestHandler();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);//get yesterdays date
        String now = df.format(cal.getTime());
        String yesterday = now.substring(0,10)+"T"+now.substring(10,12)+"%3A"+now.substring(13,15)+"%3A"+now.substring(16,18)+"Z";


        String jsonString = restHandler.httpGet("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + channel_id +"&maxResults=50&order=date&publishedAfter=" + yesterday + "&type=video&key=AIzaSyCLGCJOPU8VVj7daoh5HwXZASnmGoc4ylo");
        return restHandler.makeYouTubeList(jsonString);  // if it returns an array of length 0 there are no new channels
    }

}




