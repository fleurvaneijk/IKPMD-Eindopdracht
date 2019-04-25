package com.example.ikpmd_eindopdracht;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ikpmd_eindopdracht.model.Track;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import dmax.dialog.SpotsDialog;

public class AddTrackActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_CODE = 1000;
    Button btn_upload;
    ImageView image_view;
    AlertDialog dialog;
    String filename;
    StorageReference imageStorageReference;
    StorageReference trackStorageReference;

    private Track track = new Track("", "", "", "", 0, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        dialog = new SpotsDialog.Builder().setContext(this).build();

        this.pickImage();
        this.pickTrack();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_CODE) {
            dialog.show();


            Cursor returnCursor = getContentResolver().query(data.getData(), null, null, null, null);
            Log.d("cursor", "" + returnCursor);

            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            Log.d("filename","" + returnCursor.getString(nameIndex));
            this.filename = returnCursor.getString(nameIndex);

            UploadTask uploadTask = null;

            if(filename.contains(".jpg") || filename.contains(".jpeg") || filename.contains(".png")){
                imageStorageReference = FirebaseStorage.getInstance().getReference("images/" + this.filename);
                uploadTask = imageStorageReference.putFile(data.getData());
            }
            else if(filename.contains(".mp3") || filename.contains(".mpeg")){
                trackStorageReference = FirebaseStorage.getInstance().getReference("tracks/" + this.filename);
                uploadTask = trackStorageReference.putFile(data.getData());
            }

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()) {
                        Toast.makeText(AddTrackActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                    if(filename.contains(".jpg") || filename.contains(".jpeg") || filename.contains(".png")){

                        imageStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadURL) {
                                track.setImageURL(downloadURL.toString());
                                Log.d("imageURL", "onComplete: " + downloadURL);
                                dialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }
                    else if(filename.contains(".mp3") || filename.contains(".mpeg") || filename.contains(".flac")){

                        trackStorageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadURL) {
                                track.setTrackURL(downloadURL.toString());
                                Log.d("trackURL", "onComplete: " + downloadURL);
                                dialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Track Uploaded", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }
                    return null;
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public void getInputValues() {
        String title = ((EditText)findViewById(R.id.TitleField)).getText().toString();
        String artist = ((EditText)findViewById(R.id.ArtistField)).getText().toString();
        String genre = ((EditText)findViewById(R.id.GenreField)).getText().toString();

        this.track.setTitle(title);
        this.track.setArtist(artist);
        this.track.setGenre(genre);
    }

    public void addTrack(View v) {
        // TODO: 18/04/19 add track model to database
        this.getInputValues();
        Log.d("TRACK", "addTrack: " + track.getTrackURL() + track.getImageURL() + track.getTitle());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tracks/" + track.getArtist() + " - " + track.getTitle());
        myRef.setValue(track);
    }

    public void pickImage() {
        // TODO: 18/04/19 opens filechooser and remembers image

        btn_upload = (Button)findViewById(R.id.ImagePicker);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_CODE);
            }
        });
    }

    public void pickTrack() {
        // TODO: 18/04/19 opens filechooser and remembers track
        btn_upload = (Button)findViewById(R.id.TrackPicker);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_CODE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void upload(View v) {
//        uploadImage();
//        uploadTrack();
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void uploadImage(View v, Uri fili) {
//        // TODO: 18/04/19 Filemanager openen i.p.v. hardoced jpg
//
//        String filename = imagePath.toPath().getFileName().toString();
//        Log.d("filename", "uploadImage: " + filename);
//
//        Uri file = Uri.fromFile(imagePath);
//        storageRef = FirebaseStorage.getInstance().getReference("images/");
//        StorageReference imageRef = storageRef.child(filename);
//
//        imageRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                track.setImageURL(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                Log.d("imgURL", "onSuccess: " + track.getImageURL().toString());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//
//            }
//        });
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void uploadTrack(View v) {
//        // TODO: 18/04/19 Filemanager openen i.p.v. hardoced jpg
//
//        String filename = trackPath.toPath().getFileName().toString();
//
//        Log.d("filename", "uploadTrack: " + filename);
//
//        Uri file = Uri.fromFile(trackPath);
//        storageRef = FirebaseStorage.getInstance().getReference("tracks/");
//        StorageReference trackRef = storageRef.child(filename);
//
//        trackRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                track.setTrackURL(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                Log.d("trackURL", "onSuccess: " + track.getTrackURL().toString());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//
//            }
//        });
//    }




}
