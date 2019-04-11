package com.example.ikpmd_eindopdracht.database;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealtimeDatabaseController extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    public void writeToDatabase(Object object, String path) {
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);

        myRef.setValue(object);
    }

    public Object readFromDatabase(String path) {
        final Object[] objectList = new Object[1];

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(path);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                objectList[0] = value;

                returnValue(value);

                Log.d("joopie", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("joopie", "Failed to read value.", error.toException());
            }


        });
        System.out.println(objectList[0]);

        return objectList[0];
    }

    public Object returnValue(Object value) {
        return value;
    }

}
