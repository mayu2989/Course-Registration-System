package com.mayu298.courseregistrationsystem.controller;

import com.mayu298.courseregistrationsystem.dto.*;
import com.mayu298.courseregistrationsystem.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> enrollStudent(
            @RequestBody EnrollmentRequestDTO dto) {

        return ResponseEntity.ok(
                enrollmentService.enrollStudent(dto)
        );
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<StudentCourseDTO>> getCoursesForStudent(
            @PathVariable Integer studentId) {

        return ResponseEntity.ok(
                enrollmentService.getCoursesForStudent(studentId)
        );
    }


    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsForCourse(
            @PathVariable Integer courseId) {

        return ResponseEntity.ok(
                enrollmentService.getStudentsForCourse(courseId)
        );
    }

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> unenrollStudent(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId) {

        enrollmentService.unenrollStudent(studentId, courseId);

        return ResponseEntity.noContent().build();
    }
}
