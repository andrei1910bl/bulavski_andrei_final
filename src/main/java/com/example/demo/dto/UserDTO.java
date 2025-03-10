package com.example.demo.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Integer phoneNumber;
    private LocalDate createdAt;


}
