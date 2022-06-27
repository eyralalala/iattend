package com.example.fyp.provided;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp.R;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;

import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "Register Activity")
public class RegisterUser extends BaseActivity {

    private TextView banner, registerUser;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextIcNumber, editTextPhoneNumber, editTextPassword;
    private ProgressBar progressBar;
    private RadioButton lecturer, student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(v -> registerUser());

        editTextFullName = findViewById(R.id.fullName);
        editTextAge = findViewById(R.id.age);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextIcNumber = findViewById(R.id.ic);
        editTextPhoneNumber = findViewById(R.id.phone);
        lecturer = findViewById(R.id.lecturer);
        student = findViewById(R.id.student);

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String icNumber = editTextIcNumber.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is empty!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid Email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Password should be more than 6!");
            editTextPassword.requestFocus();
            return;
        }
        showProgressDialog("Registering user ...");
        getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, age, email, icNumber, phoneNumber, getUserType());
                        getUsersReference().child(Objects.requireNonNull(getAuth().getUid()))
                                .setValue(user)
                                .addOnSuccessListener(unused -> {
                                    hideProgressDialog();
                                    toast("User has been registered! Please login");
                                    getAuth().signOut();
                                    goBack();
                                }).addOnFailureListener(e -> {
                            Objects.requireNonNull(getAuth().getCurrentUser()).delete();
                            toast("Failed to add user values");
                            hideProgressDialog();
                        });

                    } else {
                        Toast.makeText(RegisterUser.this, "Fail to register, Try again", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        log.severe(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });

    }

    private String getUserType() {
        if (lecturer.isChecked()) {
            return Constants.lecturer;
        } else if (student.isChecked()) {
            return Constants.student;
        } else {
            return Constants.currentUser;
        }
    }
}