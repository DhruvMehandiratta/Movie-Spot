package com.androsol.moviespot.Watchlist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androsol.moviespot.Activities.TVDetailsActivity;
import com.androsol.moviespot.Database.MyDBHelper;
import com.androsol.moviespot.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 11-06-2017.
 */

public class WatchlistTVAdapter extends RecyclerView.Adapter<WatchlistTVAdapter.MyViewHolder> {
    private ArrayList<WatchlistTV> tvList;
    private Context ctx;
    LayoutInflater inflater;
    ImageButton trashButton;

    public WatchlistTVAdapter(ArrayList<WatchlistTV> tvList, Context ctx) {
        this.tvList = tvList;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.wt_title);
            trashButton = (ImageButton) itemView.findViewById(R.id.remove_watchlist_button_tv);
            trashButton.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            if(view.getId() == R.id.remove_watchlist_button_tv){
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog.setMessage("Remove From Watchlist?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MyDBHelper helper = new MyDBHelper(ctx);
                                SQLiteDatabase db = helper.getWritableDatabase();
                                helper.deleteFromTVWatchlist(helper.WATCHLIST_TV_TABLE,tvList.get(getAdapterPosition()).getTv_id());
                                Snackbar.make(view,"Successfully removed from watchlist",Snackbar.LENGTH_LONG).show();
                                tvList.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create()
                        .show();
                return;
            }
            WatchlistTV tv = tvList.get(getAdapterPosition());
            Intent i = new Intent(ctx, TVDetailsActivity.class);
            i.putExtra("Id",tv.getTv_id());
            ctx.startActivity(i);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.watchlist_tv_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WatchlistTV tv = tvList.get(position);
        if(tv.getTitle() != null)
            holder.title.setText(tv.getTitle());
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }
}

