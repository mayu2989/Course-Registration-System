package com.mayu298.courseregistrationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "username cant be empty")
    String username;
    @NotNull
    String password;
}
