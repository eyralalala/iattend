package com.example.fyp.helper;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.databinding.StudentListLayoutBinding;
import com.example.fyp.provided.User;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> {

    private final ArrayList<User> students;
    private final String className;
    private OnStudentClicked onStudentClick;

    public interface OnStudentClicked {
        void onClick(int position);
    }

    public void setOnStudentClickListener(OnStudentClicked onStudentClick) {
        this.onStudentClick = onStudentClick;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentsViewHolder(StudentListLayoutBinding.
                inflate(LayoutInflater.from(parent.getContext()),
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder holder, int position) {
        holder.binding.studentName.setText(students.get(position).getFullName());
        holder.binding.studentEmail.setText(students.get(position).getEmail());
        holder.binding.studentStudentClass.setText(className);
        holder.itemView.setOnClickListener(v -> onStudentClick.onClick(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentsViewHolder extends RecyclerView.ViewHolder {
        StudentListLayoutBinding binding;

        public StudentsViewHolder(StudentListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
