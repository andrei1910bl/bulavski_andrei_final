package com.example.demo.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "groups")
@EqualsAndHashCode(of = "id")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Group name should not be empty")
    @Size(min = 2, max = 100, message = "Group name should be between 2 and 100 characters")
    private String name;

    private Timestamp createdAt;

    @Size(max = 255, message = "Photo URL should not exceed 255 characters")
    private String photoUrl;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupUser> groupUsers;
}