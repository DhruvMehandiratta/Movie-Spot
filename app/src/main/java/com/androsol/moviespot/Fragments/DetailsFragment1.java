package com.androsol.moviespot.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androsol.moviespot.Adapters.HorizontalListAdapter;
import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.DisplayPhoto;
import com.androsol.moviespot.MovieStructure.Movie;
import com.androsol.moviespot.MovieStructure.MovieDetails;
import com.androsol.moviespot.MovieStructure.MovieGenre;
import com.androsol.moviespot.MovieStructure.Results;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 15-04-2017.
 */

public class DetailsFragment1 extends Fragment {
    long movie_id;
    TextView synopsis, releaseDate, runtime, tagline, genre;
    RecyclerView recyclerView;
    HorizontalListAdapter adapter;
    ArrayList<Movie> movies;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details_fragment1_layout, container, false);
        Bundle b = getArguments();
        movie_id = b.getLong("MovieId");
        synopsis = (TextView) v.findViewById(R.id.synopsisText);
        genre = (TextView)v.findViewById(R.id.genre);
        releaseDate = (TextView) v.findViewById(R.id.releaseDateText);
        runtime = (TextView) v.findViewById(R.id.runtimeText);
        tagline = (TextView) v.findViewById(R.id.taglineText);
        movies = new ArrayList<Movie>();
        recyclerView = (RecyclerView) v.findViewById(R.id.horizontal_list_for_fragment);
        adapter = new HorizontalListAdapter(movies, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        imageView = (ImageView) v.findViewById(R.id.image_logo_final);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DisplayPhoto.class);
                i.putExtra("MovieId",movie_id);
                i.putExtra("Differentiate","Fragment");
                i.putExtra("TYPE","MOVIE");
                startActivity(i);
            }
        });
        fetchData();
        return v;
    }

    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        // call for fragment movie details
        Call<MovieDetails> movieDetails = apiInterface.getMovieDetails(movie_id);
        movieDetails.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails movie = response.body();
                if (movie != null) {

                    //synopsis

                    if (movie.getOverview() != null && !movie.getOverview().equals(""))
                        synopsis.setText(movie.getOverview());
                    else
                        synopsis.setText("Not Available");

                    //release date
                    if (movie.getRelease_date() != null && !movie.getRelease_date().equals(""))
                        releaseDate.setText(movie.getRelease_date());
                    else
                        releaseDate.setText("Not Available");

                    //tagline
                    if (movie.getTagline() != null && !movie.getTagline().equals(""))
                        tagline.setText(movie.getTagline());
                    else
                        tagline.setText("Nil");

                    //genre
                    ArrayList<MovieGenre> genres = movie.getGenres();
                    String mGenre = "";
                    for (int i = 0; i < genres.size() - 1; i++) {
                        mGenre += genres.get(i).getName() + ", ";
                    }
                    if (genres.size() != 0)
                        mGenre += genres.get(genres.size() - 1).getName();
                    else
                        mGenre = "Not Specified";
                    genre.setText(mGenre);

                    //runtime
                    int mRuntime = movie.getRuntime();
                    String mRun = mRuntime / 60 + ":";
                    mRuntime = mRuntime % 60;
                    if (mRuntime < 10)
                        mRun += "0";
                    mRun += mRuntime + "hrs";
                    mRun += " (" + movie.getRuntime() + "mins)";
                    runtime.setText(mRun);

                    //image
                    String imageURL = movie.getPoster_path();
                    Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w300" + imageURL).into(imageView);
                }
            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(getActivity(),"Poor Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

        // call for horizontal list
        final Call<Results> similarMovies = apiInterface.getSimilarMovies(movie_id);
        similarMovies.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Results similarResults = response.body();
                if (similarResults != null) {
                    movies.clear();
                    movies.addAll(similarResults.getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Toast.makeText(getActivity(), "Looks like you are not connected to the internet!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
