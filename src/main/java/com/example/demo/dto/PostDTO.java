package com.example.demo.dto;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDTO {
    private Long id;
    private UserDTO userDTO;
    private String content;
    private Timestamp createdAt;
}
