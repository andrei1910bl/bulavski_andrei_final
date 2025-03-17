package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUserSuccessfully() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();

        when(userService.updateUser(eq(userId), any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(userId, userDTO);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService).updateUser(eq(userId), any(UserDTO.class));
    }

    @Test
    void updateUserNotFound() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();

        when(userService.updateUser(eq(userId), any(UserDTO.class))).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.updateUser(userId, userDTO);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteUserSuccessfully() {
        Long userId = 1L;

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(204, response.getStatusCodeValue());
        verify(userService).deleteUser(eq(userId));
    }

    @Test
    void getUserByIdSuccessfully() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();

        when(userService.getUserById(userId)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService).getUserById(eq(userId));
    }

    @Test
    void getUserByIdNotFound() {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(404, response.getStatusCodeValue());
    }
}