package com.example.ikpmd_eindopdracht;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ikpmd_eindopdracht.model.Track;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class AddTrackActivity extends AppCompatActivity {

    private StorageReference storageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private File imagePath = new File("/storage/emulated/0/Download/73igtt9khyd11.jpg");    //only works on stan's phone
    private File trackPath = new File("/storage/emulated/0/Download/Ariana Grande - Thank u, next.mp3");    //only works on stan's phone

    private Track track = new Track("", "", "", "", new Date(), "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);
    }


    public void pickImage(View v) {
        // TODO: 18/04/19 opens filechooser and remembers image
    }

    public void pickTrack(View v) {
        // TODO: 18/04/19 opens filechooser and remembers track
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void upload(View v) {
//        uploadImage();
//        uploadTrack();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void uploadImage(View v) {
        // TODO: 18/04/19 Filemanager openen i.p.v. hardoced jpg

        String filename = imagePath.toPath().getFileName().toString();

        Log.d("filename", "uploadImage: " + filename);

        Uri file = Uri.fromFile(imagePath);
        storageRef = FirebaseStorage.getInstance().getReference("images/");
        StorageReference imageRef = storageRef.child(filename);

        imageRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                track.setImageURL(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                Log.d("imgURL", "onSuccess: " + track.getImageURL().toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void uploadTrack(View v) {
        // TODO: 18/04/19 Filemanager openen i.p.v. hardoced jpg

        String filename = trackPath.toPath().getFileName().toString();

        Log.d("filename", "uploadTrack: " + filename);

        Uri file = Uri.fromFile(trackPath);
        storageRef = FirebaseStorage.getInstance().getReference("tracks/");
        StorageReference trackRef = storageRef.child(filename);

        trackRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                track.setTrackURL(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                Log.d("trackURL", "onSuccess: " + track.getTrackURL().toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    public void addTrack(View v) {
        // TODO: 18/04/19 add track model to database
        DatabaseReference myRef = this.database.getReference(this.track.getTitle());
        myRef.setValue(this.track);
    }

    public void filechooser(View v) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Log.d("filechooser", " 1e if");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Log.d("filechooser", " 2e if");

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("images/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1337);
            } else {

                Log.d("filechooser", "else");

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

}
