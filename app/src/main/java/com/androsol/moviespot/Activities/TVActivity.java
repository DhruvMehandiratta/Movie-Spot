package com.androsol.moviespot.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.androsol.moviespot.Constants;
import com.androsol.moviespot.MainActivity;
import com.androsol.moviespot.People.CelebritiesActivity;
import com.androsol.moviespot.R;
import com.androsol.moviespot.TVFragments.VerticalTVFragment;

public class TVActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_tv);
        collapsingToolbarLayout.setTitleEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TV");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager = (ViewPager) findViewById(R.id.my_tv_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyTVAdapter(fragmentManager));
        viewPager.setCurrentItem(2);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
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
                Intent i = new Intent(TVActivity.this, TVSearchResultActivity.class);
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
            Snackbar.make(TVActivity.this.findViewById(android.R.id.content),"Already in TV :P",Snackbar.LENGTH_LONG).show();
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
            Intent i = new Intent(this, CelebritiesActivity.class);
            startActivity(i);
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

class MyTVAdapter extends FragmentStatePagerAdapter {

    public MyTVAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            VerticalTVFragment frag = new VerticalTVFragment();
            Bundle b = new Bundle();
            b.putString("Title", Constants.LATEST);
            frag.setArguments(b);
            return frag;
        }
        if(position == 1){
            VerticalTVFragment frag = new VerticalTVFragment();
            Bundle b = new Bundle();
            b.putString("Title",Constants.TOP_RATED);
            frag.setArguments(b);
            return frag;
        }
        if(position == 2){
            VerticalTVFragment frag = new VerticalTVFragment();
            Bundle b = new Bundle();
            b.putString("Title",Constants.POPULAR);
            frag.setArguments(b);
            return frag;
        }
        if(position == 3){
            VerticalTVFragment frag = new VerticalTVFragment();
            Bundle b = new Bundle();
            b.putString("Title",Constants.AIRING_TODAY);
            frag.setArguments(b);
            return frag;
        }
        if(position == 4){
            VerticalTVFragment frag = new VerticalTVFragment();
            Bundle b = new Bundle();
            b.putString("Title",Constants.AIRING_THIS_WEEK);
            frag.setArguments(b);
            return frag;
        }
        else{
            return null;
        }
    }

    @Override
    public int getCount() {
        //Log.d("countOfFrags","4");
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "LATEST";
        }
        if(position == 1){
            return "TOP-RATED";
        }
        if(position == 2){
            return "POPULAR";
        }
        if(position == 3){
            return "AIRING TODAY";
        }
        if(position == 4){
            return "AIRING THIS WEEK";
        }
        return "";
    }
}

