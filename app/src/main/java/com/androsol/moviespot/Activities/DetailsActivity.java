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
import com.androsol.moviespot.Fragments.CastFragment;
import com.androsol.moviespot.Fragments.CrewFragment;
import com.androsol.moviespot.Fragments.DetailsFragment1;
import com.androsol.moviespot.Fragments.ReviewsFragment;
import com.androsol.moviespot.MovieStructure.MovieDetails;
import com.androsol.moviespot.MovieStructure.MovieVideos;
import com.androsol.moviespot.MovieStructure.Video;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.androsol.moviespot.Activities.DetailsActivity.movie_id;


public class DetailsActivity extends AppCompatActivity {

    String imageURL;
    static long movie_id;
    ViewPager viewPager;
    ImageView toolbarImage;
    FloatingActionButton trailerButton, watchListButton;
    String mTitle = "";
    PopupMenu pop;
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Intent i = getIntent();
        movie_id = i.getLongExtra("Id", 0);

        toolbarImage= (ImageView) findViewById(R.id.movie_big_poster);

        //watchlist work
        watchListButton = (FloatingActionButton) findViewById(R.id.watch_list_button);
        fetchMovieData();
        watchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchListButton.setVisibility(View.GONE);
                pop = new PopupMenu(DetailsActivity.this, watchListButton);
                pop.getMenuInflater().inflate(R.menu.popup_menu,pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //TODO work database
                        MyDBHelper dbHelper = new MyDBHelper(DetailsActivity.this);
                        //check for already in watchlist
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        String query = "SELECT * FROM "+dbHelper.WATCHLIST_MOVIE_TABLE + " WHERE " +
                                dbHelper.COLUMN_MOVIE_ID + " = " + movie_id + " ; ";
                        Cursor c = db.rawQuery(query, null);
                        c.moveToFirst();
                        if(!c.isAfterLast()){
                            Snackbar.make(DetailsActivity.this.findViewById(android.R.id.content),
                                    "Already present in watchlist!",Snackbar.LENGTH_LONG).show();
                            return true;
                        }
                        getTitleHelp();
                        if(mTitle.length()==0){
                            Snackbar.make(DetailsActivity.this.findViewById(android.R.id.content),
                                    "Please wait for the data to be loaded first!",Snackbar.LENGTH_LONG).show();
                            return true;
                        }
                        Log.d("dhruvTQWERTY",mTitle);
                        dbHelper.addToMoviesWatchlist(dbHelper.WATCHLIST_MOVIE_TABLE,movie_id,mTitle);
                        Snackbar.make(DetailsActivity.this.findViewById(android.R.id.content),"Added to watchlist!",Snackbar.LENGTH_LONG).show();
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

        //......

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nest_scroll_view);
        scrollView.setFillViewport(true);

        Log.d("idFetched", movie_id + "");




        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, DisplayPhoto.class);
               // Intent i = new Intent(getApplicationContext(), DisplayPhoto.class);
               i.putExtra("MovieId",movie_id);
                i.putExtra("TYPE","MOVIE");
                i.putExtra("Differentiate","Activity");
                startActivity(i);
            }
        });
        trailerButton = (FloatingActionButton) findViewById(R.id.fab);
        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                        .addConverterFactory(GsonConverterFactory.create()).build();
                ApiInterface apiInterface = retrofit.create(ApiInterface.class);
                Call<MovieVideos> call = apiInterface.getVideos(movie_id);
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

        viewPager = (ViewPager) findViewById(R.id.details_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter1(fragmentManager));
    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    public void fetchMovieData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MovieDetails> call = apiInterface.getMovieDetails(movie_id);
        call.enqueue(new Callback<MovieDetails>() {

            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                MovieDetails movie = response.body();
                if (movie != null) {
                    imageURL = movie.getBackdrop_path();

                    Log.d("dhruv", "image   " + imageURL);
                     mTitle = movie.getTitle();

                    String logoURL = movie.getPoster_path();
                    setImage(imageURL, mTitle,logoURL);
                } else
                    Log.d("dhruv", "lallalal" + movie);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.d("responseFail", "FAiLlfladf");
            }
        });
    }


    public void setImage(String s, String mTitle, String logoURL){
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + s).into(toolbarImage);
        getSupportActionBar().setTitle(mTitle);

    }

    public void  getTitleHelp(){
        final String[] helpTitle = new String[1];
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MovieDetails> call = apiInterface.getMovieDetails(movie_id);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails helpMovie = response.body();
                if (helpMovie != null) {
                    helpTitle[0] = helpMovie.getTitle();
                    mTitle = helpTitle[0];
                }
            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
//Snackbar.make(DetailsActivity.this.findViewById(android.R.id.content),"Check your Internet Connection",Snackbar.LENGTH_LONG).show();
            }
        });
    }

}

class MyAdapter1 extends FragmentStatePagerAdapter {

    public MyAdapter1(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            DetailsFragment1 frag = new DetailsFragment1();
            Bundle b = new Bundle();
            b.putLong("MovieId", movie_id);
            frag.setArguments(b);
            return frag;
        }
        if (position == 1) {
            CastFragment frag = new CastFragment();
            Bundle b = new Bundle();
            b.putLong("MovieId", movie_id);
            frag.setArguments(b);
            return frag;
        }
        if(position == 2){
            CrewFragment frag = new CrewFragment();
            Bundle b = new Bundle();
            b.putLong("MovieId",movie_id);
            frag.setArguments(b);
            return frag;
        }
        if (position == 3) {
            ReviewsFragment frag = new ReviewsFragment();
            Bundle b = new Bundle();
            b.putLong("MovieId", movie_id);
            frag.setArguments(b);
            return frag;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        //Log.d("countOfFrags","4");
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "OVERVIEW";
        }
        if (position == 1) {
            return "CAST";
        }
        if (position == 2) {
            return "CREW";
        }
        if(position == 3){
            return "REVIEWS";
        }
        return "";
    }


}
