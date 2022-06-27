package com.example.fyp.ui.lecturer;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fyp.R;
import com.example.fyp.data.Exam;
import com.example.fyp.databinding.ActivityLecturerExamsBinding;
import com.example.fyp.helper.ExamsAdapter;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "Lecturer Exams")
@SuppressLint("NotifyDataSetChanged")
public class LecturerExams extends BaseActivity {
    private ActivityLecturerExamsBinding binding;
    private ArrayList<Exam> exams, tempExams;
    private ArrayList<String> keys;
    private ExamsAdapter examsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLecturerExamsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Exams");
        binding.addExam.setOnClickListener(v -> goTo(CreateExam.class));
        exams = new ArrayList<>();
        tempExams = new ArrayList<>();
        keys = new ArrayList<>();
        recyclerViewSetter();
        showProgressDialog("Loading exams ...");
        getDatabaseReference().child(Constants.exams).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        keys.add(snapshot.getKey());
                        Exam exam = snapshot.getValue(Exam.class);
                        assert exam != null;
                        if (exam.getCreatedBy().equals(getAuth().getUid())) {
                            exams.add(exam);
                            tempExams.add(exam);
                            examsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        int index = keys.indexOf(snapshot.getKey());
                        Exam exam = snapshot.getValue(Exam.class);
                        assert exam != null;
                        if (exam.getCreatedBy().equals(getAuth().getUid())) {
                            exams.set(index, exam);
                            tempExams.set(index, exam);
                            examsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        int index = keys.indexOf(snapshot.getKey());
                        if (index != -1) {
                            exams.remove(index);
                            keys.remove(index);
                            examsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        toast("Child loading error");
                        log.severe(error.getMessage());
                    }
                });
        getDatabaseReference().child(Constants.exams)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        hideProgressDialog();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        toast("Unable to load date");
                        log.severe(error.getMessage());
                    }
                });
    }


    private void recyclerViewSetter() {
        examsAdapter = new ExamsAdapter(tempExams,false);
        binding.examsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.examsRecyclerView.setAdapter(examsAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchable_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) search.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText.trim());
                return true;
            }
        });
        return true;
    }

    private void filter(String query) {
        tempExams.clear();
        if (!query.equals("")) {
            for (Exam exam : exams) {
                if (exam.getCourseName().contains(query)) {
                    tempExams.add(exam);
                    examsAdapter.notifyDataSetChanged();
                } else if (exam.getCourseCode().contains(query)) {
                    tempExams.add(exam);
                    examsAdapter.notifyDataSetChanged();
                } else if (exam.getExamType().contains(query)) {
                    tempExams.add(exam);
                    examsAdapter.notifyDataSetChanged();
                }
            }
        } else {
            tempExams.addAll(exams);
            examsAdapter.notifyDataSetChanged();
        }
    }
}