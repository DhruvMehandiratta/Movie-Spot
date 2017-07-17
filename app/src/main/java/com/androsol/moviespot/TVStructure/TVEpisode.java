package com.androsol.moviespot.TVStructure;

/**
 * Created by Dhruv on 01-05-2017.
 */

public class TVEpisode {
    private String air_date;
    private int episode_number;
    private String name;
    private String still_path;
    private long vote_count;
    private double vote_average;
    private String overview;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStill_path() {
        return still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
