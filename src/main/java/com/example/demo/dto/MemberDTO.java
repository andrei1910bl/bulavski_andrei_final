package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MemberDTO {
    private Long id;
    private CommunityDTO communityDTO;
    private UserDTO userDTO;
    private Timestamp joinedAt;
    private String position;
}
