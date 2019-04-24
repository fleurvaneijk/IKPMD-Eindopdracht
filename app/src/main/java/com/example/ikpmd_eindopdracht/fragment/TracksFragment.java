package com.example.ikpmd_eindopdracht.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ikpmd_eindopdracht.AddTrackActivity;
import com.example.ikpmd_eindopdracht.R;
import com.example.ikpmd_eindopdracht.list.TrackListAdapter;
import com.example.ikpmd_eindopdracht.model.Track;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TracksFragment extends Fragment {

    private ListView mListView;
    private TrackListAdapter trackAdapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseRef;

    private List<Track> trackModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.fillTrackList();
        return inflater.inflate(R.layout.fragment_tracks, container, false);
    }

    private void fillTrackList() {
        this.databaseRef = this.database.getReference("Tracks");

        this.databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                trackModels.clear();

                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Track track = d.getValue(Track.class);
                    trackModels.add(track);
                }

                trackAdapter = new TrackListAdapter(getActivity(), 0, trackModels);
                mListView.setAdapter(trackAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("ERROR", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = getView().findViewById(R.id.track_list);
    }
}
