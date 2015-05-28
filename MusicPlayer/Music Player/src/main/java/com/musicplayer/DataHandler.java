package com.musicplayer;

/**
 * Created by Saboor Salaam on 5/28/2014.
 */
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;


public class DataHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "musicplayerdb";
    private static final int DATABASE_VERSION = 3;

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SONGS_TABLE = "CREATE TABLE Songs (name TEXT PRIMARY KEY, " +
                "artist TEXT, " +
                "rating VARCHAR(1) )";

        db.execSQL(CREATE_SONGS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "Songs");
        onCreate(db);
    }

    public boolean doesSongExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Songs", new String[]{"name"}, "name=?",
                new String[]{name}, null, null, null, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public void addSong(song_type s) {

        SQLiteDatabase db = this.getWritableDatabase();

        if (doesSongExist(s.getName())) {

            ContentValues content = new ContentValues();
           content.put("name", s.getName());
            content.put("artist", s.getArtist());
            content.put("rating", s.getRating() + "");
            String z = s.getRating()+"";
            String x = s.getName();
            db.execSQL("UPDATE Songs SET rating='z' WHERE name='s'");
            //db.update("Songs", content, "name=" + s.getName(), null);
        } else {

            ContentValues content = new ContentValues();
            content.put("name", s.getName());
            content.put("artist", s.getArtist());
            content.put("rating", s.getRating() + "");
            db.insert("Songs", null, content);

        }

        db.close();
    }

    public song_type getSong(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Songs", new String[]{"name", "artist", "rating"}, "name=?",
                new String[]{name}, null, null, null, null);

        song_type s = new song_type();

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            s.setName(cursor.getString(0));
            s.setArtist(cursor.getString(1));
            s.setRating(cursor.getString(2).charAt(0));
            return s;
        } else {
            s.name = "DNE";
            return s;
        }
    }
}