package com.example.demo.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    private String photoUrl;

    private String description;

    @Min(value = 0, message = "Age should be greater than or equal to 0")
    private Integer age;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}