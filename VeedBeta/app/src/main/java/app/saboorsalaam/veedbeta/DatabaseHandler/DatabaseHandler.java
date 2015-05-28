package app.saboorsalaam.veedbeta.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import app.saboorsalaam.veedbeta.ParseClasses.Channel;
import app.saboorsalaam.veedbeta.ParseClasses.ParseDBCommunicator;
import app.saboorsalaam.veedbeta.ParseClasses.Video;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "User_data";

    // Channel table name
    private static final String TABLE_LOCAL_CHANNELS = "channels";
    private static final String TABLE_LOCAL_FAVORITES = "favorites";

    // Channel Table Columns names
    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_WEIGHT = "channel_weight";

    private static final String VIDEO_ID = "video_id";
    private static final String VIDEO_TITLE = "video_title";
    private static final String VIDEO_DATE = "video_date";
    private static final String VIDEO_DES= "video_des";
    private static final String VIDEO_THUMB = "video_thumb";
    private static final String VIDEO_CH_TITLE = "video_ch_title";
    private  static  final String VIDEO_TYPE = "video_type";



    private static final String VIDEO_WEIGHT = "video_weight";
    private Context mContext;
    private List<Channel> channels, channelIds;



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCAL_CHANNELS_TABLE = "CREATE TABLE " + TABLE_LOCAL_CHANNELS + "("
                + CHANNEL_ID + " TEXT PRIMARY KEY, " +
                CHANNEL_WEIGHT +" INTEGER)";
        String CREATE_LOCAL_FAVORITES_TABLE = "CREATE TABLE " + TABLE_LOCAL_FAVORITES+ "("
                + VIDEO_ID + " TEXT PRIMARY KEY, "
                + VIDEO_DATE + " TEXT, "
                + VIDEO_TITLE + " TEXT, "
                + VIDEO_DES + " TEXT, "
                + VIDEO_THUMB + " TEXT, "
                + VIDEO_CH_TITLE + " TEXT, "
                + VIDEO_TYPE + " TEXT, " +
                VIDEO_WEIGHT +" INTEGER)";
        db.execSQL(CREATE_LOCAL_CHANNELS_TABLE);
        db.execSQL(CREATE_LOCAL_FAVORITES_TABLE);
    }




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCAL_CHANNELS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCAL_FAVORITES);
        onCreate(db);
    }


    public List<Channel> getAllLocalChannels(final connectionFailedListener connectionFailedListener) {  //MUST BE DONE ON DIFFERENT THREAD!!!****************$$$$$$####

        channels = new ArrayList<Channel>();

        String selectQuery = "SELECT " + CHANNEL_ID + " FROM " + TABLE_LOCAL_CHANNELS +
                " ORDER BY " + CHANNEL_WEIGHT + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        ParseDBCommunicator parseDBCommunicator = new ParseDBCommunicator(mContext);
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<String> ids = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        Log.d("Channels", ids.size() + " retrieved from the database!");

        channels = parseDBCommunicator.getSelectedChannels(ids, new ParseDBCommunicator.connectionFailedListener() {
            @Override
            public void onConnectionFailed() {
                connectionFailedListener.onConnectionFailed();
            }

            @Override
            public void onConnectionSuccessful() {
                connectionFailedListener.onConnectionSuccessful();
            }

            @Override
            public void onCannotConnectToParse(){
                connectionFailedListener.onCannotConnectToParse();
            }
        });

        cursor.close();
        db.close();
        return channels;
    }


    public List<Video> getAllLocalFavorites() {  //MUST BE DONE ON DIFFERENT THREAD!!!****************$$$$$$####

        List<Video> favorites = new ArrayList<Video>();

        String selectQuery = "SELECT * FROM " + TABLE_LOCAL_FAVORITES +
                " ORDER BY " + VIDEO_WEIGHT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                favorites.add(new Video(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favorites;
    }

    public void deleteChannel(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = CHANNEL_ID+"=?";
        String[]whereArgs = new String[] {id};
        db.beginTransaction();
        db.delete(TABLE_LOCAL_CHANNELS, whereClause , whereArgs);
        db.setTransactionSuccessful();
        db.endTransaction();
        if(true) {
            Log.d("Channel_deleted", whereClause + whereArgs[0]);
        }else{
            Log.d("Channel could not be deleted", whereClause + whereArgs[0]);
        }
    }


    public void deleteFavorite(Video video)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "video_id"+"=?";
        String[]whereArgs = new String[] {video.getVideoId()};
        db.delete(TABLE_LOCAL_FAVORITES, whereClause , whereArgs);
    }

    public void addChannel(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + CHANNEL_WEIGHT +
                " FROM " + TABLE_LOCAL_CHANNELS +
                " ORDER BY " + CHANNEL_WEIGHT + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        ContentValues values = new ContentValues();
        values.put(CHANNEL_ID, id);

        if(cursor.moveToLast()) {
            cursor.moveToLast();
            values.put(CHANNEL_WEIGHT, cursor.getInt(0) + 1);
        }
        else
        {
            values.put(CHANNEL_WEIGHT, 0);
        }

        db.insert(TABLE_LOCAL_CHANNELS, null, values);
        db.close(); // Closing database connection

    }

    public void addFavorite(Video video)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT " + VIDEO_WEIGHT +
                " FROM " + TABLE_LOCAL_FAVORITES +
                " ORDER BY " + VIDEO_WEIGHT + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);
        ContentValues values = new ContentValues();
        values.put(VIDEO_ID, video.getVideoId());
        values.put(VIDEO_TITLE, video.getVideoTitle());
        values.put(VIDEO_DES, video.getVideoDescription());
        values.put(VIDEO_DATE, video.getDatePublished());
        values.put(VIDEO_THUMB, video.getVideoThumbnail());
        values.put(VIDEO_CH_TITLE, video.getChannelTitle());
        values.put(VIDEO_TYPE, video.getType());

        if(cursor.moveToLast()) {
            cursor.moveToLast();
            values.put(VIDEO_WEIGHT, cursor.getInt(0) + 1);
        }
        else
        {
            values.put(VIDEO_WEIGHT, 0);
        }

        db.insert(TABLE_LOCAL_FAVORITES, null, values);
        db.close(); // Closing database connection

    }


    public void saveChannelOrder(List<Channel> channels)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        for(int i = 0; i < channels.size(); i++)
        {
            String id = channels.get(i).getChannel_id();
            ContentValues newValues = new ContentValues();
            newValues.put(CHANNEL_WEIGHT, i);
            String[] args = new String[]{id};
            db.update(TABLE_LOCAL_CHANNELS, newValues, CHANNEL_ID + "=?", args);
            //db.execSQL("UPDATE " + TABLE_LOCAL_CHANNELS + " SET " + CHANNEL_WEIGHT + "=" + i + " WHERE " + CHANNEL_ID + "=" + id);
        }
        db.close();
    }

    public void saveOrder(List<Object> objects)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        for(int i = 0; i < objects.size(); i++)
        {
            Field a = null; //Get object ID
            try {
                a = objects.get(i).getClass().getField("channel_id");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            String id = "error";

            try {
                id = (String) a.get(objects.get(i));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            ContentValues newValues = new ContentValues();
            newValues.put(CHANNEL_WEIGHT, i);
            String[] args = new String[]{id};
            db.update(TABLE_LOCAL_CHANNELS, newValues, CHANNEL_ID + "=?", args);
            //db.execSQL("UPDATE " + TABLE_LOCAL_CHANNELS + " SET " + CHANNEL_WEIGHT + "=" + i + " WHERE " + CHANNEL_ID + "=" + id);
        }
        db.close();
    }

    public boolean isUserFollowingChannel(String id)
    {
        Boolean following = false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOCAL_CHANNELS, null,CHANNEL_ID + "=?", new String[]{id},null, null, null);
        if(cursor.moveToFirst()) {following = true;}
        cursor.close();
        return following;
    }

    public boolean isVideoAFavorite(String id)
    {
        Boolean following = false;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOCAL_FAVORITES, null,VIDEO_ID + "=?", new String[]{id},null, null, null);
        if(cursor.moveToFirst()) {following = true;}
        cursor.close();

        return following;
    }


    public void printList(List<Channel> ch)
    {
        for(int i = 0; i < ch.size(); i++)
        {
            Log.d("Channel Printer", "Channel: " + i + ch.get(i).getChannel_name() + " ID: " + ch.get(i).getChannel_id() + "Thumbnail URI: " + ch.get(i).getVid1thumb());
        }
    }


    public interface connectionFailedListener {

        void onConnectionFailed();
        void onConnectionSuccessful();
        void onCannotConnectToParse();
    }


}
