package com.androsol.moviespot.Watchlist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androsol.moviespot.Activities.DetailsActivity;
import com.androsol.moviespot.Database.MyDBHelper;
import com.androsol.moviespot.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 09-06-2017.
 */

public class WatchlistMovieAdapter extends RecyclerView.Adapter<WatchlistMovieAdapter.MyViewHolder>{
    private ArrayList<WatchlistMovie> movies;
    private Context ctx;
    LayoutInflater inflater;
    ImageButton trashButton;

    public WatchlistMovieAdapter(ArrayList<WatchlistMovie> movies, Context ctx) {
        this.movies = movies;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.wm_title);
            trashButton = (ImageButton) itemView.findViewById(R.id.remove_watchlist_button);
            trashButton.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if(view.getId() == R.id.remove_watchlist_button){
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog.setMessage("Remove From Watchlist?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyDBHelper helper = new MyDBHelper(ctx);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        helper.deleteFromMoviesWatchList(helper.WATCHLIST_MOVIE_TABLE,movies.get(getAdapterPosition()).getMovie_id());
                        Snackbar.make(view,"Successfully removed from watchlist",Snackbar.LENGTH_LONG).show();
                        movies.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create()
                        .show();
                return;
            }
            WatchlistMovie movie = movies.get(getAdapterPosition());
            Intent i = new Intent(ctx, DetailsActivity.class);
            i.putExtra("Id",movie.getMovie_id());
            ctx.startActivity(i);
        }
    }

    @Override
    public WatchlistMovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.watchlist_movie_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WatchlistMovieAdapter.MyViewHolder holder, int position) {
        WatchlistMovie movie = movies.get(position);
        if(movie.getTitle() != null)
            holder.title.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
