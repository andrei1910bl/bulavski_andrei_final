package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatDTO {
    private Long id;
    private UserDTO firstUser;
    private UserDTO secondUser;
}