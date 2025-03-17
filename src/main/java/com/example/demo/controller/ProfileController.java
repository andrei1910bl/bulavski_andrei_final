package com.example.demo.controller;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.User;
import com.example.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<ProfileDTO> createProfile(@PathVariable Long userId, @Valid @RequestBody ProfileDTO profileDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        ProfileDTO createdProfile = profileService.createProfile(user, profileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable Long userId, @Valid @RequestBody ProfileDTO profileDTO) {
        ProfileDTO updatedProfile = profileService.updateProfile(userId, profileDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long userId) {
        ProfileDTO profileDTO = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profileDTO);
    }
}