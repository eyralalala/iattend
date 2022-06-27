package com.example.fyp.helper;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.data.Exam;
import com.example.fyp.databinding.ExamListLayoutBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamsViewHolder> {
    private final ArrayList<Exam> exams;
    private final boolean isAttendance;
    private OnItemClick onItemClick;

    public interface OnItemClick {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ExamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExamListLayoutBinding binding = ExamListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ExamsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamsViewHolder holder, int position) {
        Exam current = exams.get(position);
        String formattedDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(current.getExamDate());
        String startTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(current.getExamStartTime());
        String endTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(current.getExamEndTime());
        holder.binding.examListCourseCode.setText(current.getCourseCode());
        holder.binding.examListCourseName.setText(current.getCourseName());
        holder.binding.examListExamDate.setText(String.format("%s %s-%s", formattedDate, startTime, endTime));
        holder.binding.examListExamType.setText(current.getExamType());
        if (isAttendance) {
            holder.itemView.setOnClickListener(v -> onItemClick.onClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    static class ExamsViewHolder extends RecyclerView.ViewHolder {
        ExamListLayoutBinding binding;

        public ExamsViewHolder(@NonNull ExamListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
