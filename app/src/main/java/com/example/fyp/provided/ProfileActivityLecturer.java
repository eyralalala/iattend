package com.example.fyp.provided;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fyp.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivityLecturer extends AppCompatActivity {

    private FirebaseUser userL;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_lecturer);
    }
}