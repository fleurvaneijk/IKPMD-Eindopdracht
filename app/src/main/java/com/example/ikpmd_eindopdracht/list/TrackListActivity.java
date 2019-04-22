package com.example.ikpmd_eindopdracht.list;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ikpmd_eindopdracht.R;
import com.example.ikpmd_eindopdracht.model.Track;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackListActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    private ListView mListView;
    private TrackListAdapter trackAdapter;
    private List<Track> trackModels = new ArrayList<>();
    private Track selectedTrack;

    public TrackListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);
        setTitle(R.string.tracks_title);

        mListView = (ListView) findViewById(R.id.track_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast t = Toast.makeText(TrackListActivity.this,
                        "Click" + position,Toast.LENGTH_LONG);
                t.show();
            }
        });

        fillTheModels();

    }

    private void fillTheModels() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Tracks");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                List<Track> trackList;

                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Track track = d.getValue(Track.class);
                    trackModels.add(track);
                }

                trackAdapter = new TrackListAdapter(TrackListActivity.this, 0, trackModels);
                mListView.setAdapter(trackAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });
    }

    // TODO: 22/04/19 app crashes when switching song (too much on his plate :c) 
    public void startPlaying () {

        String trackPath = this.selectedTrack.getTrackURL();

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(trackPath);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if(mp.isPlaying()){
                        mp.stop();
                    }
                    mp.start();
                }
            });

            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedTrack(Track selectedTrack){
        this.selectedTrack = selectedTrack;
        this.startPlaying();
    }

    public void addToFavorites(View v) {

    }


}
