package com.example.demo.service;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.entity.Profile;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.ProfileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    private final ProfileMapper profileMapper = ProfileMapper.INSTANCE;

    @Transactional
    public ProfileDTO createProfile(User user, ProfileDTO profileDTO) {
        log.info("Создание профиля для пользователя с ID: {}", user.getId());
        Profile profile = profileMapper.toEntity(profileDTO);
        profile.setUser(user);
        ProfileDTO createdProfileDTO = profileMapper.toDTO(profileRepository.save(profile));
        log.info("Профиль успешно создан: {}", createdProfileDTO);
        return createdProfileDTO;
    }

    @Transactional
    public ProfileDTO updateProfile(Long userId, ProfileDTO profileDTO) {
        log.info("Обновление профиля для пользователя с ID: {}", userId);
        Profile profile = profileRepository.findByUserId(userId);
        if (profile != null) {
            profile.setName(profileDTO.getName());
            profile.setLastName(profileDTO.getLastName());
            profile.setPhotoUrl(profileDTO.getPhotoUrl());
            profile.setDescription(profileDTO.getDescription());
            profile.setAge(profileDTO.getAge());
            ProfileDTO updatedProfileDTO = profileMapper.toDTO(profileRepository.save(profile));
            log.info("Профиль успешно обновлен: {}", updatedProfileDTO);
            return updatedProfileDTO;
        }
        log.error("Профиль не найден для пользователя с ID: {}", userId);
        throw new RuntimeException("Profile not found for userId: " + userId);
    }

    public ProfileDTO getProfileByUserId(Long userId) {
        log.info("Получение профиля для пользователя с ID: {}", userId);
        Profile profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            log.error("Профиль не найден для пользователя с ID: {}", userId);
            throw new RuntimeException("Profile not found for userId: " + userId);
        }
        return profileMapper.toDTO(profile);
    }
}