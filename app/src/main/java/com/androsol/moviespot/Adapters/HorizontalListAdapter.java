package com.androsol.moviespot.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
 * Created by Dhruv on 23-03-2017.
 */

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.MyViewHolder> {
    private ArrayList<Movie> movies;
    private Context ctx;
    LayoutInflater inflater;


    public HorizontalListAdapter(ArrayList<Movie> movies, Context ctx){
        this.ctx = ctx;
        this.movies = movies;
        inflater = LayoutInflater.from(ctx);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView title ;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            title = (TextView) itemView.findViewById(R.id.card_title);
            image = (ImageView) itemView.findViewById(R.id.card_image);
        }
        @Override
        public void onClick(View v) {
            Movie movie = movies.get(getAdapterPosition());
            Intent i = new Intent(ctx, DetailsActivity.class);
            i.putExtra("Id",movie.getId());
            ctx.startActivity(i);
        }



        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }


@Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v= inflater.inflate(R.layout.horizontal_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        if(movie.getPoster_path() != null)
        Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + movie.getPoster_path()).into(holder.image);
        //Log.d("posterH", movie.getPoster_path());
    }

    @Override
    public int getItemCount() {
        if(movies != null)
        return movies.size();
        return 0;
    }
}
