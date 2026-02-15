package com.mayu298.courseregistrationsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentResponseDTO {
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
