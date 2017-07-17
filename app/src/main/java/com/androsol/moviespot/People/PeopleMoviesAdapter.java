package com.androsol.moviespot.People;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.Activities.DetailsActivity;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Dhruv on 09-06-2017.
 */

public class PeopleMoviesAdapter extends RecyclerView.Adapter<PeopleMoviesAdapter.MyViewHolder> {

    private ArrayList<PeopleMovie> movies;
    private Context ctx;
    LayoutInflater inflater;

    public PeopleMoviesAdapter(ArrayList<PeopleMovie> movies, Context ctx) {
        this.movies = movies;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, release_date;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.pm_title);
            image = (ImageView) itemView.findViewById(R.id.pm_image);
            release_date = (TextView) itemView.findViewById(R.id.pm_date);
        }

        @Override
        public void onClick(View view) {
            PeopleMovie movie = movies.get(getAdapterPosition());
            Intent i = new Intent(ctx, DetailsActivity.class);
            i.putExtra("Id",movie.getId());
            ctx.startActivity(i);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.people_movies_list_item,parent,false);
        return new MyViewHolder(v);
     }

    @Override
    public void onBindViewHolder(PeopleMoviesAdapter.MyViewHolder holder, int position) {
        PeopleMovie movie = movies.get(position);
        if(movie.getOriginal_title() != null)
        holder.title.setText(movie.getOriginal_title());
        if(movie.getRelease_date() != null)
        holder.release_date.setText(movie.getRelease_date());
        if(movie.getPoster_path() != null){
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + movie.getPoster_path()).into(holder.image);
        }else{
            holder.image.setImageResource(R.drawable.ic_nill_movies);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    }