package com.example.demo.dto;

import lombok.Data;


@Data
public class ChatDTO {
    private Long id;
    private UserDTO firstUser;
    private UserDTO secondUser;
}