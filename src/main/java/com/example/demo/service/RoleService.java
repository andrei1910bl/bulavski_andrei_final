package com.example.demo.service;

import com.example.demo.repository.entity.Role;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role getUserRole() {
        log.info("Получение роли USER");
        return roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> {
                    log.error("Роль USER не найдена");
                    return new RuntimeException("Роль ROLE_USER не найдена");
                });
    }
}