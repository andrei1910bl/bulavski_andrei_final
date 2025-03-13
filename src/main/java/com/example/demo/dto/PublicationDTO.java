package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PublicationDTO {
    private Long id;
    private String content;
    private Timestamp createdAt;
    private MemberDTO memberDTO;
}
