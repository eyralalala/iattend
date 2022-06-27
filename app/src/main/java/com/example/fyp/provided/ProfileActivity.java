package com.example.fyp.provided;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.fyp.R;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends BaseActivity {

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.signOut);
        logout.setOnClickListener(v -> {
            getAuth().signOut();
            goToWithFinishAffinity(MainActivity.class);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.profileId);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeId:
                    startActivity(new Intent(getApplicationContext(), HomeStudent.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.aboutId:
                    startActivity(new Intent(getApplicationContext(), studentAbout.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
        User user = getObject(Constants.currentUser, User.class);
        String fullName = user.getFullName();
        String firstName = fullName.substring(0, fullName.indexOf(' '));
        ((TextView) findViewById(R.id.greeting)).setText(String.format("Hello %s", firstName));
        ((TextView) findViewById(R.id.fullName)).setText(user.getFullName());
        ((TextView) findViewById(R.id.emailAddress)).setText(user.getEmail());
        ((TextView) findViewById(R.id.age)).setText(user.getAge());
        ((TextView) findViewById(R.id.ic)).setText(user.getIcNumber());
        ((TextView) findViewById(R.id.phone)).setText(user.getPhoneNumber());
    }
}

