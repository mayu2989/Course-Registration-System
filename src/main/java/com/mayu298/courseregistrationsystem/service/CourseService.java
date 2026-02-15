package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.dto.CourseRequestDTO;
import com.mayu298.courseregistrationsystem.dto.CourseResponseDTO;
import com.mayu298.courseregistrationsystem.model.Course;
import com.mayu298.courseregistrationsystem.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseResponseDTO createCourse(CourseRequestDTO dto) {

        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());

        Course savedCourse = courseRepository.save(course);

        return mapToDTO(savedCourse);
    }

    public List<CourseResponseDTO> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO getCourseById(Integer id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found with id: " + id));

        return mapToDTO(course);
    }

    public void deleteCourseById(Integer id) {
        courseRepository.deleteById(id);
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