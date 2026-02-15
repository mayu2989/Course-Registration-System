package com.mayu298.courseregistrationsystem.controller;

import com.mayu298.courseregistrationsystem.dto.CourseRequestDTO;
import com.mayu298.courseregistrationsystem.dto.CourseResponseDTO;
import com.mayu298.courseregistrationsystem.model.Course;
import com.mayu298.courseregistrationsystem.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> addCourse(
            @Valid @RequestBody CourseRequestDTO dto) {

        CourseResponseDTO savedCourse =
                courseService.createCourse(dto);

        URI location = URI.create("/courses/" + savedCourse.getId());

        return ResponseEntity.created(location).body(savedCourse);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {

        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseById(
            @PathVariable Integer id) {

        courseService.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }
}

