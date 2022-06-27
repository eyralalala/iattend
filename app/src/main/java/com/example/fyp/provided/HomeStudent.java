package com.example.fyp.provided;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;

public class HomeStudent extends AppCompatActivity {

    private Button profile, schedule, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);
    }
}