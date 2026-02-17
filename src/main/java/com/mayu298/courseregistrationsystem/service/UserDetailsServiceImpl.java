package com.mayu298.courseregistrationsystem.service;

import com.mayu298.courseregistrationsystem.model.User;
import com.mayu298.courseregistrationsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl
        implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(() ->
                        "ROLE_" + user.getRole().name())
        );
    }
}
