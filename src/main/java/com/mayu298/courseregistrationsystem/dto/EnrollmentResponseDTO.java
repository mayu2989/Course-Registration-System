package com.mayu298.courseregistrationsystem.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EnrollmentResponseDTO {

    private Integer id;

    private Integer studentId;
    private String studentName;

    private Integer courseId;
    private String courseTitle;

    private LocalDateTime enrolledAt;
}
