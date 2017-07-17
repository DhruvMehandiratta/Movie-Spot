package com.androsol.moviespot.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androsol.moviespot.Adapters.ReviewsListAdapter;
import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.MovieStructure.Review;
import com.androsol.moviespot.MovieStructure.ReviewResults;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Dhruv on 20-04-2017.
 */

public class ReviewsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Review> reviews;
    ReviewsListAdapter adapter;
    long movie_id;
    View v;
    CoordinatorLayout l;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.reviews_fragment,container,false);
        l= (CoordinatorLayout) v.findViewById(R.id.review_fragment_main_layout);
        recyclerView = (RecyclerView) v.findViewById(R.id.reviews_list);
        RecyclerView.LayoutManager mlayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        reviews = new ArrayList<Review>();
        adapter = new ReviewsListAdapter(reviews,getActivity());
        recyclerView.setAdapter(adapter);
        Bundle b = getArguments();
        movie_id = b.getLong("MovieId");
        fetchData(1);
        return v;
    }
    public void fetchData(final int i){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        if(i > 3){
            return;
        }
        Call<ReviewResults> call = apiInterface.getReviews(movie_id);
        call.enqueue(new Callback<ReviewResults>() {
            @Override
            public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<Review> array1 = response.body().getResults();
                        if (array1.size() == 0) {
                            Log.d("dhruv", "inside " + getView());
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "No reviews available!", Snackbar.LENGTH_LONG)
                                    .show();

                        } else {
                            reviews.clear();
                            reviews.addAll(array1);
                            adapter.notifyDataSetChanged();
                            fetchData(i+1);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ReviewResults> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Looks like you are not connected to the internet!",Snackbar.LENGTH_LONG).show();
            }
        });

    }


}
