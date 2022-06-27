package com.example.fyp.ui.student;

import android.os.Bundle;

import com.example.fyp.data.Exam;
import com.example.fyp.databinding.ActivityStudentExamViewBinding;
import com.example.fyp.provided.User;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "StudentExamView")
public class StudentExamView extends BaseActivity {
    private ActivityStudentExamViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentExamViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Exam exam = getObject("exam", Exam.class);
        String examKey = String.format(Locale.getDefault(), "attended_%s", exam.getExamId());
        binding.attendButton.setEnabled(!getPreferenceBoolean(examKey));
        Objects.requireNonNull(getSupportActionBar()).setTitle(String.format("%s (%s)", exam.getCourseName(), exam.getCourseCode()));
        binding.courseCode.setText(exam.getCourseCode());
        binding.courseName.setText(exam.getCourseName());
        binding.examType.setText(exam.getExamType());
        binding.examDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(exam.getExamDate()));
        binding.examTime.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(exam.getExamEndTime()));
        long duration = exam.getExamEndTime() - exam.getExamStartTime();
        int minutes = (int) (duration / 60000L);
        binding.examDuration.setText(String.format(Locale.getDefault(), "%d minutes", minutes));
        binding.attendButton.setOnClickListener(v -> {
            long now = System.currentTimeMillis();
            long tensBeforeStart = exam.getExamStartTime() - (10 * 60000L);
            long tensAfterStart = exam.getExamStartTime() + (10 * 60000L);
            if (now >= tensBeforeStart && now <= tensAfterStart) {
                showProgressDialog("Marking attendance ...");
                getDatabaseReference().child("Students").child(Objects.requireNonNull(getAuth().getUid())).child(exam.getExamId()).setValue(exam)
                        .addOnSuccessListener(unused -> {
                            getDatabaseReference().child("ExamAttendance").child(exam.getExamId())
                                    .child(getAuth().getUid()).setValue(getObject(Constants.currentUser, User.class))
                                    .addOnSuccessListener(unused1 -> {
                                        hideProgressDialog();
                                        toast("Attendance Marked");
                                        binding.attendButton.setEnabled(false);
                                        savePreferenceBoolean(examKey, true);
                                    });
                        }).addOnFailureListener(e -> {
                    toast("Failed to mark attendance");
                    log.severe(e.getMessage());
                });
            } else {
                longToast("You can only attend 10 minutes before or after exam started.");
            }
        });
    }
}