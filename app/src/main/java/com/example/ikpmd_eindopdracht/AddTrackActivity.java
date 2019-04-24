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
import com.squareup.picasso.Picasso;

import java.io.File;

import dmax.dialog.SpotsDialog;

public class AddTrackActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_CODE = 1000;
    Button btn_upload;
    ImageView image_view;
    AlertDialog dialog;

    StorageReference storageReference;

    private File imagePath = new File("/storage/emulated/0/Download/73igtt9khyd11.jpg");    //only works on stan's phone
    private File trackPath = new File("/storage/emulated/0/Download/Ariana Grande - Thank u, next.mp3");    //only works on stan's phone

    private Track track = new Track("", "", "", "", 0, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        dialog = new SpotsDialog.Builder().setContext(this).build();
        btn_upload = (Button)findViewById(R.id.ConfirmBtn);

        storageReference = FirebaseStorage.getInstance().getReference("image_upload");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_CODE) {
            dialog.show();

            UploadTask uploadTask = storageReference.putFile(data.getData());

            Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()) {
                        Toast.makeText(AddTrackActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        String url = task.getResult().toString().substring(0, task.getResult().toString().indexOf("&token") - 1);
                        Log.d("DIRECTLINK", url);

                        dialog.dismiss();
                    }
                }
            });
        }
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

    public void getInputValues(View v) {
        String title = ((EditText)findViewById(R.id.TitleField)).getText().toString();
        String artist = ((EditText)findViewById(R.id.ArtistField)).getText().toString();
        String genre = ((EditText)findViewById(R.id.GenreField)).getText().toString();

        this.track.setTitle(title);
        this.track.setArtist(artist);
        this.track.setGenre(genre);
    }

//    public void addTrack(View v) {
//        // TODO: 18/04/19 add track model to database
//
//        DatabaseReference myRef = this.database.getReference(this.track.getTitle());
//        myRef.setValue(this.track);
//    }

//    public void filechooser(View v) {
////        Permission is not yet granted
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////            Permission has been previously asked and denied
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            }
////            Permission has not been asked yet -> so will ask
//            else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            }
//        }
////        Permission is granted
//        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
//        }
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            Uri returnUri = returnIntent.getData();
//            Log.d("uri", "" + returnUri);
//            Log.d("uri", "" + returnUri.getPath());
//
//            Uri file = Uri.fromFile(new File(returnUri.getPath()));
//            Log.d("file?", "" + file);
//
//            StorageReference riversRef = storageRef.child(file.getLastPathSegment());
//            UploadTask uploadTask = riversRef.putFile(file);
//
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle unsuccessful uploads
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                    // ...
//                }
//            });
//
//            Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
//            Log.d("cursor", "" + returnCursor);
//
//            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//            returnCursor.moveToFirst();
//            Log.d("filename","" + returnCursor.getString(nameIndex));
//        }
//    }
}
