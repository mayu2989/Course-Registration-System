package com.mayu298.courseregistrationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "course")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    String title;
    @Column(length = 1000)
    @NotBlank(message = "Description is required")
    String description;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}
