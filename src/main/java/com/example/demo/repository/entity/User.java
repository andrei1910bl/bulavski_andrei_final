package com.example.demo.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "username should not be empty")
    @Size(min = 2, max = 30, message = "name should be between 2 and 30 characters")
    private String username;

    @NotEmpty(message = "Should not be empty")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @NotEmpty(message = "Phone number not be empty")
    private String phoneNumber;

    @NotEmpty(message = "password should not be empty")
    private String password;

    private Timestamp createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Friend> friends = new HashSet<>();

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
    private Set<Friend> friendOf = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "firstUser", cascade = CascadeType.ALL)
    private List<Chat> chatAsFirstUser;

    @OneToMany(mappedBy = "secondUser", cascade = CascadeType.ALL)
    private List<Chat> chatAsSecondUser;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Member> members;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<GroupUser> groupUsers = new HashSet<>();

    public void addUserRole(UserRole userRole) {
        userRoles.add(userRole);
        userRole.setUser(this);
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
        friend.setUser(this);
    }

}