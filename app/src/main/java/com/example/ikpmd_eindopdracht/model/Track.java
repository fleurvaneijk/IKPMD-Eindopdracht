package com.example.ikpmd_eindopdracht.model;

import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Track implements Serializable {

    private String title;
    private String artist;
    private String genre;
    private Date duration;
    private MediaPlayer track;

    public Track(String title, String artist, String genre, Date duration, MediaPlayer track) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.track = track;
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

    public MediaPlayer getTrack() {
        return track;
    }

    public void setTrack(MediaPlayer track) {
        this.track = track;
    }
}
