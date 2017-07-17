package com.androsol.moviespot.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androsol.moviespot.MovieStructure.Crew;
import com.androsol.moviespot.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 29-04-2017.
 */

public class CrewListAdapter extends RecyclerView.Adapter<CrewListAdapter.MyViewHolder> {
    private ArrayList<Crew> crew;
    private Context ctx;
    LayoutInflater inflater;

    public CrewListAdapter(ArrayList<Crew> crew, Context ctx){
        this.crew = crew;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, job;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_text);
            job = (TextView) itemView.findViewById(R.id.job_text);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.crew_list_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Crew thisCrew = crew.get(position);
        holder.name.setText(thisCrew.getName());
        holder.job.setText(thisCrew.getJob());
    }

    @Override
    public int getItemCount() {
        return crew.size();
    }
}
