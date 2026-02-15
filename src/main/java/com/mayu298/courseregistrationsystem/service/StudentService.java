package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.dto.StudentRequestDTO;
import com.mayu298.courseregistrationsystem.dto.StudentResponseDTO;
import com.mayu298.courseregistrationsystem.model.Student;
import com.mayu298.courseregistrationsystem.repository.StudentRepository;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    public StudentResponseDTO createStudent(StudentRequestDTO dto) {

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        Student savedStudent = studentRepository.save(student);

        return mapToDTO(savedStudent);
    }

    // GET ALL
    public Page<StudentResponseDTO> getAllStudents(String name, Pageable pageable)
    {
        Page<Student> students;

        if(name != null && !name.isEmpty())
        {
            students = studentRepository
                    .findByNameContainingIgnoreCase(name, pageable);
        }
        else
        {
            students = studentRepository.findAll(pageable);
        }

        return students.map(this::mapToDTO);
    }


    // GET BY ID
    public StudentResponseDTO getStudentById(Integer id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id: " + id));

        return mapToDTO(student);
    }

    // DELETE
    public void deleteStudentById(Integer id) {
        studentRepository.deleteById(id);
    }

    // ENTITY → DTO Mapper
    private StudentResponseDTO mapToDTO(Student student) {

        StudentResponseDTO dto = new StudentResponseDTO();

        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setCreatedAt(student.getCreatedAt());

        return dto;
    }
}
