package com.androsol.moviespot.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.MovieStructure.Cast;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 26-04-2017.
 */

public class CastListAdapter extends RecyclerView.Adapter<CastListAdapter.MyViewHolder> {
    private ArrayList<Cast> cast;
    private Context ctx;
    LayoutInflater inflater;

    public CastListAdapter(ArrayList<Cast> cast, Context ctx){
        this.cast = cast;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView castName, charName;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            castName = (TextView) itemView.findViewById(R.id.cast_name);
            charName = (TextView) itemView.findViewById(R.id.cast_character_name);
            imageView = (ImageView) itemView.findViewById(R.id.cast_image);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = inflater.inflate(R.layout.cast_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cast thisCast = cast.get(position);
        holder.castName.setText(thisCast.getName());
        holder.charName.setText(thisCast.getCharacter());
        if(thisCast.getProfile_path() != null)
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + thisCast.getProfile_path()).into(holder.imageView);
        else
            holder.imageView.setImageResource(R.drawable.ic_perm_identity_black_24dp);
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }
    }