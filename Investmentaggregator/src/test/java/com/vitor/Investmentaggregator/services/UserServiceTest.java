package com.vitor.Investmentaggregator.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vitor.Investmentaggregator.controller.dto.CreateUserDto;
import com.vitor.Investmentaggregator.controller.dto.UpdateUserDto;
import com.vitor.Investmentaggregator.entities.User;
import com.vitor.Investmentaggregator.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor ArgumentCaptor<UUID> uuidCaptor;

    @InjectMocks
    private UserService userService;

    @Nested
    class CreateUser {

        @Test
        @DisplayName("Should create a user with success")
        void testCreateUser() {
            // Arrange
            var userId = UUID.randomUUID();
            var user = new User();
            user.setUserId(userId);
            user.setUsername("username");
            user.setPassword("encodedPassword");
            user.setEmail("email");

            doReturn("encodedPassword")
                    .when(passwordEncoder)
                    .encode("password");
            
            doReturn(user)
                    .when(userRepository)
                    .save(userCaptor.capture());

            var input = new CreateUserDto("username", "password", "email");

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output, "The returned UUID should not be null");
            assertEquals(userId, output, "The returned UUID should match the saved user's ID");

            var savedUser = userCaptor.getValue();
           
            assertEquals(input.username(), savedUser.getUsername(),
                         "The saved user's username should match the input");
            
            assertEquals("encodedPassword", savedUser.getPassword(),
                         "The saved user's password should be encoded");
            
            assertEquals(input.email(), savedUser.getEmail(), 
                         "The saved user's email should match the input");

            verify(userRepository).save(any(User.class));
        }

        @Test
        @DisplayName("Should throw an exception when an error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){
            // Arrange

            doThrow(new RuntimeException())
                    .when(userRepository)
                    .save(any());

            var input = new CreateUserDto("username", "password", "email");

            // Act &  Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }


    @Nested // not finished yet 
    class DeleteUserById {

        @Test
        @DisplayName("Should delete a user with success when user exists")
        void testDeleteUserById() {
              // Arrange
              var userId = UUID.randomUUID();
              
              doReturn(true)
                      .when(userRepository)
                      .existsById(uuidCaptor.capture());

             doNothing()
                    .when(userRepository)
                    .deleteById(uuidCaptor.capture());
                    
            // Act
            userService.deleteUserById(userId.toString());

            // Assert
            var idList = uuidCaptor.getAllValues();
            assertEquals(userId, idList.get(0),
                         "The deleted user's ID should match the input");
            assertEquals(userId, idList.get(1),
                         "The deleted user's ID should match the input");

            verify(userRepository, times(1)).deleteById(any(UUID.class));
            verify(userRepository, times(1)).existsById(idList.get(0));


        }

        @Test
        @DisplayName("Should not delete a user when user does not exists")
        void testDeleteUserByIdWhenUserNotExist() {
              // Arrange
              var userId = UUID.randomUUID();
              
              doReturn(false)
                      .when(userRepository)
                      .existsById(uuidCaptor.capture());
            // Act
            userService.deleteUserById(userId.toString());

            // Assert
    
            assertEquals(userId, uuidCaptor.getValue());

            verify(userRepository, times(0))
                    .deleteById(any(UUID.class));
            
            verify(userRepository, times(1))
                    .existsById(uuidCaptor.getValue());

        }


    }


    @Nested
    class getUserById{

        
        @Test
        @DisplayName("Should get a user with success when Optional is not empty")
        void testGetUserById() {
        
            // Arrange
            var userId = UUID.randomUUID();
            var user = new User();
            user.setUserId(userId);
            user.setUsername("username");
            user.setPassword("encodedPassword");
            user.setEmail("email");
        
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidCaptor.capture());
        
            // Act
            var output = userService.getUserById(String.valueOf(userId));
        
            // Assert
            assertTrue(output.isPresent(), "The returned user should not be null");
        
            assertEquals(user.getUserId(), uuidCaptor.getValue(),
                         "The returned user's ID should match the input");
            
            assertEquals(user.getUsername(), output.get().getUsername(),
                         "The returned user's username should match the input");
            
            assertEquals(user.getEmail(), output.get().getEmail(),
                         "The returned user's email should match the input");
        }
        

        @Test
        @DisplayName("Should get a user with success when Optional is empty")
        void testGetUserByIdWhenOptionalIsEmpity() {
        
            // Arrange
            var userId = UUID.randomUUID();
        
            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidCaptor.capture());
        
            // Act
            var output = userService.getUserById(String.valueOf(userId));
        
            // Assert
            assertTrue(output.isEmpty(), "The returned user should be null");

            assertEquals(userId, uuidCaptor.getValue(),
                         "The returned user's ID should match the input");  
        }

        @Test
        @DisplayName("Should throw an exception when an error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){
            // Arrange
            var userId = UUID.randomUUID();
        
            doThrow(new RuntimeException())
                    .when(userRepository)
                    .findById(uuidCaptor.capture());
        
            // Act &  Assert
            assertThrows(RuntimeException.class, () -> userService.getUserById(String.valueOf(userId)));
        }

    }


    @Nested
    class getAllUsers{
        
        @Test
        @DisplayName("Should get all users with success")
        void testGetAllUsers() {
        
            // Arrange
            var userId = UUID.randomUUID();
            var user = new User();
            user.setUserId(userId);
            user.setUsername("username");
            user.setPassword("encodedPassword");
            user.setEmail("email");
        
            doReturn(List
                    .of(user))
                    .when(userRepository)
                    .findAll();
        
            // Act
            var output = userService.getAllUsers();
        
            // Assert
            assertNotNull(output, "The returned list should not be null");
        
            assertEquals(1, output.size(), "The returned list should have one user");
        }

        @Test
        @DisplayName("Should get all users with success when the list is empty")
        void testGetAllUsersWhenListIsEmpty() {
        
            // Arrange
        
            doReturn(List
                    .of())
                    .when(userRepository)
                    .findAll();
        
            // Act
            var output = userService.getAllUsers();
        
            // Assert
            assertNotNull(output, "The returned list should not be null");
        
            assertEquals(0, output.size(), "The returned list should be empty");
        }

        @Test
        @DisplayName("Should throw an exception when an error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){
            // Arrange
        
            doThrow(new RuntimeException())
                    .when(userRepository)
                    .findAll();
        
            // Act &  Assert
            assertThrows(RuntimeException.class, () -> userService.getAllUsers());
        }   


    }




    @Nested
    class updateUserById {

        @Test
        @DisplayName("Should update a user by id with success when user exists, username and password are not null") 
        void testUpdateUserById() {
             // Arrange
             var userId = UUID.randomUUID();
             var user = new User();
             user.setUserId(userId);
             user.setUsername("username");
             user.setPassword("encodedPassword");
             user.setEmail("email");
         
             doReturn(Optional.of(user))
                     .when(userRepository)
                     .findById(uuidCaptor.capture());
            
             doReturn(user)
                     .when(userRepository)
                     .save(userCaptor.capture());

            var input = new UpdateUserDto("newUsername", "newPassword");

            // Act

            userService.updateUserById(userId.toString(), input);

            // Assert

            var updatedUser = userCaptor.getValue();
            assertEquals(user.getUserId(), uuidCaptor.getValue());

            assertEquals(input.username(), updatedUser.getUsername(),
                         "The updated user's username should match the input");

            assertNotEquals(input.password(), user.getPassword());

            verify(userRepository, times(1))
                    .findById(uuidCaptor.getValue());
            
            verify(userRepository, times(1))
                    .save(user);
            
        }

        @Test
        @DisplayName("Should not update a user by id with success when user not exists") 
        void testUpdateUserByIdWhenUserNotExists() {
             // Arrange
             var userId = UUID.randomUUID();
         
             doReturn(Optional.empty())
                     .when(userRepository)
                     .findById(uuidCaptor.capture());
            
            var input = new UpdateUserDto("newUsername", "newPassword");

            // Act

            userService.updateUserById(userId.toString(), input);

            // Assert
            assertEquals(userId, uuidCaptor.getValue());

            verify(userRepository, times(1))
                    .findById(uuidCaptor.getValue());
            
            verify(userRepository, times(0))
                    .save(any(User.class));
            
        }
    }
}
