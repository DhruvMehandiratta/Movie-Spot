package com.androsol.moviespot.TVFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.Constants;
import com.androsol.moviespot.R;
import com.androsol.moviespot.TVAdapters.VerticalTVAdapter;
import com.androsol.moviespot.TVStructure.TV;
import com.androsol.moviespot.TVStructure.TVResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 29-04-2017.
 */

public class VerticalTVFragment extends Fragment {
    RecyclerView recyclerView;
    VerticalTVAdapter adapter;
    ArrayList<TV> tvList;
    String title;
    ApiInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tv_vertical_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.tv_vertical_list);
        RecyclerView.LayoutManager mlayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        tvList = new ArrayList<TV>();
        adapter = new VerticalTVAdapter(tvList,getActivity());
        recyclerView.setAdapter(adapter);
        Bundle b = getArguments();
        title = b.getString("Title");
        fetchData();
        return v;
    }
    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);
        if (title.equals(Constants.LATEST)) {
            Call<TV> call = apiInterface.getTVLatest(Constants.LANGUAGE);
            call.enqueue(new Callback<TV>() {
                @Override
                public void onResponse(Call<TV> call, Response<TV> response) {
                    TV tv = response.body();
                    if (tv == null) {
                        Toast.makeText(getActivity(), "No latest TV shows found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    tvList.clear();
                    tvList.add(tv);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<TV> call, Throwable t) {
                    Toast.makeText(getActivity(), "Unable to fetch data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            tvList.clear();
            fetchData1(1);
        }
    }
    public void fetchData1(final int i){
        if(i > 5)
            return;
        Call<TVResults> call = apiInterface.getTVPopular(Constants.LANGUAGE, i);     // just for initialisation
        if(title.equals(Constants.AIRING_TODAY)){
            call = apiInterface.getTVToday(Constants.LANGUAGE, i);
        }else if(title.equals(Constants.TOP_RATED)){
            call = apiInterface.getTVTopRated(Constants.LANGUAGE, i);
        }else if(title.equals(Constants.POPULAR)){
            call = apiInterface.getTVPopular(Constants.LANGUAGE, i);
        }else if(title.equals(Constants.AIRING_THIS_WEEK)){
            call = apiInterface.getTVThisWeek(Constants.LANGUAGE, i);
        }
        call.enqueue(new Callback<TVResults>() {
            @Override
            public void onResponse(Call<TVResults> call, Response<TVResults> response) {
                TVResults tv = response.body();
                if (tv != null) {
                    tvList.addAll(tv.getResults());
                    adapter.notifyDataSetChanged();
                    fetchData1(i + 1);
                }
            }

            @Override
            public void onFailure(Call<TVResults> call, Throwable t) {

                Toast.makeText(getActivity(), "Looks like you are not connected to the internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }