package com.example.ikpmd_eindopdracht.model;

import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Track implements Serializable {

    private String image;
    private String title;
    private String artist;
    private String genre;
    private Date duration;
    private String trackURL;


    public Track(String image, String title, String artist, String genre, Date duration, String trackURL) {
        this.image = image;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.trackURL = trackURL;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
