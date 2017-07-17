package com.androsol.moviespot.Fragments;

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

import com.androsol.moviespot.Adapters.CastListAdapter;
import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.MovieStructure.Cast;
import com.androsol.moviespot.MovieStructure.CastResults;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 26-04-2017.
 */

public class CastFragment extends Fragment {

    RecyclerView recyclerView;
    CastListAdapter adapter;
    ArrayList<Cast> cast;
    Long movie_id;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.cast_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.cast_list);
        RecyclerView.LayoutManager mlayoutManager= new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        cast = new ArrayList<Cast>();
        adapter = new CastListAdapter(cast,getActivity());
        recyclerView.setAdapter(adapter);
        Bundle b = getArguments();
        movie_id = b.getLong("MovieId");
        fetchData();
        return v;
    }
    public void fetchData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CastResults> call = apiInterface.getCast(movie_id);
        call.enqueue(new Callback<CastResults>() {
            @Override
            public void onResponse(Call<CastResults> call, Response<CastResults> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<Cast> castArray = response.body().getCast();
                        if (castArray.size() != 0) {
                            cast.clear();
                            cast.addAll(castArray);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "No data available!", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CastResults> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Looks like you are not connected to the internet!", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}
