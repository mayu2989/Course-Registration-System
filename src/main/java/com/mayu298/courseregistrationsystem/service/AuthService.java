package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.dto.LoginRequestDTO;
import com.mayu298.courseregistrationsystem.dto.RegisterRequestDTO;
import com.mayu298.courseregistrationsystem.exception.EmailAlreadyExistsException;
import com.mayu298.courseregistrationsystem.exception.UsernameAlreadyExistsException;
import com.mayu298.courseregistrationsystem.model.Role;
import com.mayu298.courseregistrationsystem.model.Student;
import com.mayu298.courseregistrationsystem.model.User;
import com.mayu298.courseregistrationsystem.repository.StudentRepository;
import com.mayu298.courseregistrationsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private UserRepository userRepository;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private StudentRepository studentRepository;
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,AuthenticationManager authenticationManager,
                       StudentRepository studentRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
    }
    @Transactional
    public void register(RegisterRequestDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_STUDENT);

        User savedUser;

        try {
            savedUser = userRepository.save(user);
        }
        catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        Student student = new Student();
        student.setUser(savedUser);
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        try {
            studentRepository.save(student);
        }
        catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }
    public String login(LoginRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        return jwtService.generateToken(dto.getUsername());
    }

}
