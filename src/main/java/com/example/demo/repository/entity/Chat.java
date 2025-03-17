package com.example.demo.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_user_id", nullable = false)
    private User firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user_id", nullable = false)
    private User secondUser;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;
}