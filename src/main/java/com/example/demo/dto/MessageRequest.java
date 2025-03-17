package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private Long userId;
    private Long chatId;
    private Long groupId;
    private String content;
}