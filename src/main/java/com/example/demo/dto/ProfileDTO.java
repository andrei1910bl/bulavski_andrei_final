package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileDTO {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @Size(max = 255, message = "Photo URL should not exceed 255 characters")
    private String photoUrl;

    private String description;

    @Min(value = 0, message = "Age should be greater than or equal to 0")
    private Integer age;

    private UserDTO userDTO;
}