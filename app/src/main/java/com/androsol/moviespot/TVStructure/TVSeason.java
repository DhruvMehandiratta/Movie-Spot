package com.androsol.moviespot.TVStructure;

import java.util.ArrayList;

/**
 * Created by Dhruv on 01-05-2017.
 */

public class TVSeason {
    private int id;
    private String name;
    private String overview;
    private int season_number;
    private String poster_path;
    private String air_date;
    private ArrayList<TVEpisode> episodes;


    public long getId() {
        return id;
    }

    public ArrayList<TVEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<TVEpisode> episodes) {
        this.episodes = episodes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }
}
