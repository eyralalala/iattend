package com.example.fyp.ui.student;

import android.os.Bundle;
import android.view.Menu;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.fyp.R;
import com.example.fyp.databinding.ActivityStudentHomeBinding;
import com.example.fyp.utils.Constants;
import com.google.firebase.messaging.FirebaseMessaging;

import lombok.extern.java.Log;

@Log(topic = "Student Home")
public class StudentHome extends AppCompatActivity {
    private ActivityStudentHomeBinding binding;
    private NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.student)
                .addOnSuccessListener(unused -> log.info("Subscribed successfully")).addOnFailureListener(e -> log.info(e.getMessage()));
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.st_nav_host_fragment);
        assert navHostFragment != null;
        controller = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, controller);
        setupBottomBar();
    }

    private void setupBottomBar() {
        PopupMenu popupMenu = new PopupMenu(this, null);
        popupMenu.inflate(R.menu.menu_bottom);
        Menu menu = popupMenu.getMenu();
        binding.bottomBar.setupWithNavController(menu, controller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return controller.navigateUp() || super.onSupportNavigateUp();
    }
}