package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.entity.Role;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.UserRole;
import com.example.demo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        log.info("Обновление пользователя с ID: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            userRepository.save(user);
            log.info("Пользователь с ID {} успешно обновлён", id);
            return UserMapper.INSTANCE.toDTO(user);
        }
        log.error("Пользователь с ID {} не найден", id);
        return null;
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Удаление пользователя с ID: {}", id);
        userRepository.deleteById(id);
        log.info("Пользователь с ID {} успешно удалён", id);
    }

    public UserDTO getUserById(Long id) {
        log.info("Получение пользователя с ID: {}", id);
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::toDTO)
                .orElse(null);
    }
}