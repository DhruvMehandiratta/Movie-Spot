package com.androsol.moviespot.People;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 08-06-2017.
 */

public class PeopleDetailsFragment1 extends Fragment {
    long peopleId;
    TextView bday, homepage, birthPlace, biography, popularity;
    RecyclerView recyclerView;
    PeopleImageAdapter adapter;
    ArrayList<PeopleImage> images;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.people_details_fragement_layout,container, false);
        Bundle b = getArguments();
        peopleId = b.getLong("PeopleId");
        bday = (TextView) v.findViewById(R.id.bday);
        homepage = (TextView) v.findViewById(R.id.hmpg);
        birthPlace = (TextView) v.findViewById(R.id.place_birth);
        biography = (TextView) v.findViewById(R.id.biography_text);
        popularity = (TextView) v.findViewById(R.id.popularity_text);
        images = new ArrayList<PeopleImage>();
        recyclerView = (RecyclerView) v.findViewById(R.id.people_horizontal_list);
        adapter = new PeopleImageAdapter(images, getContext(),peopleId);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        fetchData();
        return v;
    }
    public void fetchData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<PeopleDetails> call = apiInterface.getPeopleDetails(peopleId);
        call.enqueue(new Callback<PeopleDetails>() {
            @Override
            public void onResponse(Call<PeopleDetails> call, Response<PeopleDetails> response) {
                PeopleDetails people = response.body();
                if (people != null) {
                    if (people.getBirthday() != null)
                        bday.setText(people.getBirthday());
                    if (people.getHomepage() != null)
                        homepage.setText(people.getHomepage());
                    if (people.getPlace_of_birth() != null)
                        birthPlace.setText(people.getPlace_of_birth());
                    if (people.getPopularity() != 0)
                        popularity.setText(people.getPopularity() + "");
                    if (people.getBiography() != null)
                        biography.setText(people.getBiography());
                }
            }
            @Override
            public void onFailure(Call<PeopleDetails> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Looks like you are not connected to the internet!",Snackbar.LENGTH_LONG).show();

            }
        });

        //list of images
        Call<PeopleImagesArray> call1 = apiInterface.getPeopleImages(peopleId);
        call1.enqueue(new Callback<PeopleImagesArray>() {
            @Override
            public void onResponse(Call<PeopleImagesArray> call, Response<PeopleImagesArray> response) {
                PeopleImagesArray images1 = response.body();
                if (images1 != null) {
                    images.clear();
                    images.addAll(images1.getProfiles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PeopleImagesArray> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Looks like you are not connected to the internet!",Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
