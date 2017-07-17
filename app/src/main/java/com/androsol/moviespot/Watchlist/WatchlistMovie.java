package com.androsol.moviespot.Watchlist;

/**
 * Created by Dhruv on 09-06-2017.
 */

public class WatchlistMovie {

    public WatchlistMovie(Long movie_id, String movie_title, Long id){
        this.movie_id = movie_id;
        this.id = id;
        this.title = movie_title;
    }

    private String title;
    private long movie_id;
    private long id;

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
