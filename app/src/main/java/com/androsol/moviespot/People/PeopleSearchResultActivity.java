package com.androsol.moviespot.People;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeopleSearchResultActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PeopleAdapter adapter;
    ArrayList<People> peopleList;
    TextView noOfResults;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_search_result);
        Intent i = getIntent();
        query = i.getStringExtra("Query");
        noOfResults = (TextView) findViewById(R.id.no_of_results_people);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewForPeopleSearch);
        noOfResults.setText("Searching Celebrity...");
        RecyclerView.LayoutManager mlayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        peopleList = new ArrayList<People>();
        adapter = new PeopleAdapter(peopleList, this);
        recyclerView.setAdapter(adapter);
        fetchData(1);
    }
    public void fetchData(final int i){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        if(i>5) {
            int size = this.peopleList.size();
            String text = size + " results found";
            noOfResults.setText(text);
            return;
        }
        Call<PeopleResults> call = apiInterface.getPeopleSearchResults(query,i);
        call.enqueue(new Callback<PeopleResults>() {
            @Override
            public void onResponse(Call<PeopleResults> call, Response<PeopleResults> response) {
                PeopleResults movies = response.body();
                if(movies != null) {
                        peopleList.addAll(movies.getResults());
                    adapter.notifyDataSetChanged();
                    fetchData(i + 1);
                }
            }

            @Override
            public void onFailure(Call<PeopleResults> call, Throwable t) {
                Snackbar.make(PeopleSearchResultActivity.this.findViewById(android.R.id.content),"Looks like you are not connected to the internet",Snackbar.LENGTH_LONG).show();

            }
        });
    }
}
