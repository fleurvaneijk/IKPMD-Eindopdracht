package com.example.ikpmd_eindopdracht;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signOut(View v) {
        Log.d("kaas", "signing out");
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignInActivity.class));

    }
}
