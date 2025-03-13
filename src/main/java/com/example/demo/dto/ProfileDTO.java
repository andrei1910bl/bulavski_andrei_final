package com.example.demo.dto;

import lombok.Data;

@Data
public class ProfileDTO {

    private Long id;
    private String name;
    private String lastName;
    private String photoUrl;
    private String description;
    private Integer age;
    private UserDTO userDTO;
}
