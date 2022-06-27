package com.example.fyp.ui.student;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fyp.data.Exam;
import com.example.fyp.databinding.ActivityStudentAttendanceHistoryBinding;
import com.example.fyp.helper.ExamsAdapter;
import com.example.fyp.utils.BaseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "StudentAttendanceHistory")
public class StudentAttendanceHistory extends BaseActivity {
    private ActivityStudentAttendanceHistoryBinding binding;
    private ArrayList<Exam> exams;
    private ExamsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentAttendanceHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerViewSetter();
        showProgressDialog("Loading attended exams ...");
        Objects.requireNonNull(getSupportActionBar()).setTitle("Attended Exams");
        getDatabaseReference().child("Students").child(Objects.requireNonNull(getAuth().getUid()))
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        hideProgressDialog();
                        for (DataSnapshot exam : snapshot.getChildren()) {
                            exams.add(exam.getValue(Exam.class));
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        hideProgressDialog();
                        toast("Failed to get exams");
                        log.severe(error.getMessage());
                    }
                });
    }

    private void recyclerViewSetter() {
        exams = new ArrayList<>();
        adapter = new ExamsAdapter(exams, true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            saveObject("exam", exams.get(position));
            goTo(StudentExamView.class);
        });
    }
}