package com.example.fyp.ui.student.frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp.databinding.FragmentStudentHomeBinding;
import com.example.fyp.ui.student.StudentAttendanceHistory;
import com.example.fyp.ui.student.StudentExams;
import com.example.fyp.utils.BaseFragment;

public class StudentHomeFragment extends BaseFragment {
    private FragmentStudentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cardViewExams.setOnClickListener(v -> {
            goTo(StudentExams.class);
        });
        binding.cardAttendance.setOnClickListener(v -> {
            goTo(StudentAttendanceHistory.class);
        });
    }
}