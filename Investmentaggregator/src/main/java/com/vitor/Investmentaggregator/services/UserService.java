package com.vitor.Investmentaggregator.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vitor.Investmentaggregator.controller.dto.CreateAccountDto;
import com.vitor.Investmentaggregator.controller.dto.CreateUserDto;
import com.vitor.Investmentaggregator.controller.dto.UpdateUserDto;
import com.vitor.Investmentaggregator.entities.Account;
import com.vitor.Investmentaggregator.entities.BillingAddress;
import com.vitor.Investmentaggregator.entities.User;
import com.vitor.Investmentaggregator.repository.AccountRepository;
import com.vitor.Investmentaggregator.repository.BillingAdressRepository;
import com.vitor.Investmentaggregator.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final BillingAdressRepository billingAdressRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AccountRepository accountRepository, BillingAdressRepository billingAdressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.billingAdressRepository = billingAdressRepository;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserById(String userId){
        var  id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        } else{
            return;
        }
    }

    @Transactional
    public void updateUserById(String userId,
                               UpdateUserDto dto) {
        var  id = UUID.fromString(userId);
        var userExists = userRepository.findById(id);

        if (userExists.isPresent()){

            var user = userExists.get();

            if (dto.username() != null) {
                user.setUsername(dto.username());
            }

            if (dto.password() != null) {
                var encodedPassword = passwordEncoder.encode(dto.password());
                user.setPassword(encodedPassword);
            }
        } else {
            return;
        }

        userRepository.save(userExists.get());
    }

    @Transactional
    public void createAccount(String userId , CreateAccountDto dto) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> 
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        var account = new Account();
        account.setUser(user);
        account.setDescription(dto.description());

        var accountSaved = accountRepository.save(account);

        var billingAdress = new BillingAddress(
                                              dto.street(),
                                              dto.number(),
                                              accountSaved);
        billingAdressRepository.save(billingAdress);
    }
}
