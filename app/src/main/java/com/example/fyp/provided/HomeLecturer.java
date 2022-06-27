package com.example.fyp.provided;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fyp.R;

public class HomeLecturer extends AppCompatActivity implements View.OnClickListener {

    private Button profileL, scheduleL, aboutL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_lecturer);

        profileL = (Button) findViewById(R.id.profileL);
        profileL.setOnClickListener(this);

        scheduleL = (Button) findViewById(R.id.scheduleL);
        scheduleL.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileL:
                startActivity(new Intent(this, ProfileActivityLecturer.class));
                break;
            case R.id.scheduleL:
                startActivity(new Intent(this, L_StartActivity.class));
                break;
        }
    }
}