package com.androsol.moviespot.Watchlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androsol.moviespot.Database.MyDBHelper;
import com.androsol.moviespot.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 09-06-2017.
 */

public class WatchlistMovieFragment extends Fragment {
    RecyclerView recyclerView;
    WatchlistMovieAdapter adapter;
    ArrayList<WatchlistMovie> movies;
    MyDBHelper helper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.watchlist_movie_fragment,container,false);
        movies = new ArrayList<WatchlistMovie>();
        helper = new MyDBHelper(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.watchlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new WatchlistMovieAdapter(movies, getContext());
        recyclerView.setAdapter(adapter);
        //setUpList();
        setUpViews();
        return v;
    }

    public void setUpViews(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "SELECT * FROM "+helper.WATCHLIST_MOVIE_TABLE + ";";
        Cursor c = db.rawQuery(query,null);
        movies.clear();
        c.moveToFirst();
        while(!c.isAfterLast()){
            long id = c.getInt(c.getColumnIndex(helper.COLUMN_ID));
            String title = c.getString(c.getColumnIndex(helper.COLUMN_MOVIE_TITLE));
            Long movie_id = c.getLong(c.getColumnIndex(helper.COLUMN_MOVIE_ID));
            WatchlistMovie movie = new WatchlistMovie(movie_id, title, id);
            movies.add(movie);
            c.moveToNext();
        }
        db.close();
        adapter.notifyDataSetChanged();

    }
}
