package com.mayu298.courseregistrationsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 3,message = "Name must be at least 3 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
