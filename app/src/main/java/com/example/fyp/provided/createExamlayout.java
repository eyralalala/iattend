package com.example.fyp.provided;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;

public class createExamlayout extends AppCompatActivity {

    EditText input_date, input_time;
    TextView showDate, showTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

    }
}