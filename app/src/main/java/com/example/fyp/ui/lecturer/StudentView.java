package com.example.fyp.ui.lecturer;

import android.os.Bundle;

import com.example.fyp.databinding.ActivityStudentViewBinding;
import com.example.fyp.provided.User;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;

import java.util.Locale;
import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "StudentView")
public class StudentView extends BaseActivity {
    private ActivityStudentViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User student = getObject(Constants.student, User.class);
        Objects.requireNonNull(getSupportActionBar()).setTitle(student.getFullName());
        binding.stName.setText(student.getFullName());
        binding.stEmail.setText(student.getEmail());
        binding.stPhone.setText(student.getPhoneNumber());
        binding.stAge.setText(String.format(Locale.getDefault(), "%s years", student.getAge()));
    }
}