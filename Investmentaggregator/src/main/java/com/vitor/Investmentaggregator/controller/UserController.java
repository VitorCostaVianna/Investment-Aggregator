package com.vitor.Investmentaggregator.controller;
 
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.Investmentaggregator.controller.dto.CreateAccountDto;
import com.vitor.Investmentaggregator.controller.dto.CreateUserDto;
import com.vitor.Investmentaggregator.controller.dto.UpdateUserDto;
import com.vitor.Investmentaggregator.entities.User;
import com.vitor.Investmentaggregator.services.UserService;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/v1/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto dto) {
        var userId = userService.createUser(dto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        
        var user = userService.getUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        
        var users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        
        userService.deleteUserById(userId);
        
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto dto) {
        
        userService.updateUserById(userId, dto);
        
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/account")
    public ResponseEntity<Void> createAccount(@PathVariable("userId") String userId,
                                              @RequestBody CreateAccountDto dto) {
        
        userService.createAccount(userId,dto);
        
        return ResponseEntity.ok().build();
    }
}
