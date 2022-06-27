package com.example.fyp.ui.lecturer.frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp.databinding.FragmentLecturerHomeBinding;
import com.example.fyp.ui.lecturer.LecturerAttendance;
import com.example.fyp.ui.lecturer.LecturerExams;
import com.example.fyp.utils.BaseFragment;

public class LecturerHomeFragment extends BaseFragment {

    private FragmentLecturerHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLecturerHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cardCreateExam.setOnClickListener(v -> goTo(LecturerExams.class));
        binding.cardViewAttendance.setOnClickListener(v -> goTo(LecturerAttendance.class));
    }
}