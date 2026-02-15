package com.mayu298.courseregistrationsystem.controller;

import com.mayu298.courseregistrationsystem.dto.CourseResponseDTO;
import com.mayu298.courseregistrationsystem.dto.EnrollmentRequestDTO;
import com.mayu298.courseregistrationsystem.dto.EnrollmentResponseDTO;
import com.mayu298.courseregistrationsystem.dto.StudentResponseDTO;
import com.mayu298.courseregistrationsystem.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // 1️⃣ ENROLL STUDENT INTO COURSE
    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> enrollStudent(
            @Valid @RequestBody EnrollmentRequestDTO dto) {

        EnrollmentResponseDTO response =
                enrollmentService.enrollStudent(dto);

        URI location = URI.create("/enrollments/" + response.getId());

        return ResponseEntity.created(location).body(response);
    }

    // 2️⃣ GET ALL COURSES OF A STUDENT
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesForStudent(
            @PathVariable Integer studentId) {

        return ResponseEntity.ok(
                enrollmentService.getCoursesForStudent(studentId)
        );
    }

    // 3️⃣ GET ALL STUDENTS OF A COURSE
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsForCourse(
            @PathVariable Integer courseId) {

        return ResponseEntity.ok(
                enrollmentService.getStudentsForCourse(courseId)
        );
    }

    // 4️⃣ UNENROLL STUDENT FROM COURSE
    @DeleteMapping
    public ResponseEntity<Void> unenrollStudent(
            @RequestParam Integer studentId,
            @RequestParam Integer courseId) {

        enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.noContent().build();
    }
}
