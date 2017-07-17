package com.androsol.moviespot.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androsol.moviespot.Adapters.CrewListAdapter;
import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.MovieStructure.Crew;
import com.androsol.moviespot.MovieStructure.CrewResults;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 29-04-2017.
 */

public class CrewFragment extends Fragment {

    RecyclerView recyclerView;
    CrewListAdapter adapter;
    ArrayList<Crew> crew;
    Long movie_id;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.crew_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.crew_list);
        RecyclerView.LayoutManager mlayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        crew = new ArrayList<Crew>();
        adapter = new CrewListAdapter(crew,getActivity());
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
        Call<CrewResults> call = apiInterface.getCrew(movie_id);
        call.enqueue(new Callback<CrewResults>() {
            @Override
            public void onResponse(Call<CrewResults> call, Response<CrewResults> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<Crew> crewArray = response.body().getCrew();
                        if (crewArray.size() != 0) {
                            crew.clear();
                            crew.addAll(crewArray);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "No data available!", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CrewResults> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Looks like you are not connected to the internet!", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}

