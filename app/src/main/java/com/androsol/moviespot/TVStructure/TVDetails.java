package com.androsol.moviespot.TVStructure;

import java.util.ArrayList;

/**
 * Created by Dhruv on 30-04-2017.
 */

public class TVDetails {

    private String backdrop_path;
    private String overview;
    private String name;
    private String poster_path;
    private String first_air_date;
    private long number_of_seasons;
    private long number_of_episodes;
    private ArrayList<TVGenre> genres;
    private String status;
    private String last_air_date;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public long getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(long number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public long getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(long number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public ArrayList<TVGenre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<TVGenre> genres) {
        this.genres = genres;
    }
}
