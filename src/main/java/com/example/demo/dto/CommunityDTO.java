package com.example.demo.dto;

import lombok.Data;

@Data
public class CommunityDTO {
    private Long id;
    private String name;
    private String description;
    private java.sql.Timestamp createdAt;
    private String photoUrl;
}
