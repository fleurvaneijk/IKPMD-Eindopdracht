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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackListActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    private ListView mListView;
    private TrackListAdapter trackAdapter;
    private List<Track> trackModels = new ArrayList<>();

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

    public void addTrackToDatabase(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TestTrack1");
        myRef.setValue(new Track("https://firebasestorage.googleapis.com/v0/b/ikpmd-eindopdracht.appspot.com/o/images%2Fa_1.jpeg?alt=media&token=11308a2f-bc14-4ce3-bb1b-33ef93d1243f",
                                    "Thank you, next", "Ariana Grande", "Pop", 150,
                            "https://firebasestorage.googleapis.com/v0/b/ikpmd-eindopdracht.appspot.com/o/tracks%2FAriana%20Grande%20-%20Thank%20u%2C%20next.mp3?alt=media&token=a5c41817-9b6a-4757-a67a-6d53ab2e4bf4"));
    }


    private void fillTheModels() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Tracks");

        // TODO: 21/04/19 THREADS (ASYNC)
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

//        trackModels.add(new Track("https://firebasestorage.googleapis.com/v0/b/ikpmd-eindopdracht.appspot.com/o/images%2Fa_1.jpeg?alt=media&token=11308a2f-bc14-4ce3-bb1b-33ef93d1243f",
//                "Shield Frog", "Color Glitch", "Electronic",1, "https://firebasestorage.googleapis.com/v0/b/ikpmd-eindopdracht.appspot.com/o/tracks%2FAriana%20Grande%20-%20Thank%20u%2C%20next.mp3?alt=media&token=a5c41817-9b6a-4757-a67a-6d53ab2e4bf4"));


//        trackModels.add(new Track("@drawable/twee","Underwater Disco", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/drie", "Ozone Drive", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/vier", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/vijf", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/zes", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/zeven", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/acht", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/negen", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/tien", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
//        trackModels.add(new Track("@drawable/ozone", "MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
    }

    public void startPlaying (View v) {
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.shieldfrog);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    public void addToFavorites(View v) {

    }


}
