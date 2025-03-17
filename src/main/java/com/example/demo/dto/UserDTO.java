package com.example.demo.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Phone number not be empty")
    private String phoneNumber;
    private Timestamp createdAt;


}
