package com.androsol.moviespot.TVAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.R;
import com.androsol.moviespot.TVStructure.TVEpisode;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 01-05-2017.
 */

public class TVSeasonAdapter extends RecyclerView.Adapter<TVSeasonAdapter.MySeasonViewHolder> {
    private ArrayList<TVEpisode> episodes;
    private Context ctx;
    LayoutInflater inflater;

    public TVSeasonAdapter(ArrayList<TVEpisode> episodes,Context ctx){
        this.episodes = episodes;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public class MySeasonViewHolder extends RecyclerView.ViewHolder{
            public TextView nameText, dateText, overviewText, voteCountText, voteAverageText, episodeNoText;
            public ImageView imageView;

        public MySeasonViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.tv_episode_image);
            nameText = (TextView) itemView.findViewById(R.id.tv_episode_name);
            dateText = (TextView) itemView.findViewById(R.id.tv_episode_date);
            overviewText = (TextView) itemView.findViewById(R.id.tv_episode_overview_text);
            voteCountText = (TextView) itemView.findViewById(R.id.tv_episode_vote_count);
            voteAverageText = (TextView) itemView.findViewById(R.id.tv_episode_vote_average);
            episodeNoText = (TextView) itemView.findViewById(R.id.tv_episode_no_text);
        }

    }
    @Override
    public MySeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.tv_episodes_list_item,parent,false);
        return new MySeasonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MySeasonViewHolder holder, int position) {

        TVEpisode episode = episodes.get(position);
        holder.episodeNoText.setText(episode.getEpisode_number()+"");
        holder.voteAverageText.setText(episode.getVote_average()+"");
        holder.voteCountText.setText(episode.getVote_count()+"");
        holder.nameText.setText(episode.getName());
        holder.overviewText.setText(episode.getOverview());
        holder.dateText.setText(episode.getAir_date());
        if(episode.getStill_path() != null)
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + episode.getStill_path()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }
}
