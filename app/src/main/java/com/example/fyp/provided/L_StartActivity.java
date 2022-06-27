package com.example.fyp.provided;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;

public class L_StartActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView createExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l__start);


        createExam = (ImageView) findViewById(R.id.createExam);
        createExam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(L_StartActivity.this, createExam.class));
        return;

    }
}