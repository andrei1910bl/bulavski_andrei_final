package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private java.sql.Timestamp createdAt;
    private String photoUrl;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupUser> groupUsers;
}