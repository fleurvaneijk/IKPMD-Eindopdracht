package com.example.ikpmd_eindopdracht;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ikpmd_eindopdracht.database.RealtimeDatabaseController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    RealtimeDatabaseController rdc = new RealtimeDatabaseController();
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void write(View v) {
        rdc.writeToDatabase("Fleur", "message");
    }

    public void read(View v) {
        Object object = rdc.readFromDatabase("message");
        System.out.println(object);
    }

}
