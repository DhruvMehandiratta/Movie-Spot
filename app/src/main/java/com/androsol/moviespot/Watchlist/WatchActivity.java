package com.androsol.moviespot.Watchlist;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.androsol.moviespot.R;

public class WatchActivity extends AppCompatActivity {

    int id = 5122;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("WatchList");
        Intent i = getIntent();
        String type = i.getStringExtra("TYPE");
        if(type.equals("MOVIE")){
            WatchlistMovieFragment frag = new WatchlistMovieFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_watch, frag).commit();
        }
        else if(type.equals("TV")){
            WatchListTVFragment frag = new WatchListTVFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_watch, frag).commit();
        }
    }
    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }
}
