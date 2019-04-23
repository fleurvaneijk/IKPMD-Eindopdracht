package com.example.ikpmd_eindopdracht.database;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class RealtimeDatabaseController extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public synchronized void writeToDatabase(Object object, String path) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);
        myRef.setValue(object);
    }

    public synchronized Object readFromDatabase(String path) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);

        final Object[] objectList = new Object[1];

        final CountDownLatch done = new CountDownLatch(1);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                objectList[0] = value;
                done.countDown();

                Log.d("rdc", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("rdc", "Failed to read value.", error.toException());
            }
        });

        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return objectList[0];
    }
}
