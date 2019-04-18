package com.example.ikpmd_eindopdracht.model;

import java.io.Serializable;
import java.util.Date;

public class Track implements Serializable {

    private String imageURL;
    private String title;
    private String artist;
    private String genre;
    private Date duration;
    private String trackURL;


    public Track(String imageURL, String title, String artist, String genre, Date duration, String trackURL) {
        this.imageURL = imageURL;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.trackURL = trackURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getTrackURL() {
        return trackURL;
    }

    public void setTrackURL(String track) {
        this.trackURL = track;
    }
}
