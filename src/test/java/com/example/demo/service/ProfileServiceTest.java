package com.example.demo.service;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.entity.Profile;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.ProfileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProfileSuccessfully() {
        User user = new User();
        user.setId(1L);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName("John");
        profileDTO.setLastName("Doe");

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setName(profileDTO.getName());
        profile.setLastName(profileDTO.getLastName());

        when(profileMapper.toEntity(profileDTO)).thenReturn(profile);
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileMapper.toDTO(profile)).thenReturn(profileDTO);

        ProfileDTO createdProfile = profileService.createProfile(user, profileDTO);

        assertNotNull(createdProfile);
        assertEquals(profileDTO.getName(), createdProfile.getName());
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    void updateProfileSuccessfully() {
        Long userId = 1L;
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName("Jane");
        profileDTO.setLastName("Doe");

        Profile existingProfile = new Profile();
        existingProfile.setUser(new User());
        existingProfile.setName("Old Name");
        existingProfile.setLastName("Old Last Name");

        when(profileRepository.findByUserId(userId)).thenReturn(existingProfile);
        when(profileMapper.toDTO(existingProfile)).thenReturn(profileDTO);
        when(profileRepository.save(any(Profile.class))).thenReturn(existingProfile);

        ProfileDTO updatedProfile = profileService.updateProfile(userId, profileDTO);

        assertNotNull(updatedProfile);
        assertEquals(profileDTO.getName(), updatedProfile.getName());
        verify(profileRepository, times(1)).save(existingProfile);
    }

    @Test
    void updateProfileNotFound() {
        Long userId = 1L;
        ProfileDTO profileDTO = new ProfileDTO();

        when(profileRepository.findByUserId(userId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profileService.updateProfile(userId, profileDTO);
        });

        assertEquals("Profile not found for userId: " + userId, exception.getMessage());
    }

    @Test
    void getProfileByUserIdSuccessfully() {
        Long userId = 1L;

        Profile profile = new Profile();
        profile.setUser(new User());
        profile.setName("John");
        profile.setLastName("Doe");

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(profile.getName());
        profileDTO.setLastName(profile.getLastName());

        when(profileRepository.findByUserId(userId)).thenReturn(profile);
        when(profileMapper.toDTO(profile)).thenReturn(profileDTO);

        ProfileDTO fetchedProfile = profileService.getProfileByUserId(userId);

        assertNotNull(fetchedProfile);
        assertEquals(profile.getName(), fetchedProfile.getName());
        verify(profileRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getProfileByUserIdNotFound() {
        Long userId = 1L;

        when(profileRepository.findByUserId(userId)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profileService.getProfileByUserId(userId);
        });

        assertEquals("Profile not found for userId: " + userId, exception.getMessage());
    }
}