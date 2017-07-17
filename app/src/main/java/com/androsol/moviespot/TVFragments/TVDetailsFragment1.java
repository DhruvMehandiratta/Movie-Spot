package com.androsol.moviespot.TVFragments;

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

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.DisplayPhoto;
import com.androsol.moviespot.R;
import com.androsol.moviespot.TVAdapters.HorizontalTVAdapter;
import com.androsol.moviespot.TVStructure.TV;
import com.androsol.moviespot.TVStructure.TVDetails;
import com.androsol.moviespot.TVStructure.TVGenre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhruv on 30-04-2017.
 */

public class TVDetailsFragment1 extends Fragment {

    long tv_id;
    TextView synopsis, firstAir, lastAir, status, genre, seasons;
    ImageView imageView;
    ArrayList<TV> tvList;
    RecyclerView recyclerView;
    HorizontalTVAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tv_details_fragment1,container,false);
        Bundle b = getArguments();
        tv_id = b.getLong("TVId");
        seasons = (TextView) v.findViewById(R.id.tv_season_text);
        synopsis = (TextView) v.findViewById(R.id.tv_synopsisText);
        genre = (TextView)v.findViewById(R.id.tv_genre);
        firstAir = (TextView) v.findViewById(R.id.firstText);
        lastAir = (TextView) v.findViewById(R.id.lastText);
        status = (TextView) v.findViewById(R.id.statusText);
        tvList = new ArrayList<TV>();
        //recyclerView = (RecyclerView) v.findViewById(R.id.tv_horizontal_list_for_fragment);
        //adapter = new HorizontalTVAdapter(tvList, getContext());
        //recyclerView.setAdapter(adapter);
        //recyclerView.setNestedScrollingEnabled(false);

        imageView = (ImageView) v.findViewById(R.id.tv_image_logo_final);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DisplayPhoto.class);
                i.putExtra("TVId",tv_id);
                i.putExtra("TYPE","TV");
                i.putExtra("Differentiate","Fragment");
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
        Call<TVDetails> tvDetails = apiInterface.getTVDetails(tv_id);
        tvDetails.enqueue(new Callback<TVDetails>() {
            @Override
            public void onResponse(Call<TVDetails> call, Response<TVDetails> response) {
                TVDetails tv = response.body();
                if (tv != null) {

                    //synopsis

                    if (tv.getOverview() != null && !tv.getOverview().equals(""))
                        synopsis.setText(tv.getOverview());
                    else
                        synopsis.setText("Not Available");

                    //release date
                    if (tv.getFirst_air_date() != null && !tv.getFirst_air_date().equals(""))
                        firstAir.setText(tv.getFirst_air_date());
                    else
                        firstAir.setText("Not Available");

                    //last date
                    if (tv.getLast_air_date() != null && !tv.getLast_air_date().equals(""))
                        lastAir.setText(tv.getLast_air_date());
                    else
                        lastAir.setText("Not Available");

                    //genre
                    ArrayList<TVGenre> genres = tv.getGenres();
                    String mGenre = "";
                    for (int i = 0; i < genres.size() - 1; i++) {
                        mGenre += genres.get(i).getName() + ", ";
                    }
                    if (genres.size() != 0)
                        mGenre += genres.get(genres.size() - 1).getName();
                    else
                        mGenre = "Not Specfied";
                    genre.setText(mGenre);

                    //status
                    if (tv.getStatus() != null && !tv.getStatus().equals("")) {
                        status.setText(tv.getStatus());
                    } else {
                        status.setText("Not Available");
                    }

                    //seasons
                    if (tv.getNumber_of_episodes() != 0 && tv.getNumber_of_seasons() != 0) {
                        seasons.setText(tv.getNumber_of_seasons() + "/" + tv.getNumber_of_episodes());
                    } else {
                        seasons.setText("Not Specified");
                    }
                    //image
                    String imageURL = tv.getPoster_path();
                    Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w300" + imageURL).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<TVDetails> call, Throwable t) {
                Toast.makeText(getActivity(),"Poor Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

         //call for horizontal list
        //some problem is here....will rectify later
//        Call<TVResults> similarTV = apiInterface.getSimilarTV(tv_id);
//        similarTV.enqueue(new Callback<TVResults>() {
//            @Override
//            public void onResponse(Call<TVResults> call, Response<TVResults> response) {
//                TVResults similarResults = response.body();
//                tvList.clear();
//                if(similarResults == null){
//                    Log.d("dhruvPROOOOOOO","hai");
//                    return;
//                }
//                tvList.addAll(similarResults.getResults());
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<TVResults> call, Throwable t) {
//                Toast.makeText(getActivity(), "Looks like you are not connected to the internet!", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}
