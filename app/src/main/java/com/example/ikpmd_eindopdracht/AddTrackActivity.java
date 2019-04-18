package com.example.ikpmd_eindopdracht;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class AddTrackActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Log.d("filechooser", " 1e if");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Log.d("filechooser", " 2e if");

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {

                Log.d("filechooser", "else");

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }


    public void pickImage(View v) {
        // TODO: 18/04/19 opens filechooser and remembers image
    }

    public void pickTrack(View v) {
        // TODO: 18/04/19 opens filechooser and remembers track
    }

    public void upload(View v) {
        uploadImage();
        uploadTrack();
    }

    private void uploadImage() {
        // TODO: 18/04/19 Filemanager openen i.p.v. hardoced jpg
        Uri file = Uri.fromFile(new File("/storage/emulated/0/Download/73igtt9khyd11.jpg"));
        StorageReference riversRef = mStorageRef.child("images/test.jpg");

        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
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

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            Log.d("filechooser", " 1e if");
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//                Log.d("filechooser", " 2e if");

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("images/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1337);
//            } else {
//
//                Log.d("filechooser", "else");
//
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            }
//        }
    }

}
