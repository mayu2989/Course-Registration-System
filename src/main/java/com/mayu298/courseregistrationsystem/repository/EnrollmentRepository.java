package com.mayu298.courseregistrationsystem.repository;

import com.mayu298.courseregistrationsystem.model.Course;
import com.mayu298.courseregistrationsystem.model.Enrollment;
import com.mayu298.courseregistrationsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    boolean existsByStudentAndCourse(Student student, Course course);

    List<Enrollment> findByStudent(Student student);

    List<Enrollment> findByCourse(Course course);

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    boolean existsByCourseId(Integer courseId);
}
