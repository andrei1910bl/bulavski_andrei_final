package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageDTO {
    private Long id;
    private String content;
    private Timestamp sentAt;
    private UserDTO userDTO;
    private ChatDTO chatDTO;
    private GroupDTO groupDTO;
}