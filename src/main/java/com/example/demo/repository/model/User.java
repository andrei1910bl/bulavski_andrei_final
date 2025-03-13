package com.example.demo.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String password;
    private Timestamp createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    // пригласил дружить
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Friend> friends;

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
    private Set<Friend> friendOf;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL)
    private List<Chat> chatAsFirstUser;

    @OneToMany(mappedBy = "secondUser", cascade = CascadeType.ALL)
    private List<Chat> chatAsSecondUser;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<GroupUser> groupUsers;
    //todo
}
