package com.example.fyp.provided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LecturerLogin extends AppCompatActivity implements View.OnClickListener {

    private TextView registerL, forgotPasswordL, lecturerL;
    private EditText editTextEmailL, editTextPasswordL;
    private Button signInL;

    private FirebaseAuth mAuth;
    private ProgressBar progressBarL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_login);

        registerL = (TextView) findViewById(R.id.registerL);
        registerL.setOnClickListener(this);

        signInL = (Button) findViewById(R.id.signInL);
        signInL.setOnClickListener(this);

        editTextEmailL = (EditText) findViewById(R.id.emailL);
        editTextPasswordL = (EditText) findViewById(R.id.passwordL);

        forgotPasswordL = (TextView) findViewById(R.id.forgotpasswordL);
        forgotPasswordL.setOnClickListener(this);


        progressBarL = (ProgressBar) findViewById(R.id.progressBarL);

        mAuth =FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerL:
                startActivity(new Intent(LecturerLogin.this, RegisterUserL.class));
                break;

            case R.id.signInL:
                userLLogin();
                break;

            case R.id.forgotpasswordL:
                startActivity(new Intent(LecturerLogin.this, HomeLecturer.class));
                break;
        }
    }

    private void userLLogin() {
        String emailL = editTextEmailL.getText().toString().trim();
        String passwordL = editTextPasswordL.getText().toString().trim();

        if (emailL.isEmpty()){
            editTextEmailL.setError("Email is required!");
            editTextEmailL.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailL).matches()){
            editTextEmailL.setError("Please enter a valid Email!");
            editTextEmailL.requestFocus();
            return;
        }

        if (passwordL.isEmpty()){
            editTextPasswordL.setError("Password is required!");
            editTextPasswordL.requestFocus();
            return;
        }

        if (passwordL.length() < 6){
            editTextPasswordL.setError("Min password length is 6 characters!");
            editTextPasswordL.requestFocus();
            return;
        }

        progressBarL.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(emailL, passwordL).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //redirect to user profile
                    FirebaseUser userL = FirebaseAuth.getInstance().getCurrentUser();

                    if (userL.isEmailVerified()){
                        startActivity(new Intent(LecturerLogin.this, ProfileActivityLecturer.class));
                    }else {
                        userL.sendEmailVerification();
                        Toast.makeText(LecturerLogin.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(LecturerLogin.this, "Failed to login! Kindly check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}