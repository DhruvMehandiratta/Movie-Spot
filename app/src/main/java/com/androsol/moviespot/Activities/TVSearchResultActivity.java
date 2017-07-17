package com.androsol.moviespot.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.androsol.moviespot.ApiInterface;
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

public class TVSearchResultActivity extends AppCompatActivity {

    String query;
    VerticalTVAdapter adapter;
    ArrayList<TV> tvs;
    RecyclerView recyclerView;
    TextView noOfResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvsearch_result);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewForTVSearch);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        noOfResults = (TextView) findViewById(R.id.no_of_results_tv);
        noOfResults.setText("Searching TV...");
        Intent i = getIntent();
        query = i.getStringExtra("Query");
        tvs = new ArrayList<TV>();
        adapter = new VerticalTVAdapter(tvs,this);
        recyclerView.setAdapter(adapter);
        fetchData(1);
    }
    public void fetchData(final int i){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        if(i>5) {
            int size = this.tvs.size();
            String text = size + " results found";
            noOfResults.setText(text);
            return;
    }
        Call<TVResults> call = apiInterface.getTVSearchResults(query,i);
        call.enqueue(new Callback<TVResults>() {
            @Override
            public void onResponse(Call<TVResults> call, Response<TVResults> response) {
                TVResults tvArray = response.body();
                if (tvArray != null) {
                    TVSearchResultActivity.this.tvs.addAll(tvArray.getResults());
                    adapter.notifyDataSetChanged();
                    fetchData(i + 1);
                }
            }
            @Override
            public void onFailure(Call<TVResults> call, Throwable t) {
                Snackbar.make(TVSearchResultActivity.this.findViewById(android.R.id.content),"Looks like you are not connected to the internet",Snackbar.LENGTH_LONG).show();

            }
        });
    }
}

