package com.example.fyp.ui.lecturer.frags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fyp.databinding.FragmentLecturerProfileBinding;
import com.example.fyp.provided.MainActivity;
import com.example.fyp.provided.User;
import com.example.fyp.utils.BaseFragment;
import com.example.fyp.utils.Constants;

public class LecturerProfileFragment extends BaseFragment {
    private FragmentLecturerProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLecturerProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = getObject(Constants.currentUser, User.class);
        String fullName = user.getFullName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        binding.greeting.setText(String.format("Hello %s", firstName));
        binding.fullName.setText(user.getFullName());
        binding.emailAddress.setText(user.getEmail());
        binding.age.setText(String.format("%s yrs", user.getAge()));
        binding.ic.setText(user.getIcNumber());
        binding.phone.setText(user.getPhoneNumber());
        binding.signOut.setOnClickListener(v -> alert("Logout?", "Yes", (dialog, which) -> {
            getAuth().signOut();
            toast("Signed out successfully");
            goToWithFinishAffinity(MainActivity.class);
        }, "No", (dialog, which) -> dialog.cancel()));
    }
}