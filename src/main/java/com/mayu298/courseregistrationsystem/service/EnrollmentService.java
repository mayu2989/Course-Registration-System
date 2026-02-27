package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.dto.*;
import com.mayu298.courseregistrationsystem.exception.DuplicateEnrollmentException;
import com.mayu298.courseregistrationsystem.exception.ResourceNotFoundException;
import com.mayu298.courseregistrationsystem.model.Course;
import com.mayu298.courseregistrationsystem.model.Enrollment;
import com.mayu298.courseregistrationsystem.model.Student;
import com.mayu298.courseregistrationsystem.repository.CourseRepository;
import com.mayu298.courseregistrationsystem.repository.EnrollmentRepository;
import com.mayu298.courseregistrationsystem.repository.StudentRepository;
import com.mayu298.courseregistrationsystem.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private static final Logger log =
            LoggerFactory.getLogger(EnrollmentService.class);

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // ============================
    // ENROLL LOGGED-IN STUDENT
    // ============================
    @PreAuthorize("hasRole('STUDENT')")
    @Transactional
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO dto) {

        CustomUserDetails principal =
                (CustomUserDetails)
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

        Integer userId = principal.getId();

        Student student = studentRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved;

        try {
            saved = enrollmentRepository.save(enrollment);
        }
        catch (DataIntegrityViolationException ex){
            throw new DuplicateEnrollmentException("Already enrolled");
        }

        return mapToDTO(saved);
    }

    // ============================
    // GET MY COURSES
    // ============================
    @PreAuthorize("hasRole('STUDENT')")
    public List<StudentCourseDTO> getMyCourses() {

        CustomUserDetails principal =
                (CustomUserDetails)
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

        Integer userId = principal.getId();

        Student student = studentRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudent(student);

        return enrollments.stream()
                .map(e -> {

                    Course c = e.getCourse();

                    StudentCourseDTO dto = new StudentCourseDTO();
                    dto.setCourseId(c.getId());
                    dto.setTitle(c.getTitle());
                    dto.setDescription(c.getDescription());
                    dto.setEnrolledAt(e.getCreatedAt());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ============================
    // ADMIN ONLY
    // ============================
    @PreAuthorize("hasRole('ADMIN')")
    public List<StudentResponseDTO> getStudentsForCourse(Integer courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        List<Enrollment> enrollments =
                enrollmentRepository.findByCourse(course);

        return enrollments.stream()
                .map(e -> {
                    Student s = e.getStudent();

                    StudentResponseDTO dto = new StudentResponseDTO();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    dto.setEmail(s.getEmail());
                    dto.setCreatedAt(s.getCreatedAt());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ============================
    // UNENROLL MYSELF
    // ============================
    @PreAuthorize("hasRole('STUDENT')")
    @Transactional
    public void unenrollMyCourse(Integer courseId) {

        CustomUserDetails principal =
                (CustomUserDetails)
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

        Integer userId = principal.getId();

        Student student = studentRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        Enrollment enrollment =
                enrollmentRepository.findByStudentAndCourse(student, course)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Enrollment not found"));

        enrollmentRepository.delete(enrollment);
    }

    // ============================
    // MAPPER
    // ============================

    private EnrollmentResponseDTO mapToDTO(Enrollment enrollment) {

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();

        dto.setId(enrollment.getId());
        dto.setStudentId(enrollment.getStudent().getId());
        dto.setStudentName(enrollment.getStudent().getName());
        dto.setCourseId(enrollment.getCourse().getId());
        dto.setCourseTitle(enrollment.getCourse().getTitle());
        dto.setEnrolledAt(enrollment.getCreatedAt());

        return dto;
    }
}