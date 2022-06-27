package com.example.fyp.ui.student.frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fyp.databinding.FragmentStudentAboutBinding;

public class StudentAboutFragment extends Fragment {

    private FragmentStudentAboutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}