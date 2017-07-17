package com.androsol.moviespot.MovieStructure;

/**
 * Created by Dhruv on 26-04-2017.
 */

public class Cast {
    private String name;
    private String profile_path;
    private long id;
    private String character;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
