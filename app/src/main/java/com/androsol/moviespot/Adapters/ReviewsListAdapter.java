package com.androsol.moviespot.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androsol.moviespot.MovieStructure.Review;
import com.androsol.moviespot.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 20-04-2017.
 */

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.MyViewHolder> {

    private ArrayList<Review> reviews;
    private Context ctx;
    LayoutInflater inflater;

    public ReviewsListAdapter(ArrayList<Review> reviews, Context ctx) {
        this.reviews = reviews;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView authorText, contentText;

        public MyViewHolder(View itemView) {
            super(itemView);
            authorText = (TextView) itemView.findViewById(R.id.author_text);
            contentText = (TextView) itemView.findViewById(R.id.content_text);
        }

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.reviews_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.authorText.setText(reviews.get(position).getAuthor());
        holder.contentText.setText(reviews.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
