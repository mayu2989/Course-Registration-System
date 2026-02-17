package com.mayu298.courseregistrationsystem.dto;

import com.mayu298.courseregistrationsystem.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "username is required")
    String username;
    @NotBlank(message = "Password is required")
    String  password;
    @NotNull
    Role role;
}
