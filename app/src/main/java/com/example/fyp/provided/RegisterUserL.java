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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserL extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextFullNameL, editTextAgeL, editTextEmailL, editTextPasswordL;
    private TextView bannerL, registerUserL;
    private ProgressBar progressBarL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_l);

        mAuth = FirebaseAuth.getInstance();

        bannerL = (TextView) findViewById(R.id.bannerL);
        bannerL.setOnClickListener(this);

        registerUserL =(Button) findViewById(R.id.registerUserL);
        registerUserL.setOnClickListener(this);

        editTextFullNameL = (EditText) findViewById(R.id.fullNameL);
        editTextAgeL = (EditText) findViewById(R.id.ageL);
        editTextEmailL = (EditText) findViewById(R.id.emailL);
        editTextPasswordL = (EditText) findViewById(R.id.passwordL);

        progressBarL = (ProgressBar) findViewById(R.id.progressBarL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bannerL:
                startActivity(new Intent(this, LecturerLogin.class));
                break;
            case R.id.registerUserL:
                registerUserL();
        }

    }

    private void registerUserL() {
        String emailL = editTextEmailL.getText().toString().trim();
        String passwordL = editTextPasswordL.getText().toString().trim();
        String fullnameL = editTextFullNameL.getText().toString().trim();
        String ageL = editTextAgeL.getText().toString().trim();

        if (fullnameL.isEmpty()){
            editTextFullNameL.setError("Full name is required!");
            editTextFullNameL.requestFocus();
            return;
        }

        if (ageL.isEmpty()){
            editTextAgeL.setError("Age is required!");
            editTextAgeL.requestFocus();
            return;
        }

        if (emailL.isEmpty()){
            editTextEmailL.setError("Email is required!");
            editTextEmailL.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailL).matches()){
            editTextEmailL.setError("Please provide valid email");
            editTextEmailL.requestFocus();
            return;
        }

        if (passwordL.isEmpty()){
            editTextPasswordL.setError("Please enter your Password");
            editTextPasswordL.requestFocus();
            return;
        }

        if (passwordL.length() < 6){
            editTextPasswordL.setError("Your password is short!");
            editTextPasswordL.requestFocus();
            return;
        }


        progressBarL.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailL, passwordL)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            UserL userL = new UserL(fullnameL, ageL, emailL);

                            FirebaseDatabase.getInstance().getReference("UserL")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userL).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterUserL.this, "Users Lecturer has been registered successfully!", Toast.LENGTH_LONG).show();
                                        progressBarL.setVisibility(View.GONE);

                                        //redirect to login layout
                                    }else {
                                        Toast.makeText(RegisterUserL.this, "Fail to register! Try again", Toast.LENGTH_LONG).show();
                                        progressBarL.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }else {
                            Toast.makeText(RegisterUserL.this, "Fail to register! Try again!", Toast.LENGTH_LONG).show();
                            progressBarL.setVisibility(View.GONE);
                        }
                    }
                });


    }
}