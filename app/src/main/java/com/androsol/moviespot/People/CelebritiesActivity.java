package com.androsol.moviespot.People;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.androsol.moviespot.Activities.ContactUsActivity;
import com.androsol.moviespot.Activities.TVActivity;
import com.androsol.moviespot.Activities.WatchlistActivity;
import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.MainActivity;
import com.androsol.moviespot.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CelebritiesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    PeopleAdapter adapter;
    ArrayList<People> peopleList;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_people);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Celebrities");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        peopleList = new ArrayList<People>();
        recyclerView = (RecyclerView) findViewById(R.id.celebrities_list);
        RecyclerView.LayoutManager mlayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new PeopleAdapter(peopleList,this);
        recyclerView.setAdapter(adapter);

        fetchData();
    }
    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);
        peopleList.clear();
        fetchData1(1);
    }
        public void fetchData1(final int i){
            if(i > 15)
                return;
        Call<PeopleResults> call = apiInterface.getPeople(i);
        call.enqueue(new Callback<PeopleResults>() {
            @Override
            public void onResponse(Call<PeopleResults> call, Response<PeopleResults> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ArrayList<People> peopleArray = response.body().getResults();
                        if (peopleArray.size() != 0) {
                            peopleList.addAll(peopleArray);
                            adapter.notifyDataSetChanged();
                            fetchData1(i + 1);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<PeopleResults> call, Throwable t) {
                Snackbar.make(CelebritiesActivity.this.findViewById(android.R.id.content), "Looks like you are not connected to the internet!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(CelebritiesActivity.this, PeopleSearchResultActivity.class);
                i.putExtra("Query", query);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.movies) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        } else if (id == R.id.tv) {
            Intent i = new Intent(this, TVActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Movie Spot");
                String share = "\nDownload 'Movie Spot' for latest movies and TV shows! It's just awesome.\n\n";
                share = share + "https://play.google.com/store/apps/details?id="+getPackageName()+"";
                i.putExtra(Intent.EXTRA_TEXT, share);
                startActivity(Intent.createChooser(i,"Choose one"));
            }
            catch (Exception e){

            }
        }
        else if(id == R.id.watchlist){
            Intent i = new Intent(this, WatchlistActivity.class);
            startActivity(i);
        }

        else if(id == R.id.people){
            Snackbar.make(CelebritiesActivity.this.findViewById(android.R.id.content),"Already in Celebrities :P",Snackbar.LENGTH_LONG).show();
        }

        else if(id == R.id.nav_contact_us){
            Intent i = new Intent(this, ContactUsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}