package com.example.demo.controller;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.User;
import com.example.demo.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProfile_UserExists() {
        Long userId = 1L;
        ProfileDTO profileDTO = new ProfileDTO();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(new User()));

        ResponseEntity<ProfileDTO> response = profileController.createProfile(userId, profileDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepository).findById(userId);
        verify(profileService).createProfile(any(User.class), eq(profileDTO));
    }

    @Test
    public void testCreateProfile_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            profileController.createProfile(1L, new ProfileDTO());
        });

        assertEquals("User not found with id: 1", thrown.getMessage());
        verify(userRepository).findById(1L);
        verify(profileService, never()).createProfile(any(), any());
    }

    @Test
    public void testUpdateProfile() {
        ProfileDTO profileDTO = new ProfileDTO();

        when(profileService.updateProfile(anyLong(), any(ProfileDTO.class))).thenReturn(profileDTO);

        ResponseEntity<ProfileDTO> response = profileController.updateProfile(1L, profileDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(profileService).updateProfile(1L, profileDTO);
    }

    @Test
    public void testGetProfile() {
        ProfileDTO profileDTO = new ProfileDTO();

        when(profileService.getProfileByUserId(1L)).thenReturn(profileDTO);

        ResponseEntity<ProfileDTO> response = profileController.getProfile(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(profileService).getProfileByUserId(1L);
    }
}