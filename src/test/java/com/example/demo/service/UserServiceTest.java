package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUserReturnsUpdatedDTO() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updatedUser");
        userDTO.setEmail("updated@example.com");
        userDTO.setPhoneNumber("123456789");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUser");
        existingUser.setEmail("old@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserDTO result = userService.updateUser(userId, userDTO);

        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUserReturnsNullIfNotFound() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updatedUser");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserDTO result = userService.updateUser(userId, userDTO);

        assertNull(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUserCallsDelete() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getUserByIdReturnsDTOIfFound() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void getUserByIdReturnsNullIfNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserDTO result = userService.getUserById(userId);

        assertNull(result);
    }
}