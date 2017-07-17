package com.androsol.moviespot.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.androsol.moviespot.MainActivity;
import com.androsol.moviespot.People.CelebritiesActivity;
import com.androsol.moviespot.R;

public class ContactUsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView mLink, dhruv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contactus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mLink = (TextView) findViewById(R.id.link);
        dhruv = (TextView) findViewById(R.id.dhruv_id);
        //if(mLink !=  null){
         //   mLink.setMovementMethod(LinkMovementMethod.getInstance());
        //}
        if(dhruv == null){
            Toast.makeText(this, "Error bhenchof",Toast.LENGTH_LONG).show();
        }
        if(dhruv != null){
            dhruv.setMovementMethod(LinkMovementMethod.getInstance());
        }
        Linkify.addLinks(mLink,Linkify.ALL);
        Linkify.addLinks(dhruv,Linkify.ALL);

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
            Intent i = new Intent(this, CelebritiesActivity.class);
            startActivity(i);
        }
        else if(id == R.id.nav_contact_us){
            Snackbar.make(ContactUsActivity.this.findViewById(android.R.id.content),"You are already contacting us :P",Snackbar.LENGTH_LONG).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
