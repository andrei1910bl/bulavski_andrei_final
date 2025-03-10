package com.example.demo.dto;

import lombok.Data;

@Data
public class UserRoleDTO {
    private Long id;
    private UserDTO userDTO;
    private RoleDTO roleDTO;
}
