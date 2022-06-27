package com.example.fyp.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class Exam {
    private String examId, createdBy, courseCode,
            courseName,
            examType;
    private Long examDate,
            examStartTime,
            examEndTime;
}
