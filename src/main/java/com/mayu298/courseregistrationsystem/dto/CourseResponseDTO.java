package com.mayu298.courseregistrationsystem.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CourseResponseDTO {

    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
