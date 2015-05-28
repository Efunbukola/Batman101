package beta.veed.com.veed;

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
    private static final int DATABASE_VERSION = 14;

    // Database Name
    private static final String DATABASE_NAME = "User_data";

    // Channel table name
    private static final String TABLE_LOCAL_CHANNELS = "channels";

    // Channel Table Columns names
    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_WEIGHT = "channel_weight";
    private Context mContext;


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCAL_CHANNELS_TABLE = "CREATE TABLE " + TABLE_LOCAL_CHANNELS + "("
                + CHANNEL_ID + " TEXT PRIMARY KEY, " +
                CHANNEL_WEIGHT +" INTEGER)";
        db.execSQL(CREATE_LOCAL_CHANNELS_TABLE);
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

    public void deleteChannel(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "channel_id"+"=?";
        String[]whereArgs = new String[] {id};
        db.delete(TABLE_LOCAL_CHANNELS, whereClause , whereArgs);
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
        return (following);
    }


    public void printList(List<channel> ch)
    {
        for(int i = 0; i < ch.size(); i++)
        {
            Log.d("Channel Printer", "Channel: " + i + ch.get(i).getChannel_name() + " ID: " + ch.get(i).getChannel_id() + "Thumbnail URI: " + ch.get(i).getVid1thumb());
        }
    }


}

/*

    public void InitializeUserAccount() {
        String CREATE_USER_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_USER_ACCOUNT + "("
                + EMAIL + " TEXT PRIMARY KEY," + USER_NAME + " TEXT,"
                + PASSWORD + " TEXT" + ")";
        getWritableDatabase().execSQL(CREATE_USER_ACCOUNT_TABLE);
    }

    public UserAccount getUserAccount() {
        String selectQuery = "SELECT  * FROM " + TABLE_USER_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        UserAccount account = new UserAccount();
        if (cursor.moveToFirst()) {
            account.setEmail(cursor.getString(0));
            account.setName(cursor.getString(1));
            account.setPassword(cursor.getString(3));
        }

        // return contact list
        return account;
    }

    public void createUserAccount(UserAccount newAccount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, newAccount.getName()); // Contact Name
        values.put(EMAIL, newAccount.getEmail()); // Contact Phone Number
        values.put(PASSWORD, newAccount.getPassword());

        // Inserting Row
        db.insert(TABLE_USER_ACCOUNT, null, values);
        db.close(); // Closing database connection
    }

    boolean IsUserRegistered() {
        Cursor cursor = getWritableDatabase().rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", TABLE_USER_ACCOUNT});
        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

*/
