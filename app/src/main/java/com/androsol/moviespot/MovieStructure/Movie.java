package com.androsol.moviespot.MovieStructure;

/**
 * Created by Dhruv on 23-03-2017.
 */

public class Movie {
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    private double vote_average;
    private long vote_count;
    private long id;
    private String original_language;

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public Movie(String title, String image1, String release_date, float vote_average, int vote_count){
        this.poster_path = image1;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Movie(String original_title, String overview, String image1) {
        this.title = original_title;
        this.overview = overview;
        this.poster_path = image1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
