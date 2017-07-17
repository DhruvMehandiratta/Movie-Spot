package com.androsol.moviespot.TVAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.Activities.TVDetailsActivity;
import com.androsol.moviespot.R;
import com.androsol.moviespot.TVStructure.TV;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 29-04-2017.
 */

public class VerticalTVAdapter extends RecyclerView.Adapter<VerticalTVAdapter.MyViewHolder> {
    private ArrayList<TV> tvList;
    private Context ctx;
    LayoutInflater inflater;

    public VerticalTVAdapter(ArrayList<TV> tvList, Context ctx) {
        this.tvList = tvList;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, release_date, vote_count, vote_average;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_card_vertical_list_title);
            image = (ImageView) itemView.findViewById(R.id.tv_card_vertical_list_image);
            release_date = (TextView) itemView.findViewById(R.id.tv_card_vertical_list_release_date);
            vote_count = (TextView) itemView.findViewById(R.id.tv_card_vertical_list_vote_count);
            vote_average = (TextView) itemView.findViewById(R.id.tv_card_vertical_list_vote_average);

        }

        @Override
        public void onClick(View view) {
            TV tv = tvList.get(getAdapterPosition());
            Intent i = new Intent(ctx,TVDetailsActivity.class);
            i.putExtra("Id", tv.getId());
            ctx.startActivity(i);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.vertical_tv_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TV tv = tvList.get(position);
        holder.title.setText(tv.getName());
        holder.release_date.setText(tv.getFirst_air_date());
        String voteAverage = Double.toString(tv.getVote_average());
        holder.vote_average.setText(voteAverage);
        String voteCount = tv.getVote_count()+"";
        holder.vote_count.setText(voteCount);

        // observation :- poster path can be null in latest sometimes
        if(tv.getPoster_path() != null)
            Log.d("poster", tv.getPoster_path());
        // observation :- picasso is handling poster path != null condition automatically
        if(tv.getPoster_path() != null)
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + tv.getPoster_path()).into(holder.image);
        else
            holder.image.setImageResource(R.drawable.ic_nill_movies);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }
}
