package com.androsol.moviespot.Watchlist;

/**
 * Created by Dhruv on 11-06-2017.
 */

public class WatchlistTV {
    public WatchlistTV(Long tv_id, String tv_title, Long id){
        this.tv_id = tv_id;
        this.id = id;
        this.title = tv_title;
    }
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTv_id() {
        return tv_id;
    }

    public void setTv_id(long tv_id) {
        this.tv_id = tv_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long tv_id;
    private long id;


}
