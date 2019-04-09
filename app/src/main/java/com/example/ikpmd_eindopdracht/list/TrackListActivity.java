package com.example.ikpmd_eindopdracht.list;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ikpmd_eindopdracht.R;
import com.example.ikpmd_eindopdracht.model.Track;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackListActivity extends AppCompatActivity {

    private ListView mListView;
    private TrackListAdapter trackAdapter;
    private List<Track> trackModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

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
        trackAdapter = new TrackListAdapter(TrackListActivity.this, 0, trackModels);
        mListView.setAdapter(trackAdapter);
    }

    private void fillTheModels() {
        trackModels.add(new Track("Shield Frog", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
        trackModels.add(new Track("Underwater Disco", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
        trackModels.add(new Track("Ozone Drive", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
        trackModels.add(new Track("MAJOR ASS", "Color Glitch", "Electronic", new Date(1999, 6, 28, 0, 3, 12), MediaPlayer.create(getBaseContext(), R.raw.shieldfrog)));
    }


}
