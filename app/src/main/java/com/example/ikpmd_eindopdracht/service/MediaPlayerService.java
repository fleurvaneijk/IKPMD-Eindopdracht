package com.example.ikpmd_eindopdracht.service;

import android.media.MediaPlayer;

import java.io.IOException;

public class MediaPlayerService extends Thread {

    private static MediaPlayerService mediaPlayerService;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private  String trackURL;

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
        playTrack();
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
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void resumeTrack() {
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    public void stopTrack() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}
