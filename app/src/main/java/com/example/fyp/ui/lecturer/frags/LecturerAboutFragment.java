package com.example.fyp.ui.lecturer.frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fyp.R;
import com.example.fyp.databinding.FragmentLecturerAboutBinding;

public class LecturerAboutFragment extends Fragment {

    private FragmentLecturerAboutBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLecturerAboutBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_lecturer_about, container, false);
    }
}