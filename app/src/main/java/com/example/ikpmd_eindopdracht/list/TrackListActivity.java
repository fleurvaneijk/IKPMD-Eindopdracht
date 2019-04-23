package com.example.ikpmd_eindopdracht.list;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrackListActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ListView mListView;
    private TrackListAdapter trackAdapter;
    private List<Track> trackModels = new ArrayList<>();



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
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Tracks");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                Log.d("ERROR", "Failed to read value.", error.toException());
            }
        });
    }

    public void addToFavorites(View v) {

    }
}
