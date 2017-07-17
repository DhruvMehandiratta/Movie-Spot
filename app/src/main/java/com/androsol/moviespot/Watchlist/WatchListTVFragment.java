package com.androsol.moviespot.Watchlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androsol.moviespot.Database.MyDBHelper;
import com.androsol.moviespot.R;

import java.util.ArrayList;

/**
 * Created by Dhruv on 10-06-2017.
 */

public class WatchListTVFragment extends Fragment {
    RecyclerView recyclerView;
    WatchlistTVAdapter adapter;
    ArrayList<WatchlistTV> tvList;
    MyDBHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.watchlist_tv_fragment,container, false);
        tvList = new ArrayList<WatchlistTV>();
        helper = new MyDBHelper(getActivity());
        recyclerView = (RecyclerView) v.findViewById(R.id.watchlist_recyclerview_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new WatchlistTVAdapter(tvList, getContext());
        recyclerView.setAdapter(adapter);
        setUpViews();
        return v;
    }
    public void setUpViews(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String query = "SELECT * FROM "+helper.WATCHLIST_TV_TABLE + ";";
        Cursor c = db.rawQuery(query,null);
        tvList.clear();
        c.moveToFirst();
        while(!c.isAfterLast()){
            long id = c.getInt(c.getColumnIndex(helper.TVTABLE_COLUMN_ID));
            String title = c.getString(c.getColumnIndex(helper.TVTABLE_COLUMN_TITLE));
            Long tv_id = c.getLong(c.getColumnIndex(helper.TVTABLE_COLUMN_TV_ID));
            Log.d("dhruvIDofTV",tv_id+"");
            WatchlistTV tv = new WatchlistTV(tv_id, title, id);
            tvList.add(tv);
            c.moveToNext();
        }
        Log.d("dhruvSIZEOFTV",tvList.size()+"");
        db.close();
        adapter.notifyDataSetChanged();

    }
}
