package com.mayu298.courseregistrationsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentCourseDTO {

    private Integer courseId;
    private String title;
    private String description;
    private LocalDateTime enrolledAt;

}
