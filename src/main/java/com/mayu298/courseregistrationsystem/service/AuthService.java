package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.dto.LoginRequestDTO;
import com.mayu298.courseregistrationsystem.dto.RegisterRequestDTO;
import com.mayu298.courseregistrationsystem.model.User;
import com.mayu298.courseregistrationsystem.repository.UserRepository;
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
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequestDTO registerRequestDTO) {
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(registerRequestDTO.getRole());
        userRepository.save(user);
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
