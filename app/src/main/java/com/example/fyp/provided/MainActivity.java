package com.example.fyp.provided;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fyp.R;
import com.example.fyp.ui.lecturer.LecturerHome;
import com.example.fyp.ui.student.StudentHome;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "Login Activity")
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private TextView register, forgotPassword, lecturer;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getAuth().getCurrentUser() != null) {
            if (getAuth().getCurrentUser().isEmailVerified()) {
                User currentUser = getObject(Constants.currentUser, User.class);
                if (currentUser.getUserType().equals(Constants.lecturer)) {
                    goTo(LecturerHome.class, true);
                } else {
                    goTo(StudentHome.class, true);
                }
            } else {
                getAuth().signOut();
                longToast("It looks like your email isn't verified, Please verify and <b>re-login</b>");
            }
        }
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotpassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                goTo(RegisterUser.class);
                break;

            case R.id.signIn:
                userLogin();
                break;

            case R.id.forgotpassword:
                goTo(ForgotPassword.class);
                break;
        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Min password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        showProgressDialog("Sign in ...");
        getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                assert user != null;
                setDialogMessage("Checking email verification");
                if (user.isEmailVerified()) {
                    getUsersReference().child(Objects.requireNonNull(getAuth().getUid())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            saveObject(Constants.currentUser, user);
                            assert user != null;
                            if (user.getUserType().equals(Constants.lecturer)) {
                                hideProgressDialog();
                                goTo(LecturerHome.class, true);
                            } else {
                                hideProgressDialog();
                                FirebaseMessaging.getInstance().subscribeToTopic(Constants.student)
                                        .addOnSuccessListener(command -> goTo(StudentHome.class, true));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            toast("Failed to get user");
                            log.severe(error.getMessage());
                        }
                    });
                } else {
                    user.sendEmailVerification();
                    hideProgressDialog();
                    Toast.makeText(MainActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(MainActivity.this, "Fail to login. Please check your credentials", Toast.LENGTH_LONG).show();
                log.severe(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }
}