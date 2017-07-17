package com.androsol.moviespot.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.DisplayPhoto;
import com.androsol.moviespot.R;
import com.androsol.moviespot.TVAdapters.TVSeasonAdapter;
import com.androsol.moviespot.TVStructure.TVEpisode;
import com.androsol.moviespot.TVStructure.TVSeason;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeasonActivity extends AppCompatActivity {

    TextView name, number, overview;
    ImageView image;
    RecyclerView recyclerView;
    long season_number;
    long tv_id;
    String image_url;
    TVSeasonAdapter adapter;
    ArrayList<TVEpisode> episodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        Intent i = getIntent();
        season_number = i.getLongExtra("SeasonNumber",0);
        tv_id = i.getLongExtra("TV_ID",0);

        name = (TextView) findViewById(R.id.season_name_text);
        image = (ImageView) findViewById(R.id.season_photo);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeasonActivity.this, DisplayPhoto.class);
                i.putExtra("TVId",tv_id);
                i.putExtra("TYPE","Season");
                i.putExtra("Differentiate","Fragment"); // although not required here
                i.putExtra("Season_number",season_number);
                startActivity(i);
            }
        });
        number = (TextView) findViewById(R.id.season_id_text);
        overview = (TextView) findViewById(R.id.season_overview_text);
        recyclerView = (RecyclerView) findViewById(R.id.episodes_list);
        ViewCompat.setNestedScrollingEnabled(recyclerView,false);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        episodes = new ArrayList<TVEpisode>();
        adapter = new TVSeasonAdapter(episodes, this);
        recyclerView.setAdapter(adapter);
        fetchData();
    }
    public void fetchData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<TVSeason> season = apiInterface.getTVSeason(tv_id, season_number);
        season.enqueue(new Callback<TVSeason>() {
            @Override
            public void onResponse(Call<TVSeason> call, Response<TVSeason> response) {
                TVSeason season = response.body();
                if (season != null) {
                    String s_name = season.getName();
                    name.setText(s_name);
                    int s_no = season.getSeason_number();
                    number.setText(s_no + "");
                    String s_overview = season.getOverview();
                    overview.setText(s_overview);
                    image_url = season.getPoster_path();
                    setImage(image_url);

                    episodes.clear();
                    episodes.addAll(season.getEpisodes());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<TVSeason> call, Throwable t) {
                Snackbar.make(SeasonActivity.this.findViewById(android.R.id.content),"Looks like you are not connected to the internet",Snackbar.LENGTH_LONG).show();
            }
        });

    }

    public void setImage(String image_url){
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + image_url).into(image);
    }
}
