package com.androsol.moviespot.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.Database.MyDBHelper;
import com.androsol.moviespot.DisplayPhoto;
import com.androsol.moviespot.MovieStructure.MovieVideos;
import com.androsol.moviespot.MovieStructure.Video;
import com.androsol.moviespot.R;
import com.androsol.moviespot.TVFragments.TVCastFragment;
import com.androsol.moviespot.TVFragments.TVDetailsFragment1;
import com.androsol.moviespot.TVFragments.TVSeasonFragment;
import com.androsol.moviespot.TVStructure.TVDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.androsol.moviespot.Activities.TVDetailsActivity.noOfSeasons;
import static com.androsol.moviespot.Activities.TVDetailsActivity.tv_id;

public class TVDetailsActivity extends AppCompatActivity {

    ImageView toolbarImage;
    static long tv_id;
    FloatingActionButton trailerButton, watchListButton;
    ViewPager viewPager;
    String imageURL, mTitle = "";
    static long noOfSeasons;
    PopupMenu pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Intent i = getIntent();
        tv_id = i.getLongExtra("Id", 0);
        Log.d("idFetched", tv_id + "");

        toolbarImage = (ImageView) findViewById(R.id.tv_movie_big_poster);

        //watchlist work
        watchListButton = (FloatingActionButton) findViewById(R.id.watch_list_button_tv);
        fetchTVData();
        watchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchListButton.setVisibility(View.GONE);
                pop = new PopupMenu(TVDetailsActivity.this, watchListButton);
                pop.getMenuInflater().inflate(R.menu.popup_menu,pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //TODO work database
                        MyDBHelper dbHelper = new MyDBHelper(TVDetailsActivity.this);
                        //check for already in watchlist
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        String query = "SELECT * FROM "+dbHelper.WATCHLIST_TV_TABLE + " WHERE " +
                                dbHelper.TVTABLE_COLUMN_TV_ID + " = " + tv_id + " ; ";
                        Cursor c = db.rawQuery(query, null);
                        c.moveToFirst();
                        if(!c.isAfterLast()){
                            Snackbar.make(TVDetailsActivity.this.findViewById(android.R.id.content),
                                    "Already present in watchlist!",Snackbar.LENGTH_LONG).show();
                            return true;
                        }
                        getTitleHelp();
                        Log.d("TVTITLEtaken",mTitle);
                        if(mTitle.length()==0){
                            Snackbar.make(TVDetailsActivity.this.findViewById(android.R.id.content),
                                    "Please wait for the data to be loaded first!",Snackbar.LENGTH_LONG).show();
                            return true;
                        }
                        Log.d("dhruvTQWERTYjajja",mTitle);
                        Log.d("dhruvTVIDtaken",tv_id+"");
                        dbHelper.addToTVWatchlist(dbHelper.WATCHLIST_TV_TABLE,tv_id,mTitle);
                        Snackbar.make(TVDetailsActivity.this.findViewById(android.R.id.content),"Added to watchlist!",Snackbar.LENGTH_LONG).show();
                        watchListButton.setVisibility(View.VISIBLE);
                        return true;
                    }
                });
                pop.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        watchListButton.setVisibility(View.VISIBLE);
                    }
                });
                pop.show();
            }

        });

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.tv_nest_scroll_view);
        scrollView.setFillViewport(true);

        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TVDetailsActivity.this, DisplayPhoto.class);
                // Intent i = new Intent(getApplicationContext(), DisplayPhoto.class);
                i.putExtra("TVId", tv_id);
                i.putExtra("TYPE","TV");
                i.putExtra("Differentiate", "Activity");
                startActivity(i);
            }
        });

        trailerButton = (FloatingActionButton) findViewById(R.id.tv_fab);
        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create()).build();
                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                Call<MovieVideos> call = apiInterface.getTVVideos(tv_id);
                call.enqueue(new Callback<MovieVideos>() {
                    @Override
                    public void onResponse(Call<MovieVideos> call, Response<MovieVideos> response) {
                        ArrayList<Video> videos = response.body().getResults();
                        if (videos != null) {
                            if (videos.size() == 0) {
                                Snackbar.make(view, "Trailer is not available!", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                String trailerKey = videos.get(0).getKey();
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerKey)));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieVideos> call, Throwable t) {
                        Snackbar.make(view, "Looks like internet is not connected!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
        viewPager = (ViewPager) findViewById(R.id.tv_details_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyTVAdapter1(fragmentManager));
    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Intent i = new Intent(getApplicationContext(), MainActivity.class);
        // startActivityForResult(i,0);
        super.onBackPressed();
        return true;
    }

    public void fetchTVData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<TVDetails> call = apiInterface.getTVDetails(tv_id);
        call.enqueue(new Callback<TVDetails>() {

            @Override
            public void onResponse(Call<TVDetails> call, Response<TVDetails> response) {

                TVDetails tv = response.body();

                if (tv != null) {
                    noOfSeasons = tv.getNumber_of_seasons();
                    imageURL = tv.getBackdrop_path();

                    Log.d("dhruv", "image   " + imageURL);
                    mTitle = tv.getName();

                    String logoURL = tv.getPoster_path();
                    setImage(imageURL, mTitle, logoURL);
                }
            }

            @Override
            public void onFailure(Call<TVDetails> call, Throwable t) {
                Log.d("responseFail", "FAiLlfladf");
            }
        });

    }

    public void setImage(String s, String mTitle, String logoURL) {
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + s).into(toolbarImage);
        getSupportActionBar().setTitle(mTitle);

    }

    public void  getTitleHelp(){
        final String[] helpTitle = new String[1];
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<TVDetails> call = apiInterface.getTVDetails(tv_id);
        call.enqueue(new Callback<TVDetails>() {
            @Override
            public void onResponse(Call<TVDetails> call, Response<TVDetails> response) {
                TVDetails helpTV = response.body();
                if(helpTV != null)
                    helpTitle[0] = helpTV.getName();
                mTitle = helpTitle[0];
                Log.d("dhruvTVTITLEBYFN",mTitle);
            }

            @Override
            public void onFailure(Call<TVDetails> call, Throwable t) {
//Snackbar.make(DetailsActivity.this.findViewById(android.R.id.content),"Check your Internet Connection",Snackbar.LENGTH_LONG).show();
            }
        });
    }


}

    class MyTVAdapter1 extends FragmentStatePagerAdapter {

        public MyTVAdapter1(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                TVDetailsFragment1 frag = new TVDetailsFragment1();
                Bundle b = new Bundle();
                b.putLong("TVId", tv_id);
                frag.setArguments(b);
                return frag;
            }
            if (position == 1) {
                TVCastFragment frag = new TVCastFragment();
                Bundle b = new Bundle();
                b.putLong("TVId", tv_id);
                frag.setArguments(b);
                return frag;
            }
            if(position == 2){
                TVSeasonFragment frag = new TVSeasonFragment();
                Bundle b = new Bundle();
                b.putLong("TVId",tv_id);
                b.putLong("NoOfSeasons",noOfSeasons);
                frag.setArguments(b);
                return frag;
            }
            else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "OVERVIEW";
            }
            if (position == 1) {
                return "CAST";
            }
            if(position == 2){
                return "CUSTOM SEASON DETAILS";
            }
            return "";
        }
    }
