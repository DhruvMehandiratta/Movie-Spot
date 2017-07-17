package com.androsol.moviespot.People;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androsol.moviespot.DisplayPhoto;
import com.androsol.moviespot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dhruv on 09-06-2017.
 */

public class PeopleImageAdapter extends RecyclerView.Adapter<PeopleImageAdapter.MyViewHolder> {
    private ArrayList<PeopleImage> images;
    private Context ctx;
    Long peopleId;
    LayoutInflater inflater;

    public PeopleImageAdapter(ArrayList<PeopleImage> images, Context ctx,Long peopleId) {
        this.ctx = ctx;
        this.images = images;
        inflater = LayoutInflater.from(ctx);
        this.peopleId  = peopleId;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.poeple_images_list_item, parent, false);
        return new MyViewHolder(v);    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PeopleImage image = images.get(position);
        if (image.getFile_path() != null)
            Picasso.with(ctx).load("https://image.tmdb.org/t/p/w300" + image.getFile_path()).into(holder.image);
        //Log.d("posterH", movie.getPoster_path());
    }

    @Override
    public int getItemCount() {
        if (images != null)
            return images.size();
        return 0;    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.one_people_image);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(ctx, DisplayPhoto.class);
            i.putExtra("Special","yes");
            i.putExtra("List","yes");
            i.putExtra("position",getAdapterPosition());
            String imageURL = "person/" + peopleId + "/images?api_key=41baf4a3e5c2e350d87b92554f894f2e";
            i.putExtra("PeopleId",peopleId);
            ctx.startActivity(i);
        }
    }
}