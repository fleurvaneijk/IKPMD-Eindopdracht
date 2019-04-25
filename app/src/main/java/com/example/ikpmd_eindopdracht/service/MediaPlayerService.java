package com.example.ikpmd_eindopdracht.service;

import android.media.MediaPlayer;

import java.io.IOException;

public class MediaPlayerService extends Thread {

    private static MediaPlayerService mediaPlayerService;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String trackURL;

    private int currentPosition = 0;

    public MediaPlayerService(String url) {
        this.trackURL = url;
    }

//    public static MediaPlayerService getInstance(String url){
//
//
//
//        if(mediaPlayerService == null){
//            mediaPlayerService = new MediaPlayerService();
//        }
//        return mediaPlayerService;
//    }

    @Override
    public void run() {
        if (!mediaPlayer.isPlaying() && currentPosition == 0) {
            playTrack();
        } else if (mediaPlayer.isPlaying()) {
            pauseTrack();
        } else {
            resumeTrack();
        }
    }

    public void playTrack() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(trackURL);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseTrack() {
        currentPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.stop();
    }

    public void resumeTrack() {
        mediaPlayer.seekTo(currentPosition);
    }

    public void stopTrack() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}
