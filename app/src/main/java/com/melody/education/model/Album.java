package com.melody.education.model;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Album {
    private String name;
    private String description;
    private int thumbnail;

    public Album() {
    }

    public Album(String name, String description, int thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
