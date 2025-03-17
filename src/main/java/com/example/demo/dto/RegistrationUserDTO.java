package com.example.demo.dto;

import lombok.Data;

@Data
public class RegistrationUserDTO {
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
}
