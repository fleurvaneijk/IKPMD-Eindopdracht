package com.example.ikpmd_eindopdracht;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.ikpmd_eindopdracht.database.RealtimeDatabaseController;
import com.example.ikpmd_eindopdracht.fragment.AccountFragment;
import com.example.ikpmd_eindopdracht.fragment.PlaylistFragment;
import com.example.ikpmd_eindopdracht.fragment.StatisticsFragment;
import com.example.ikpmd_eindopdracht.fragment.TracksFragment;
import com.example.ikpmd_eindopdracht.model.Track;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;

    private RealtimeDatabaseController rdc = new RealtimeDatabaseController();

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TracksFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_tracklist);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_tracklist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TracksFragment()).commit();
                break;
            case R.id.nav_playlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlaylistFragment()).commit();
                break;
            case R.id.nav_statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatisticsFragment()).commit();
                break;
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                break;
            case R.id.nav_logout:
                onSignOutClicked();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onSignOutClicked() {
        //sign out
    }

    public void write(View v) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("tracks");
        Map<String, Track> tracks = new HashMap<>();

        myRef.setValue(tracks);
    }

    public void read(View v) {
        Object object = rdc.readFromDatabase("message");
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
