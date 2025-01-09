package com.vitor.Investmentaggregator.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vitor.Investmentaggregator.controller.dto.CreateUserDto;
import com.vitor.Investmentaggregator.entities.User;
import com.vitor.Investmentaggregator.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UUID createUser(CreateUserDto dto) {

        var encodedPassword = passwordEncoder.encode(dto.password());

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(encodedPassword);
        user.setEmail(dto.email());
        user.setCreationTimestamp(Instant.now());
        user.setUpdatedTimestamp(null);

        var userSaved = userRepository.save(user);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        
        return userRepository.findById(UUID.fromString(userId));
    }
}
