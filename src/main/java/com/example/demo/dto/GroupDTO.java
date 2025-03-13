package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class GroupDTO {
    private Long id;
    private String name;
    private Timestamp createdAt;
    private String photoUrl;

}