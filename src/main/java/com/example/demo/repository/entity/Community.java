package com.example.demo.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Community name should not be empty")
    @Size(min = 2, max = 100, message = "Community name should be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    private Timestamp createdAt;

    @Size(max = 255, message = "Photo URL should not exceed 255 characters")
    private String photoUrl;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Member> members;
}