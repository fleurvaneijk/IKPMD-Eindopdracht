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
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class AddTrackActivity extends AppCompatActivity {

    private StorageReference storageRef;
    private File image = new File("FILL IN");

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

        String filename = image.toPath().getFileName().toString();

        Log.d("filename", "uploadImage: " + filename);

        Uri file = Uri.fromFile(image);
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

    private void uploadTrack() {
    }

    public void addTrack(View v) {
        // TODO: 18/04/19 add track model to database
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
