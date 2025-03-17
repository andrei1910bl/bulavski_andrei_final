package com.example.demo.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();


}