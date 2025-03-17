package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerSuccessfully() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        when(authService.register(eq(userDTO))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = authController.register(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(authService).register(eq(userDTO));
    }

    @Test
    void loginSuccessfully() {
        String identifier = "test@example.com";
        String password = "password";
        UserDTO userDTO = new UserDTO();

        when(authService.login(eq(identifier), eq(password))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = authController.login(identifier, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(authService).login(eq(identifier), eq(password));
    }

    @Test
    void loginUnauthorized() {
        String identifier = "test@example.com";
        String password = "wrongpassword";

        when(authService.login(eq(identifier), eq(password))).thenReturn(null);

        ResponseEntity<UserDTO> response = authController.login(identifier, password);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(authService).login(eq(identifier), eq(password));
    }
}