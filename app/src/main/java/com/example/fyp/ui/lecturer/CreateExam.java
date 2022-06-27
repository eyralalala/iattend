package com.example.fyp.ui.lecturer;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.example.fyp.data.Course;
import com.example.fyp.data.Exam;
import com.example.fyp.data.notification.NotificationData;
import com.example.fyp.data.notification.PushNotification;
import com.example.fyp.databinding.ActivityCreateExamBinding;
import com.example.fyp.utils.BaseActivity;
import com.example.fyp.utils.Constants;
import com.example.fyp.utils.NotificationHelper;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import lombok.extern.java.Log;

@Log(topic = "Create exam")
public class CreateExam extends BaseActivity {
    private ActivityCreateExamBinding binding;
    private long examDate, examStartTime, examEndTime;
    private NotificationHelper helper;
    public PushNotification pushNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create exam");
        setDate(MaterialDatePicker.todayInUtcMilliseconds());
        setTimes(System.currentTimeMillis());
        helper = new NotificationHelper();
        binding.buttonSaveExam.setOnClickListener(v -> {

        });
        binding.imageButtonDatePicker.setOnClickListener(v -> {
            CalendarConstraints constraintsBuilder = new CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.from(MaterialDatePicker.todayInUtcMilliseconds()))
                    .build();
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setSelection(examDate)
                    .setCalendarConstraints(constraintsBuilder)
                    .setTitleText("Pick date for exam")
                    .build();
            datePicker.addOnPositiveButtonClickListener(this::setDate);
            datePicker.addOnNegativeButtonClickListener(v1 -> datePicker.dismiss());
            datePicker.show(getSupportFragmentManager(), "dpd");
        });
        binding.imageButtonStartTimePicker.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(examStartTime);
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTitleText("Pick exam time")
                    .setHour(now.get(Calendar.HOUR_OF_DAY))
                    .setMinute(now.get(Calendar.MINUTE))
                    .setTimeFormat(timeFormat(this))
                    .build();
            timePicker.addOnPositiveButtonClickListener(v1 -> {
                Calendar time = Calendar.getInstance();
                time.set(Calendar.MINUTE, timePicker.getMinute());
                time.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                setStartTime(time.getTimeInMillis());
            });
            timePicker.addOnNegativeButtonClickListener(v1 -> timePicker.dismiss());
            timePicker.show(getSupportFragmentManager(), "tpd");
        });

        binding.imageButtonEndTimePicker.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(examEndTime);
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTitleText("Pick exam time")
                    .setHour(now.get(Calendar.HOUR_OF_DAY))
                    .setMinute(now.get(Calendar.MINUTE))
                    .setTimeFormat(timeFormat(this))
                    .build();
            timePicker.addOnPositiveButtonClickListener(v1 -> {
                Calendar time = Calendar.getInstance();
                time.set(Calendar.MINUTE, timePicker.getMinute());
                time.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                if (time.getTimeInMillis() < addTimeMillis(examStartTime, 10)) {
                    toast("End time is shorter than start time");
                } else {
                    setEndTime(time.getTimeInMillis());
                }
            });
            timePicker.addOnNegativeButtonClickListener(v1 -> timePicker.dismiss());
            timePicker.show(getSupportFragmentManager(), "tpd");
        });

        binding.buttonSaveExam.setOnClickListener(v -> {
            if (isEverythingValid()) {
                String courseName = binding.courseNameEditText.getText().toString();
                String courseCode = binding.courseCodeEditText.getText().toString();
                String examId = String.format(Locale.getDefault(), "%s_%s",
                        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(examDate),
                        new SimpleDateFormat("HH:mm", Locale.getDefault()).format(examStartTime));
                Course course = new Course(courseCode, courseName);
                Exam exam = new Exam(examId, getAuth().getUid(), courseCode, courseName, getExamType(), examDate, examStartTime, examEndTime);
                showProgressDialog("Adding course to database ...");
                getDatabaseReference().child(Constants.courses).child(courseCode).setValue(course)
                        .addOnSuccessListener(unused -> {
                            setDialogMessage("Adding exam to database ...");
                            getDatabaseReference().child(Constants.exams).child(examId)
                                    .get().addOnSuccessListener(snapshot -> {
                                if (snapshot.exists()) {
                                    alert("Exam already exists","There is already and exam on the selected date and time period.", "Cancel", (dialog, which) -> {
                                        hideProgressDialog();
                                        dialog.dismiss();
                                    }, "Overwrite", (dialog, which) -> {
                                        getDatabaseReference().child(Constants.exams).child(examId)
                                                .setValue(exam).addOnSuccessListener(command -> {
                                            sendNotification(exam.getExamDate(), exam.getExamStartTime(), exam.getCourseName());
                                            toast("Exam added successfully");
                                            hideProgressDialog();
                                        }).addOnFailureListener(command -> {
                                            hideProgressDialog();
                                            toast("Failed to add exam to database");
                                        });
                                        dialog.dismiss();
                                    });
                                } else {
                                    getDatabaseReference().child(Constants.exams).child(examId)
                                            .setValue(exam).addOnSuccessListener(command -> {
                                        sendNotification(exam.getExamDate(), exam.getExamStartTime(), exam.getCourseName());
                                        toast("Exam added successfully");
                                        hideProgressDialog();
                                    }).addOnFailureListener(command -> {
                                        hideProgressDialog();
                                        toast("Failed to add exam to database");
                                    });
                                }
                            }).addOnFailureListener(e -> {
                                toast("Unable to get Exam data");
                                log.severe(e.getMessage());
                            });
                        }).addOnFailureListener(e -> {
                    hideProgressDialog();
                    toast("Failed to add course to database");
                    log.severe(e.getMessage());
                });
            }
        });
    }

    private void sendNotification(long examDate, long examStartTime, String courseName) {
        String title = String.format(Locale.getDefault(), "New Exam (%s)", courseName);
        String examDateString = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(examDate);
        String examStartString = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(examStartTime);
        String examDateAndTime = String.format(Locale.getDefault(), "%s %s", examDateString, examStartString);
        String message = String.format(Locale.getDefault(), "You have a new exam on %s", examDateAndTime);
        pushNotification = new PushNotification(new NotificationData(title, message), "/topics/" + Constants.student);
        helper.sendNotification(pushNotification);
    }

    private String getExamType() {
        if (binding.finalTermRadio.isChecked())
            return "Final term";
        else
            return "Mid term";
    }

    private boolean isEverythingValid() {
        if (binding.courseCodeEditText.getText().toString().isEmpty()) {
            binding.courseCodeEditText.setError("Please enter course code");
            return false;
        } else if (binding.courseNameEditText.getText().toString().isEmpty()) {
            binding.courseNameEditText.setError("Please enter course name");
            return false;
        } else
            return true;
    }

    private void setTimes(long timeMillis) {
        setStartTime(timeMillis);
        setEndTime(addTimeMillis(timeMillis, 10));
    }

    private void setEndTime(long timeMillis) {
        examEndTime = timeMillis;
        binding.textViewEndTime.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(examEndTime));
    }

    private void setStartTime(long timeMillis) {
        examStartTime = timeMillis;
        binding.textViewStartTime.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(examStartTime));
    }

    private long addTimeMillis(long timeMillis, int minutesToAdd) {
        return timeMillis + (minutesToAdd * 60000L);
    }

    private void setDate(Long selection) {
        binding.textViewDate.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(selection));
        examDate = selection;
    }

    /*Function for getting system time format*/
    private Integer timeFormat(Context context) {
        if (DateFormat.is24HourFormat(context))
            return TimeFormat.CLOCK_24H;
        else
            return TimeFormat.CLOCK_12H;
    }
}