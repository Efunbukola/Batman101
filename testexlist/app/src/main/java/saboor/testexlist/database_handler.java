package saboor.testexlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saboor Salaam on 7/23/2014.
 */
public class database_handler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 19;

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




    public database_handler(Context context) {
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

    public List<channel> getAllLocalChannels() {  //MUST BE DONE ON DIFFERENT THREAD!!!****************$$$$$$####

        List<channel> channels = new ArrayList<channel>();

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

        channels = parseDBCommunicator.getSelectedChannels(ids, new ParseDBCommunicator.connectionFailedListener() {
                    @Override
                    public void onConnectionFailed() {
                        Toast t = Toast.makeText(mContext, R.string.no_network, Toast.LENGTH_LONG);
                        t.show();

                    }

                    @Override
                    public void onConnectionSuccessful() {

                    }

                    @Override
                    public void onCannotConnectToParse(){
                        Toast t = Toast.makeText(mContext, R.string.no_connection_to_parse, Toast.LENGTH_LONG);
                        t.show();
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
        String whereClause = "channel_id"+"=?";
        String[]whereArgs = new String[] {id};
        db.delete(TABLE_LOCAL_CHANNELS, whereClause , whereArgs);
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


    public void printList(List<channel> ch)
    {
        for(int i = 0; i < ch.size(); i++)
        {
            Log.d("Channel Printer", "Channel: " + i  + ch.get(i).getChannel_name() + " ID: " + ch.get(i).getChannel_id() + "Thumbnail URI: " + ch.get(i).getVid1thumb());
        }
    }


}

