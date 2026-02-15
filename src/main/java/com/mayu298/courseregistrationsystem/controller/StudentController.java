package com.mayu298.courseregistrationsystem.controller;

import com.mayu298.courseregistrationsystem.dto.StudentRequestDTO;
import com.mayu298.courseregistrationsystem.dto.StudentResponseDTO;
import com.mayu298.courseregistrationsystem.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<StudentResponseDTO> addStudent(
            @Valid @RequestBody StudentRequestDTO dto) {

        StudentResponseDTO savedStudent =
                studentService.createStudent(dto);

        URI location = URI.create("/students/" + savedStudent.getId());

        return ResponseEntity.created(location).body(savedStudent);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<Page<StudentResponseDTO>> getAllStudents(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                studentService.getAllStudents(name, pageable)
        );
    }




    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(
            @PathVariable Integer id) {

        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
