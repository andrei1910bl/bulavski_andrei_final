package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private java.sql.Timestamp createdAt;
    private String photoUrl;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Member> members;

}
