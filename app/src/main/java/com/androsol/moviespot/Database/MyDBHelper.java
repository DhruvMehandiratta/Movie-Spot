package com.androsol.moviespot.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dhruv on 10-06-2017.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    //movie watch list
    public static final String DATABASE_NAME = "watchlist.db";
    public static final int DATABASE_VERSION = 1;
    public static final String WATCHLIST_MOVIE_TABLE = "watchlistMovies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MOVIE_ID = "movieId";
    public static final String COLUMN_MOVIE_TITLE = "title";

    //TV watchlist
    public static final String WATCHLIST_TV_TABLE = "watchlistTV";
    public static final String TVTABLE_COLUMN_ID = "t_id";
    public static final String TVTABLE_COLUMN_TV_ID = "tvId";
    public static final String TVTABLE_COLUMN_TITLE = "t_title";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //movie watch list
        sqLiteDatabase.execSQL("CREATE TABLE "+ WATCHLIST_MOVIE_TABLE + " ( " + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MOVIE_ID + " INTEGER, "  + COLUMN_MOVIE_TITLE + " TEXT " + ");");

        //tv watchlist
        sqLiteDatabase.execSQL("CREATE TABLE "+ WATCHLIST_TV_TABLE + " ( " + TVTABLE_COLUMN_ID +
        " INTEGER PRIMARY KEY AUTOINCREMENT, " + TVTABLE_COLUMN_TV_ID + " INTEGER, " + TVTABLE_COLUMN_TITLE +
        " TEXT "+ ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addToMoviesWatchlist(String tableName, long movie_id, String title){
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID,movie_id);
        values.put(COLUMN_MOVIE_TITLE,title);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(tableName, null, values);
        db.close();
    }

    public void deleteFromMoviesWatchList(String tableName, long movie_id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String query = " DELETE FROM "+ tableName + " WHERE " + COLUMN_MOVIE_ID + " = " + movie_id + " ; ";
        db.execSQL(query);
        db.close();
    }

    public void addToTVWatchlist(String tableName, long tv_id, String title){
        ContentValues values = new ContentValues();
        values.put(TVTABLE_COLUMN_TV_ID,tv_id);
        values.put(TVTABLE_COLUMN_TITLE,title);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(tableName, null, values);
        db.close();
    }

    public void deleteFromTVWatchlist(String tableName, long tv_id){
        SQLiteDatabase db = getWritableDatabase();
        String query = " DELETE FROM "+tableName + " WHERE " + TVTABLE_COLUMN_TV_ID + " = " + tv_id + " ; ";
        db.execSQL(query);
        db.close();
    }
}
