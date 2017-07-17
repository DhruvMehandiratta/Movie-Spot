package com.androsol.moviespot.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androsol.moviespot.Adapters.VerticalListAdapter;
import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.Constants;
import com.androsol.moviespot.MovieStructure.Movie;
import com.androsol.moviespot.MovieStructure.Results;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 02-04-2017.
 */

public class VerticalFragment extends Fragment {
    RecyclerView recyclerView;
    VerticalListAdapter adapter;
    ArrayList<Movie> movies;
    String title;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vertical_fragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.vertical_list);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        movies = new ArrayList<Movie>();
        adapter = new VerticalListAdapter(movies, getActivity());
        recyclerView.setAdapter(adapter);
        Bundle b = getArguments();
        title = b.getString("Title");
        if(container != null)
        context = container.getContext();
        fetchData();
        return v;
    }

    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        // i = getActivity().getIntent();
        // String type = i.getStringExtra("Type");
        if (title.equals(Constants.LATEST)) {
            Call<Movie> call = apiInterface.getLatest(Constants.LANGUAGE);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Movie movie = response.body();
                    if (movie != null) {
                        movies.clear();
                        movies.add(movie);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(context, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            movies.clear();
            fetchData1(1);
//                Call<Results> call = apiInterface.getUpcoming(Constants.LANGUAGE);     // just for initialisation
//                if(title.equals(Constants.UPCOMING)){
//                    call = apiInterface.getUpcoming(Constants.LANGUAGE);
//                }else if(title.equals(Constants.TOP_RATED)){
//                    call = apiInterface.getTopRated(Constants.LANGUAGE);
//                }else if(title.equals(Constants.POPULAR)){
//                    call = apiInterface.getPopular(Constants.LANGUAGE);
//                }else if(title.equals(Constants.IN_THEATERS)){
//                    call = apiInterface.getInTheaters(Constants.LANGUAGE);
//                }
//            call.enqueue(new Callback<Results>() {
//                @Override
//                public void onResponse(Call<Results> call, Response<Results> response) {
//                    Results movies = response.body();
//                    VerticalFragment.this.movies.clear();
//                    VerticalFragment.this.movies.addAll(movies.getResults());
//                    adapter.notifyDataSetChanged();
//                    Log.d("Success",response.message());
//                }
//
//                @Override
//                public void onFailure(Call<Results> call, Throwable t) {
//                    Toast.makeText(getActivity(), "Looks like you are not connected to the internet!", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
    public void fetchData1(final int i){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        if(i > 5){
            return ;
        }
        Call<Results> call = apiInterface.getUpcoming(Constants.LANGUAGE,i);
        if(title.equals(Constants.UPCOMING)){
                    call = apiInterface.getUpcoming(Constants.LANGUAGE,i);
                }else if(title.equals(Constants.TOP_RATED)){
                    call = apiInterface.getTopRated(Constants.LANGUAGE,i);
                }else if(title.equals(Constants.POPULAR)){
                    call = apiInterface.getPopular(Constants.LANGUAGE,i);
                }else if(title.equals(Constants.IN_THEATERS)){
                    call = apiInterface.getInTheaters(Constants.LANGUAGE,i);
                }
        call.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    Results movies = response.body();
                    if(response.body() != null)
                    VerticalFragment.this.movies.addAll(movies.getResults());
                    adapter.notifyDataSetChanged();
                    fetchData1(i+1);
                    Log.d("Success",response.message());
                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Toast.makeText(context, "Looks like you are not connected to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
    }


}