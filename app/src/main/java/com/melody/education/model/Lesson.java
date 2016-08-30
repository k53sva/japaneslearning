package com.melody.education.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by K53SV on 8/29/2016.
 */
public class Lesson {
    @SerializedName("id")
    private int id;
    @SerializedName("lesson")
    private String lesson;
    @SerializedName("description")
    private String description;
    @SerializedName("url_image")
    private String url_image;
    @SerializedName("url_audio")
    private String url_audio;
    @SerializedName("conversation")
    private String conversation;

    public Lesson(int id, String lesson, String description, String url_image, String url_audio, String conversation) {
        this.id = id;
        this.lesson = lesson;
        this.description = description;
        this.url_image = url_image;
        this.url_audio = url_audio;
        this.conversation = conversation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getUrl_audio() {
        return url_audio;
    }

    public void setUrl_audio(String url_audio) {
        this.url_audio = url_audio;
    }
}
