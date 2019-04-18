package com.example.ikpmd_eindopdracht;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ikpmd_eindopdracht.database.RealtimeDatabaseController;
import com.example.ikpmd_eindopdracht.model.Track;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    private RealtimeDatabaseController rdc = new RealtimeDatabaseController();
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void write(View v) {
//        rdc.writeToDatabase("Fleur", "message");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tracks");
//        DatabaseReference tracksRef = ref.child("tracks");

        Map<String, Track> tracks = new HashMap<>();

//        tracks.put("", new Track());
//        tracks.put("", new Track());
//        tracks.put("", new Track());
//        tracks.put("", new Track());
//        tracks.put("", new Track());

        ref.setValue(tracks);
    }

    public void read(View v) {
        Object object = rdc.readFromDatabase("message");
        System.out.println(object);
    }

    public void openAddTrackActivity(View v) {
        startActivity(new Intent(MainActivity.this, AddTrackActivity.class));
    }

    public void playSong(View v) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/ikpmd-eindopdracht.appspot.com/o/07%20Skinny%20Dip.mp3?alt=media&token=0410cd97-628d-4ed7-8cc9-8e2cad301634");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
