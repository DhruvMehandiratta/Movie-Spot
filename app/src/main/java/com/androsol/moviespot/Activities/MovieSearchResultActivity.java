package com.androsol.moviespot.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.androsol.moviespot.Adapters.VerticalListAdapter;
import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.MovieStructure.Movie;
import com.androsol.moviespot.MovieStructure.Results;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieSearchResultActivity extends AppCompatActivity {

    String query;
    RecyclerView recyclerView;
    VerticalListAdapter adapter;
    ArrayList<Movie> movies;
    TextView noOfResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_result2);
        Intent i = getIntent();
        query = i.getStringExtra("Query");
        noOfResults = (TextView) findViewById(R.id.no_of_results);
        noOfResults.setText("Searching Movie...");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewForSearch);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        movies = new ArrayList<Movie>();
        adapter = new VerticalListAdapter(movies, this);
        recyclerView.setAdapter(adapter);
        fetchData(1);
    }
    public void fetchData(final int i){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        if(i>5) {
            int size = this.movies.size();
            String text = size + " results found";
            noOfResults.setText(text);
            return;
        }
        Call<Results> call = apiInterface.getMovieSearchResults(query,i);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Results movies = response.body();
                if (movies != null) {
                    MovieSearchResultActivity.this.movies.addAll(movies.getResults());
                    adapter.notifyDataSetChanged();
                    fetchData(i + 1);
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Snackbar.make(MovieSearchResultActivity.this.findViewById(android.R.id.content),"Looks like you are not connected to the internet",Snackbar.LENGTH_LONG).show();

            }
        });
    }
}
