package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Role;
import com.example.demo.repository.entity.User;
import com.example.demo.repository.entity.UserRole;
import com.example.demo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        log.info("Регистрация пользователя: {}", userDTO.getUsername());

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent() ||
                userRepository.findByEmail(userDTO.getEmail()).isPresent() ||
                userRepository.findByPhoneNumber(userDTO.getPhoneNumber()).isPresent()) {
            log.warn("Пользователь с таким именем, email или номером телефона уже существует");
            throw new RuntimeException("Пользователь с таким именем, email или номером телефона уже существует");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        User user = UserMapper.INSTANCE.toEntity(userDTO);

        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> {
                    log.error("Роль не найдена: ROLE_USER");
                    return new RuntimeException("Роль не найдена");
                });

        UserRole userRoleEntity = new UserRole();
        userRoleEntity.setUser(user);
        userRoleEntity.setRole(userRole);

        if (user.getUserRoles() == null) {
            user.setUserRoles(new HashSet<>());
        }
        user.getUserRoles().add(userRoleEntity);

        user = userRepository.save(user);
        log.info("Пользователь успешно зарегистрирован: {}", userDTO.getUsername());

        return UserMapper.INSTANCE.toDTO(user);
    }

    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return UserMapper.INSTANCE.toDTO(user);
    }
}