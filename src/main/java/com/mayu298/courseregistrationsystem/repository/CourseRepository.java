package com.mayu298.courseregistrationsystem.repository;

import com.mayu298.courseregistrationsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    @Override
    default void deleteById(Integer id) {
        Course course = this.findById(id).orElse(null);
        if (course != null) {
            course.setDeleted(true);
            this.save(course);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }
}
