package com.androsol.moviespot.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.androsol.moviespot.R;
import com.androsol.moviespot.Watchlist.WatchActivity;

public class WatchlistActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton movieButton, tvButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("WatchList");
        movieButton = (ImageButton) findViewById(R.id.movie_button);
        tvButton = (ImageButton) findViewById(R.id.tv_button);
        movieButton.setOnClickListener(this);
        tvButton.setOnClickListener(this);

    }
    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }


//
//    @Override
//    public void onBackPressed() {
//            finish();
//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.movie_button){
            Intent i = new Intent(WatchlistActivity.this, WatchActivity.class);
            i.putExtra("TYPE","MOVIE");
            startActivity(i);
        }else if(id == R.id.tv_button){
            Intent i = new Intent(WatchlistActivity.this, WatchActivity.class);
            i.putExtra("TYPE","TV");
            startActivity(i);
        }
    }
}

