package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.dto.CourseResponseDTO;
import com.mayu298.courseregistrationsystem.dto.EnrollmentRequestDTO;
import com.mayu298.courseregistrationsystem.dto.EnrollmentResponseDTO;
import com.mayu298.courseregistrationsystem.dto.StudentResponseDTO;
import com.mayu298.courseregistrationsystem.exception.DuplicateEnrollmentException;
import com.mayu298.courseregistrationsystem.exception.ResourceNotFoundException;
import com.mayu298.courseregistrationsystem.model.Course;
import com.mayu298.courseregistrationsystem.model.Enrollment;
import com.mayu298.courseregistrationsystem.model.Student;
import com.mayu298.courseregistrationsystem.repository.CourseRepository;
import com.mayu298.courseregistrationsystem.repository.EnrollmentRepository;
import com.mayu298.courseregistrationsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

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
    // ENROLL STUDENT
    // ============================

    @Transactional
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        boolean exists =
                enrollmentRepository.existsByStudentAndCourse(student, course);

        if (exists) {
            throw new DuplicateEnrollmentException("Already enrolled");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved = enrollmentRepository.save(enrollment);

        return mapToDTO(saved);
    }

    // ============================
    // GET COURSES FOR STUDENT
    // ============================

    public List<CourseResponseDTO> getCoursesForStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudent(student);

        return enrollments.stream()
                .map(e -> {
                    Course c = e.getCourse();

                    CourseResponseDTO dto = new CourseResponseDTO();
                    dto.setId(c.getId());
                    dto.setTitle(c.getTitle());
                    dto.setDescription(c.getDescription());
                    dto.setCreatedAt(c.getCreatedAt());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ============================
    // GET STUDENTS FOR COURSE
    // ============================

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
    // UNENROLL STUDENT
    // ============================

    @Transactional
    public void unenrollStudent(Integer studentId, Integer courseId) {

        Student student = studentRepository.findById(studentId)
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

        dto.setEnrolledAt(enrollment.getEnrolledAt());

        return dto;
    }
}


