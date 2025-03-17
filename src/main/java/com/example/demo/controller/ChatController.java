package com.example.demo.controller;

import com.example.demo.dto.ChatDTO;
import com.example.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/{userId}/{friendId}")
    public ChatDTO createChat(@PathVariable Long userId, @PathVariable Long friendId) {
        return chatService.createChat(userId, friendId);
    }

    @DeleteMapping("/{chatId}")
    public void deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
    }

    @GetMapping("/{userId}/search")
    public List<ChatDTO> getUserChatsByName(@PathVariable Long userId, @RequestParam String name) {
        return chatService.getUserChatsByName(userId, name);
    }

    @GetMapping("/all/{userId}")
    public List<ChatDTO> getAllUserChats(@PathVariable Long userId) {
        return chatService.getAllUserChats(userId);
    }
}