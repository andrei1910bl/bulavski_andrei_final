package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class GroupDTO {

    private Long id;

    @NotEmpty(message = "Group name should not be empty")
    @Size(min = 2, max = 100, message = "Group name should be between 2 and 100 characters")
    private String name;

    private Timestamp createdAt;

    @Size(max = 255, message = "Photo URL should not exceed 255 characters")
    private String photoUrl;
}