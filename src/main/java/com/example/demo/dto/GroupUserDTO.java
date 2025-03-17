package com.example.demo.dto;

import lombok.Data;

@Data
public class GroupUserDTO {

    private Long id;
    private UserDTO userDTO;
    private GroupDTO groupDTO;
}
