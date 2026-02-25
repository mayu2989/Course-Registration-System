package com.mayu298.courseregistrationsystem.repository;

import com.mayu298.courseregistrationsystem.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Optional<Student> findByUserId(Integer userId);

}