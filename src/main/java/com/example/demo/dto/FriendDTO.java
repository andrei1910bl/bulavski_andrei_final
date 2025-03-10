package com.example.demo.dto;

import lombok.Data;

@Data
public class FriendDTO {
    private Long id;
    private UserDTO userDTO;
    private UserDTO friendDTO;
}
