package com.androsol.moviespot.People;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 07-06-2017.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.MyViewHolder> {

    private ArrayList<People> people;
    private Context ctx;
    LayoutInflater inflater;

    public PeopleAdapter(ArrayList<People> people, Context ctx){
        this.people = people;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView name, popularity;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.people_name);
            popularity = (TextView) itemView.findViewById(R.id.people_popularity);
            imageView = (ImageView) itemView.findViewById(R.id.people_image);
        }


        @Override
        public void onClick(View view) {
            People celeb = people.get(getAdapterPosition());
            Intent i = new Intent(PeopleAdapter.this.ctx, PeopleDetailsActivity.class);
            i.putExtra("PeopleId",celeb.getId());
            ctx.startActivity(i);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.people_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PeopleAdapter.MyViewHolder holder, int position) {
        People thisCast = people.get(position);
        holder.name.setText(thisCast.getName());
        holder.popularity.setText(thisCast.getPopularity()+"");
        if(thisCast.getProfile_path() != null)
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + thisCast.getProfile_path()).into(holder.imageView);
        else
            holder.imageView.setImageResource(R.drawable.ic_perm_identity_black_24dp);
    }

    @Override
    public int getItemCount() {
        if(people != null)
            return people.size();
        return 0;
    }
}
