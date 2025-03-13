package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String photoUrl;
    private String description;
    private Integer age;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
