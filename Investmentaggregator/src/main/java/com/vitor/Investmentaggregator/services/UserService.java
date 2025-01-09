package com.vitor.Investmentaggregator.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vitor.Investmentaggregator.controller.dto.CreateUserDto;
import com.vitor.Investmentaggregator.entities.User;
import com.vitor.Investmentaggregator.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UUID createUser(CreateUserDto dto) {

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setCreationTimestamp(Instant.now());
        user.setUpdatedTimestamp(null);

        var userSaved = userRepository.save(user);

        return userSaved.getUserId();
    }
}
