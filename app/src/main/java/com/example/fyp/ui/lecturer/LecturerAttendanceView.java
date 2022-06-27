package com.example.fyp.ui.lecturer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fyp.data.Exam;
import com.example.fyp.databinding.ActivityLecturerAttendanceViewBinding;
import com.example.fyp.helper.StudentsAdapter;
import com.example.fyp.provided.User;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "LecturerAttendanceView")
public class LecturerAttendanceView extends BaseActivity {
    private ActivityLecturerAttendanceViewBinding binding;
    private ArrayList<User> students;
    private StudentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLecturerAttendanceViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Students");
        Exam exam = getObject("exam", Exam.class);
        recyclerViewSetter(exam);
        getDatabaseReference().child("ExamAttendance").child(exam.getExamId())
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            binding.emptyMsg.setVisibility(View.GONE);
                            for (DataSnapshot student : snapshot.getChildren()) {
                                students.add(student.getValue(User.class));
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            binding.emptyMsg.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        toast("Unable to get students");
                        log.severe(error.getMessage());
                    }
                });

    }

    private void recyclerViewSetter(Exam exam) {
        students = new ArrayList<>();
        adapter = new StudentsAdapter(students, exam.getCourseName());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnStudentClickListener(position -> {
            saveObject(Constants.student, students.get(position));
            goTo(StudentView.class);
        });
    }
}