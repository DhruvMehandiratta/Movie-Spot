package com.androsol.moviespot.People;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 09-06-2017.
 */

public class PeopleMoviesFragment extends Fragment {
        long peopleId;
        RecyclerView recyclerView;
        PeopleMoviesAdapter adapter;
        ArrayList<PeopleMovie> movies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.people_movies_fragment,container, false);
        Bundle b = getArguments();
        peopleId = b.getLong("PeopleId");
        movies = new ArrayList<PeopleMovie>();
        recyclerView = (RecyclerView) v.findViewById(R.id.people_movies_recyclerview);
        RecyclerView.LayoutManager mlayoutManager= new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new PeopleMoviesAdapter(movies,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        fetchData();
        return v;
    }
    public void fetchData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<PeopleMovieResults> call = apiInterface.getPeopleMovies(peopleId);
        call.enqueue(new Callback<PeopleMovieResults>() {
            @Override
            public void onResponse(Call<PeopleMovieResults> call, Response<PeopleMovieResults> response) {
                PeopleMovieResults moviesList = response.body();
                if (moviesList != null) {
                    movies.clear();
                    movies.addAll(moviesList.getCast());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PeopleMovieResults> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Looks like you are not connected to the internet!", Snackbar.LENGTH_LONG).show();

            }
        });

    }
}
