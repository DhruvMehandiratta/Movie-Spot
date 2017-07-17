package com.androsol.moviespot;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import com.androsol.moviespot.MovieStructure.MovieDetails;
import com.androsol.moviespot.People.PeopleDetails;
import com.androsol.moviespot.People.PeopleImage;
import com.androsol.moviespot.People.PeopleImagesArray;
import com.androsol.moviespot.TVStructure.TVDetails;
import com.androsol.moviespot.TVStructure.TVSeason;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayPhoto extends AppCompatActivity {
    ImageView imageView;
    Intent i;
    String title, imageURL;
    long movieId;
    static String ans, type1;
    long season_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.photo_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageView = (ImageView) findViewById(R.id.image_full);
        i = getIntent();

        //search for people image
        String special = i.getStringExtra("Special");
        if (special != null) {
            String list = i.getStringExtra("List");
            if (list.equals("no")) {
                Long peopleId = i.getLongExtra("PeopleId", 0);
                fetchDataForPeople(peopleId);
            } else if (list.equals("yes")) {
                Long peopleId = i.getLongExtra("PeopleId", 0);
                int position = i.getIntExtra("position", 0);
                fetchDataForPeople1(peopleId, position);
            }
        } else {
            ans = i.getStringExtra("Differentiate");
            type1 = i.getStringExtra("TYPE");
            if (type1 != null && type1.equals("Season")) {
                season_number = i.getLongExtra("Season_number", 0);
            }
            fetchData();
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        if (type1 != null && type1.equals("MOVIE")) {
            movieId = i.getLongExtra("MovieId", 0);
            Call<MovieDetails> call = apiInterface.getMovieDetails(movieId);
            call.enqueue(new Callback<MovieDetails>() {

                @Override
                public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                    MovieDetails movie = response.body();
                    if (movie != null) {
                        title = movie.getTitle();

                        if (ans.equals("Activity"))
                            imageURL = movie.getBackdrop_path();
                        else if (ans.equals("Fragment"))
                            imageURL = movie.getPoster_path();
                        setImage(imageURL);
                    }
                }

                public void setImage(String imageURL) {
                    Picasso.with(DisplayPhoto.this).load("https://image.tmdb.org/t/p/w500" + imageURL).into(imageView);
                }

                @Override
                public void onFailure(Call<MovieDetails> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.activity_display_photo), "Looks like internet is not connected!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else if (type1 != null && type1.equals("TV")) {
            movieId = i.getLongExtra("TVId", 0);
            Call<TVDetails> call = apiInterface.getTVDetails(movieId);
            call.enqueue(new Callback<TVDetails>() {
                @Override
                public void onResponse(Call<TVDetails> call, Response<TVDetails> response) {
                    TVDetails movie = response.body();
                    if (movie != null) {
                        title = movie.getName();
                        if (ans.equals("Activity"))
                            imageURL = movie.getBackdrop_path();
                        else
                            imageURL = movie.getPoster_path();
                        setImage(imageURL);
                    }
                }

                public void setImage(String imageURL) {
                    Picasso.with(DisplayPhoto.this).load("https://image.tmdb.org/t/p/w500" + imageURL).into(imageView);
                }

                ;

                @Override
                public void onFailure(Call<TVDetails> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.activity_display_photo), "Looks like internet is not connected!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else if (type1 != null && type1.equals("Season")) {
            movieId = i.getLongExtra("TVId", 0);
            Call<TVSeason> call = apiInterface.getTVSeason(movieId, season_number);
            call.enqueue(new Callback<TVSeason>() {
                @Override
                public void onResponse(Call<TVSeason> call, Response<TVSeason> response) {
                    TVSeason movie = response.body();
                    if (movie != null) {
                        title = movie.getName();
                        imageURL = movie.getPoster_path();
                        setImage(imageURL);
                    }
                }

                @Override
                public void onFailure(Call<TVSeason> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.activity_display_photo), "Looks like internet is not connected!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }


    ///only for people
    public void fetchDataForPeople(Long peopleId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<PeopleDetails> call = apiInterface.getPeopleDetails(peopleId);
        call.enqueue(new Callback<PeopleDetails>() {
            @Override
            public void onResponse(Call<PeopleDetails> call, Response<PeopleDetails> response) {
                PeopleDetails people = response.body();
                if (people != null) {
                    title = people.getName();
                    imageURL = people.getProfile_path();
                    setImage(imageURL);
                }

            }

            @Override
            public void onFailure(Call<PeopleDetails> call, Throwable t) {
                Snackbar.make(findViewById(R.id.activity_display_photo), "Looks like internet is not connected!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void fetchDataForPeople1(Long peopleId, final int position) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<PeopleImagesArray> call = apiInterface.getPeopleImages(peopleId);
        call.enqueue(new Callback<PeopleImagesArray>() {
            @Override
            public void onResponse(Call<PeopleImagesArray> call, Response<PeopleImagesArray> response) {
                PeopleImagesArray array = response.body();
                if (array != null) {
                    PeopleImage image = array.getProfiles().get(position);
                    imageURL = image.getFile_path();
                    setImage(imageURL);
                }
            }

            @Override
            public void onFailure(Call<PeopleImagesArray> call, Throwable t) {
                Snackbar.make(findViewById(R.id.activity_display_photo), "Looks like internet is not connected!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpaper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            onBackPressed();
//        }
        if (item.getItemId() == R.id.back_button) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.set_as_wallpaper) {
            imageView.buildDrawingCache();
            final Bitmap bitmap = imageView.getDrawingCache();
            final WallpaperManager manager = WallpaperManager.getInstance(this);
            final AlertDialog dialog = new AlertDialog.Builder(this)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                manager.setBitmap(bitmap);
                                Snackbar.make(DisplayPhoto.this.findViewById(android.R.id.content), "Wallpaper successfully applied!", Snackbar.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setMessage("Set as wallpaper?")
                    .show();
        }
        if (item.getItemId() == R.id.i_like_this_image) {
            file_download("https://image.tmdb.org/t/p/w500" + imageURL);
            Snackbar.make(this.findViewById(android.R.id.content), "Saved to device successfully!", Snackbar.LENGTH_LONG).show();
        }
        return true;
    }

    public void setImage(String imageURL) {
        Picasso.with(DisplayPhoto.this).load("https://image.tmdb.org/t/p/w500" + imageURL).into(imageView);
    }


    //first
    public void file_download(String url) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/appUp_files");
        if (!direct.exists()) {
            direct.mkdirs();
        }
        DownloadManager mgr = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/appUp_files", "test.jpg");

        mgr.enqueue(request);
    }
}