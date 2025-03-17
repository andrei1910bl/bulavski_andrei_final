package com.example.demo.controller;

import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.MessageRequest;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public MessageDTO sendMessage(@RequestBody MessageRequest messageRequest) {
        return messageService.sendMessage(
                messageRequest.getUserId(),
                messageRequest.getChatId(),
                messageRequest.getGroupId(),
                messageRequest.getContent()
        );
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
    }

    @PutMapping("/{messageId}")
    public MessageDTO updateMessage(
            @PathVariable Long messageId,
            @RequestParam String newContent) {
        return messageService.updateMessage(messageId, newContent);
    }

}