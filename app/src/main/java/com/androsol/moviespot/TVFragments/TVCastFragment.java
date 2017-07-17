package com.androsol.moviespot.TVFragments;

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
import com.androsol.moviespot.TVAdapters.TVCastAdapter;
import com.androsol.moviespot.TVStructure.TVCast;
import com.androsol.moviespot.TVStructure.TVCastResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 30-04-2017.
 */

public class TVCastFragment extends Fragment {
    RecyclerView recyclerView;
    TVCastAdapter adapter;
    ArrayList<TVCast> cast;
    Long tv_id;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tv_cast_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.tv_cast_list);
        RecyclerView.LayoutManager mlayoutManager= new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        cast = new ArrayList<TVCast>();
        adapter = new TVCastAdapter(cast,getActivity());
        recyclerView.setAdapter(adapter);
        Bundle b = getArguments();
        tv_id = b.getLong("TVId");
        fetchData();
        return v;
    }
    public void fetchData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<TVCastResults> call = apiInterface.getTVCast(tv_id);
        call.enqueue(new Callback<TVCastResults>() {
            @Override
            public void onResponse(Call<TVCastResults> call, Response<TVCastResults> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<TVCast> castArray = response.body().getCast();
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
            public void onFailure(Call<TVCastResults> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Looks like you are not connected to the internet!", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}
