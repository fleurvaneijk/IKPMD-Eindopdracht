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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    private StorageReference storageRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private File imagePath = new File("/document/image:148931");    //only works on stan's phone
    private File trackPath = new File("/storage/emulated/0/Download/Ariana Grande - Thank u, next.mp3");    //only works on stan's phone

    private Track track = new Track("", "", "", "", 0, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);
//        duration();
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
        //Permission is not yet granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Permission has been previously asked and denied
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }
            //Permission has not been asked yet -> so will ask
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        //Permission is granted
        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(intent, 1337);

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1 && resultCode == RESULT_OK) {


            String path = data.getData().getPath();
            Log.d("path", "" + path);

            File file = new File(data.getData().getPath());
            Log.d("onActivityResult", "" + file.getAbsolutePath());

            Uri file2 = Uri.fromFile(imagePath);
            Log.d("uri shit", "onActivityResult: " + file2);

            List<String> pathSegements = data.getData().getPathSegments();
            Log.d("pathsegements", "onActivityResult: " + pathSegements);


//            URL file3 =

            // Make sure the request was successful
            // The user picked a contact.
            // The Intent's data Uri identifies which contact was selected.
            // Do something with the contact here (bigger example below)
        }
    }


//    public void duration() {
//
//
//        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//        StorageReference riversRef = storageRef.child("images/rivers.jpg");
//
//        File localFile = null;
//        try {
//            localFile = File.createTempFile("images", "jpg");
//            riversRef.getFile(localFile)
//                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            // Successfully downloaded data to local file
//                            // ...
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle failed download
//                    // ...
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource(trackPath.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int duration = mediaPlayer.getDuration();
//        Log.d("duration:", "" + duration);
//    }
}
