package com.example.demo.service;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserRoleSuccessfully() {
        Role role = new Role();
        role.setId(1);
        role.setRoleName("ROLE_USER");

        when(roleRepository.findByRoleName("ROLE_USER")).thenReturn(Optional.of(role));

        Role retrievedRole = roleService.getUserRole();

        assertNotNull(retrievedRole);
        assertEquals("ROLE_USER", retrievedRole.getRoleName());
        verify(roleRepository, times(1)).findByRoleName("ROLE_USER");
    }

    @Test
    void getUserRoleNotFound() {
        when(roleRepository.findByRoleName("ROLE_USER")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            roleService.getUserRole();
        });

        assertEquals("Роль ROLE_USER не найдена", exception.getMessage());
    }
}