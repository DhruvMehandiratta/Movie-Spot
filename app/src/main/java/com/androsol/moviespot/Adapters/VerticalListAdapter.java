package com.androsol.moviespot.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.Activities.DetailsActivity;
import com.androsol.moviespot.MovieStructure.Movie;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 24-03-2017.
 */

public class VerticalListAdapter extends RecyclerView.Adapter<VerticalListAdapter.MyViewHolder> {
    private ArrayList<Movie> movies;
    private Context ctx;
    LayoutInflater inflater;

    public VerticalListAdapter(ArrayList<Movie> movies, Context ctx) {
        this.movies = movies;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }
     public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
         public TextView title, release_date, vote_count, vote_average;
         public ImageView image;

         public MyViewHolder(View itemView) {
             super(itemView);
             itemView.setOnClickListener(this);
             itemView.setOnLongClickListener(this);
                title = (TextView) itemView.findViewById(R.id.card_vertical_list_title);
                image = (ImageView) itemView.findViewById(R.id.card_vertical_list_image);
                release_date = (TextView) itemView.findViewById(R.id.card_vertical_list_release_date);
                vote_count = (TextView) itemView.findViewById(R.id.card_vertical_list_vote_count);
                vote_average = (TextView) itemView.findViewById(R.id.card_vertical_list_vote_average);

         }

         @Override
         public void onClick(View view) {
             Movie movie = movies.get(getAdapterPosition());
            Intent i = new Intent(ctx,DetailsActivity.class);
             i.putExtra("Id", movie.getId());
             ctx.startActivity(i);
         }

         @Override
         public boolean onLongClick(View view) {
        return false;
    }
}


@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.vertical_list_item,parent,false);
        return  new MyViewHolder(v);
        }

@Override
public void onBindViewHolder(VerticalListAdapter.MyViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.release_date.setText(movie.getRelease_date());
        String voteAverage = Double.toString(movie.getVote_average());
        holder.vote_average.setText(voteAverage);
        String voteCount = movie.getVote_count()+"";
        holder.vote_count.setText(voteCount);

        // observation :- poster path can be null in latest sometimes
        if(movie.getPoster_path() != null)
        Log.d("poster", movie.getPoster_path());
        // observation :- picasso is handling poster path != null condition automatically
        if(movie.getPoster_path() != null)
        Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + movie.getPoster_path()).into(holder.image);
        else
            holder.image.setImageResource(R.drawable.ic_nill_movies);
}

@Override
public int getItemCount() {
        return movies.size();
        }
        }
