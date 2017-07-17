package com.androsol.moviespot.MovieStructure;

/**
 * Created by Dhruv on 29-04-2017.
 */

public class Crew {
    private String name;
    private String job;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
