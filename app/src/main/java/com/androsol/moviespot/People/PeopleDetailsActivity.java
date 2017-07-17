package com.androsol.moviespot.People;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.androsol.moviespot.ApiInterface;
import com.androsol.moviespot.DisplayPhoto;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.androsol.moviespot.People.PeopleDetailsActivity.people_id;

public class PeopleDetailsActivity extends AppCompatActivity {

    ImageView image;
    static long people_id;
    ViewPager viewPager;
    String imageURL, mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_people_details);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        image = (ImageView) findViewById(R.id.people_big_poster);

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.people_nest_scroll_view);
        scrollView.setFillViewport(true);

        Intent i = getIntent();
        people_id = i.getLongExtra("PeopleId", 0);
        Log.d("dhruvIDIDIDID",people_id+"");
        fetchData();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PeopleDetailsActivity.this, DisplayPhoto.class);
                i.putExtra("Special", "yes");
                i.putExtra("List","no");
                i.putExtra("PeopleId",people_id);
                startActivity(i);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.people_details_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyPeopleAdapter1(fragmentManager));

    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<PeopleDetails> call = apiInterface.getPeopleDetails(people_id);
        call.enqueue(new Callback<PeopleDetails>() {
            @Override
            public void onResponse(Call<PeopleDetails> call, Response<PeopleDetails> response) {
                PeopleDetails people = response.body();
                if (people != null) {
                    imageURL = people.getProfile_path();
                    mTitle = people.getName();
                    setImage(imageURL, mTitle);
                }
            }

            @Override
            public void onFailure(Call<PeopleDetails> call, Throwable t) {
                Snackbar.make(PeopleDetailsActivity.this.findViewById(android.R.id.content), "Looks like you are not connected to the internet!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void setImage(String imageURL, String title) {
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500" + imageURL).into(image);
        getSupportActionBar().setTitle(title);
    }
}
class MyPeopleAdapter1 extends FragmentStatePagerAdapter {

    public MyPeopleAdapter1(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            PeopleDetailsFragment1 frag = new PeopleDetailsFragment1();
            Bundle b = new Bundle();
            b.putLong("PeopleId", people_id);
            frag.setArguments(b);
            return frag;
        }
        if (position == 1) {
            PeopleMoviesFragment frag = new PeopleMoviesFragment();
            Bundle b = new Bundle();
            b.putLong("PeopleId",people_id);
            frag.setArguments(b);
            return frag;        }
        if (position == 2) {
            //change it
            PeopleTVFragment frag = new PeopleTVFragment();
            Bundle b = new Bundle();
            b.putLong("PeopleId",people_id);
            frag.setArguments(b);
            return frag;
        } else {
            return null;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "BIO";
        }
        if (position == 1) {
            return "MOVIES";
        }
        if(position == 2){
            return "TV";
        }
        return "";
    }
}


