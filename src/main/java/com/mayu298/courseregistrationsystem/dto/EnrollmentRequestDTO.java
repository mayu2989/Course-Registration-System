package com.mayu298.courseregistrationsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequestDTO {

    @NotNull
    private Integer studentId;

    @NotNull
    private Integer courseId;
}
