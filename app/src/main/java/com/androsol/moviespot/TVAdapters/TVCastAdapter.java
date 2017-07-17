package com.androsol.moviespot.TVAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.moviespot.R;
import com.androsol.moviespot.TVStructure.TVCast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 01-05-2017.
 */

public class TVCastAdapter extends RecyclerView.Adapter<TVCastAdapter.MyTVCastViewHolder> {
    private ArrayList<TVCast> cast;
    private Context ctx;
    LayoutInflater inflater;
    public TVCastAdapter(ArrayList<TVCast> cast, Context ctx){
        this.cast = cast;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    public class MyTVCastViewHolder extends RecyclerView.ViewHolder {
        public TextView castName, charName;
        public ImageView imageView;

        public MyTVCastViewHolder(View itemView) {
            super(itemView);
            castName = (TextView) itemView.findViewById(R.id.tv_cast_name);
            charName = (TextView) itemView.findViewById(R.id.tv_cast_character_name);
            imageView = (ImageView) itemView.findViewById(R.id.tv_cast_image);
        }
    }
    @Override
    public MyTVCastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.tv_cast_list_item,parent,false);
        return new MyTVCastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyTVCastViewHolder holder, int position) {
        TVCast thisCast = cast.get(position);
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
