package com.mayu298.courseregistrationsystem.repository;

import com.mayu298.courseregistrationsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
}
