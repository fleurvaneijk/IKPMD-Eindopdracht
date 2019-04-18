package com.example.ikpmd_eindopdracht.database;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class RealtimeDatabaseController extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private Object object;

    public synchronized void writeToDatabase(Object object, String path) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);
        myRef.setValue(object);
    }

    public synchronized Object readFromDatabase(String path) {
        final Object[] objectList = new Object[1];

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);

        final CountDownLatch done = new CountDownLatch(1);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                objectList[0] = value;
                done.countDown();
                Log.d("joopie", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("joopie", "Failed to read value.", error.toException());
            }
        });

        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return objectList[0];
    }

//    public void setObject(Object object) {
//        this.object = object;
//    }
//
//    public Object getObject() {
//        return this.object;
//    }
}
