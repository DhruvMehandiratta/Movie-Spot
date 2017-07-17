package com.androsol.moviespot.TVAdapters;

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
import com.androsol.moviespot.TVStructure.TV;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 30-04-2017.
 */

public class HorizontalTVAdapter extends RecyclerView.Adapter<HorizontalTVAdapter.MyTVViewHolder1>{
    private ArrayList<TV> tvList;
    private Context ctx;
    LayoutInflater inflater;
    public HorizontalTVAdapter(ArrayList<TV> tvList, Context ctx){
        this.ctx = ctx;
        this.tvList = tvList;
        inflater = LayoutInflater.from(ctx);
    }

    public class MyTVViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        ImageView image;

        public MyTVViewHolder1(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_card_title);
            image = (ImageView) itemView.findViewById(R.id.tv_card_image);
        }

        @Override
        public void onClick(View v) {
            TV tv = tvList.get(getAdapterPosition());
            Intent i = new Intent(ctx, TVDetailsActivity.class);
            i.putExtra("Id", tv.getId());
            ctx.startActivity(i);
        }

    }
        @Override
    public MyTVViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyTVViewHolder1 holder, int position) {

        TV tv = tvList.get(position);
        holder.title.setText(tv.getName());
        if(tv.getPoster_path() != null)
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + tv.getPoster_path()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if(tvList.size() != 0)
            return tvList.size();
        return 0;
    }
}
