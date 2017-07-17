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

public class PeopleTVFragment extends Fragment {
    long people_id;
    RecyclerView recyclerView;
    PeopleTVAdapter adapter;
    ArrayList<PeopleTV> tvList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.people_tv_fragment,container,false);
        Bundle b = getArguments();
        people_id = b.getLong("PeopleId");
        tvList = new ArrayList<PeopleTV>();
        recyclerView = (RecyclerView) v.findViewById(R.id.people_tv_recyclerview);
        RecyclerView.LayoutManager mlayoutManager= new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new PeopleTVAdapter(tvList,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        fetchData();
        return v;
    }

    public void fetchData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<PeopleTVResults> call = apiInterface.getPeopleTV(people_id);
        call.enqueue(new Callback<PeopleTVResults>() {
            @Override
            public void onResponse(Call<PeopleTVResults> call, Response<PeopleTVResults> response) {
                PeopleTVResults tvList1 = response.body();
                if (tvList1 != null) {
                    tvList.clear();
                    tvList.addAll(tvList1.getCast());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PeopleTVResults> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Looks like you are not connected to the internet!", Snackbar.LENGTH_LONG).show();

            }
        });

    }
}
