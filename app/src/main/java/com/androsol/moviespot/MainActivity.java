package com.androsol.moviespot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.Toast;

import com.androsol.moviespot.Activities.ContactUsActivity;
import com.androsol.moviespot.Activities.MovieSearchResultActivity;
import com.androsol.moviespot.Activities.TVActivity;
import com.androsol.moviespot.Activities.WatchlistActivity;
import com.androsol.moviespot.Fragments.VerticalFragment;
import com.androsol.moviespot.People.CelebritiesActivity;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    SharedPreferences.Editor editor;

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.splash_screen_layout);
        new CountDownTimer(3000, 2000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                MainActivity.this.setContentView(R.layout.activity_main);
                runCode();
            }
        }.start();
    }

    void runCode() {
        // background music
//        SharedPreferences sp = getSharedPreferences(Constants.SHOW_DIALOGBOX,MODE_PRIVATE);
//        Boolean enable = sp.getBoolean(Constants.SHOW_DIALOGBOX,false);
//        if(enable == null){
//            editor  = getSharedPreferences(Constants.SHOW_DIALOGBOX,MODE_PRIVATE).edit();
//            editor.putBoolean(Constants.ENABLE_DIALOGBOX,true);
//            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//            dialog.setMessage("Would you like to have background music?")
//                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Intent svc = new Intent(MainActivity.this, BackgroundSoundService.class);
//                            startService(svc);
//                        }
//                    })
//                    .setNegativeButton("Nope and remember my choice!", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            editor.putBoolean(Constants.ENABLE_DIALOGBOX,false);
//                            editor.commit();
//                        }
//                    })
//                    .create().show();
//        }
//
//        if(enable != null && enable == true){
//                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                dialog.setMessage("Would you like to have background music?")
//                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent svc = new Intent(MainActivity.this, BackgroundSoundService.class);
//                                startService(svc);
//                            }
//                        })
//                        .setNegativeButton("Nope and remember my choice!", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                    editor.putBoolean(Constants.ENABLE_DIALOGBOX,false);
//                                    editor.commit();
//                            }
//                        })
//                        .create().show();
//            }
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        collapsingToolbarLayout.setTitleEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Movies");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.my_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fragmentManager));
        viewPager.setCurrentItem(2);
    }

    static int count = 0;


    @Override
    public void onBackPressed() {
        // for no. of back pressed
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (count == 0) {
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                count++;
            } else {
                finish();
                count = 0;
            }
        }
    }

//    @Override
//    protected void onStop() {
//        Intent svc = new Intent(this, BackgroundSoundService.class);
//        stopService(svc);
//        super.onStop();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(MainActivity.this, MovieSearchResultActivity.class);
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
            Snackbar.make(MainActivity.this.findViewById(android.R.id.content), "Already in Movies :P", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.tv) {
            Intent i = new Intent(this, TVActivity.class);
            startActivity(i);
        }

        else if (id == R.id.nav_share) {
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
        } else if (id == R.id.people) {
            Intent i = new Intent(this, CelebritiesActivity.class);
            startActivity(i);
        } else if (id == R.id.watchlist) {
            Intent i = new Intent(this, WatchlistActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i = new Intent(this, ContactUsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

class MyAdapter extends FragmentStatePagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            VerticalFragment frag = new VerticalFragment();
            Bundle b = new Bundle();
            b.putString("Title", Constants.LATEST);
            frag.setArguments(b);
            return frag;
        }
        if (position == 1) {
            VerticalFragment frag = new VerticalFragment();
            Bundle b = new Bundle();
            b.putString("Title", Constants.TOP_RATED);
            frag.setArguments(b);
            return frag;
        }
        if (position == 2) {
            VerticalFragment frag = new VerticalFragment();
            Bundle b = new Bundle();
            b.putString("Title", Constants.POPULAR);
            frag.setArguments(b);
            return frag;
        }
        if (position == 3) {
            VerticalFragment frag = new VerticalFragment();
            Bundle b = new Bundle();
            b.putString("Title", Constants.UPCOMING);
            frag.setArguments(b);
            return frag;
        }
        if (position == 4) {
            VerticalFragment frag = new VerticalFragment();
            Bundle b = new Bundle();
            b.putString("Title", Constants.IN_THEATERS);
            frag.setArguments(b);
            return frag;
        } else {
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
        if (position == 0) {
            return "LATEST";
        }
        if (position == 1) {
            return "TOP-RATED";
        }
        if (position == 2) {
            return "POPULAR";
        }
        if (position == 3) {
            return "UPCOMING";
        }
        if (position == 4) {
            return "IN THEATERS";
        }
        return "";
    }
}
