package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.dto.CourseRequestDTO;
import com.mayu298.courseregistrationsystem.dto.CourseResponseDTO;
import com.mayu298.courseregistrationsystem.exception.CourseDeletionNotAllowedException;
import com.mayu298.courseregistrationsystem.model.Course;
import com.mayu298.courseregistrationsystem.repository.CourseRepository;
import com.mayu298.courseregistrationsystem.repository.EnrollmentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {

        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());

        Course savedCourse = courseRepository.save(course);
        System.out.println(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getAuthorities()
        );

        return mapToDTO(savedCourse);
    }

    public List<CourseResponseDTO> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponseDTO getCourseById(Integer id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found with id: " + id));

        return mapToDTO(course);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourseById(Integer id) {
        if(enrollmentRepository.existsByCourseId(id)) {
            throw new CourseDeletionNotAllowedException(
                    "Cannot delete course with enrolled students"
            );
        }
    }

    private CourseResponseDTO mapToDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setCreatedAt(course.getCreatedAt());

        return dto;
    }
}