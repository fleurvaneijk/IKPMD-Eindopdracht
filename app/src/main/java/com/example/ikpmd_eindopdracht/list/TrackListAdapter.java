package com.example.ikpmd_eindopdracht.list;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ikpmd_eindopdracht.R;
import com.example.ikpmd_eindopdracht.model.Track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import service.MediaPlayerService;

public class TrackListAdapter extends ArrayAdapter<Track> {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<MediaPlayerService> list = new ArrayList<MediaPlayerService>();

    private class ViewHolder {
        AppCompatImageView image;
        TextView title;
        TextView artist;
        TextView genre;
        TextView duration;
    }

    public TrackListAdapter(Context context, int resource, List<Track> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (convertView == null ) {
            vh = new ViewHolder();
            LayoutInflater li = LayoutInflater.from(getContext());
            convertView = li.inflate(R.layout.view_content_row, parent, false);
            vh.image = convertView.findViewById(R.id.subject_image);
            vh.title = convertView.findViewById(R.id.subject_title);
            vh.artist = convertView.findViewById(R.id.subject_artist);
            vh.genre = convertView.findViewById(R.id.subject_genre);
            vh.duration = convertView.findViewById(R.id.subject_duration);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final Track track = getItem(position);

//        Image img = track.getImageURL();
//        vh.image.setImageURI(track.getImageURL());
        vh.title.setText(track.getTitle());
        vh.artist.setText(track.getArtist());
        vh.genre.setText(track.getGenre());


        convertView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayerService mediaPlayerService = new MediaPlayerService(track.getTrackURL());

                if(!list.isEmpty()) {
                    System.out.println(list.get(0));
                    MediaPlayerService thread = list.get(0);
                    thread.stopTrack();
                    list.clear();
                    list.add(mediaPlayerService);
                }
                else {
                    list.add(mediaPlayerService);
                }

                mediaPlayerService.start();

            }
        });

        return convertView;
    }


//    class PlayMusicThread extends Thread {
//
//        private volatile PlayMusicThread playMusicThread;
//
//        String TAG = "PlayMusicThread";
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        String trackURL;
//
//        private PlayMusicThread() {
//        }
//
//        public PlayMusicThread getInstance(){
//            return playMusicThread;
//        }
//
//
//        public void setTrackURL(String trackURL){
//            this.trackURL = trackURL;
//        }
//
//        @Override
//        public void run() {
//            try {
//                if(mediaPlayer.isPlaying()){
//                    Log.d(TAG, "run: yoooo stopt");
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                }
//                mediaPlayer.setDataSource(trackURL);
//                mediaPlayer.prepareAsync();
//                mediaPlayer.start();
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        try {
//                            mp.prepare();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        mp.start();
//                    }
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    // TODO: 22/04/19 app crashes when switching song (too much on his plate :c)
//    public void startPlaying (Track track) {
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource(track.getTrackURL());
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    if(mp.isPlaying()){
//                        mp.stop();
//                    }
//                    mp.start();
//                }
//            });
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
