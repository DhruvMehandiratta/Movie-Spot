package com.androsol.moviespot.People;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.Activities.TVDetailsActivity;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 09-06-2017.
 */

public class PeopleTVAdapter extends RecyclerView.Adapter<PeopleTVAdapter.MyViewHolder> {

    private ArrayList<PeopleTV> tvList;
    private Context ctx;
    LayoutInflater inflater;

    public PeopleTVAdapter(ArrayList<PeopleTV> tvList, Context ctx) {
        this.tvList = tvList;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.pt_title);
            image = (ImageView) itemView.findViewById(R.id.pt_image);
        }

        @Override
        public void onClick(View view) {
            PeopleTV tv = tvList.get(getAdapterPosition());
            Intent i = new Intent(ctx, TVDetailsActivity.class);
            i.putExtra("Id",tv.getId());
            ctx.startActivity(i);
        }
    }

    @Override
    public PeopleTVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.people_tv_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PeopleTVAdapter.MyViewHolder holder, int position) {
        PeopleTV tv = tvList.get(position);
        if(tv.getName() != null)
            holder.title.setText(tv.getName());
        if(tv.getPoster_path() != null){
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + tv.getPoster_path()).into(holder.image);
        }else{
            holder.image.setImageResource(R.drawable.ic_nill_movies);
        }
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }
}
