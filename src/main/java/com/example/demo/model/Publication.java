package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


}
