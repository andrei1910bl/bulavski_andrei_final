package com.example.demo.controller;

import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.MessageRequest;
import com.example.demo.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessageSuccessfully() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setUserId(1L);
        messageRequest.setChatId(2L);
        messageRequest.setGroupId(3L);
        messageRequest.setContent("Hello");

        MessageDTO messageDTO = new MessageDTO();
        when(messageService.sendMessage(eq(1L), eq(2L), eq(3L), eq("Hello"))).thenReturn(messageDTO);

        MessageDTO response = messageController.sendMessage(messageRequest);

        assertEquals(messageDTO, response);
        verify(messageService).sendMessage(eq(1L), eq(2L), eq(3L), eq("Hello"));
    }

    @Test
    void deleteMessageSuccessfully() {
        Long messageId = 1L;

        messageController.deleteMessage(messageId);

        verify(messageService).deleteMessage(eq(messageId));
    }

    @Test
    void updateMessageSuccessfully() {
        Long messageId = 1L;
        String newContent = "Updated content";
        MessageDTO messageDTO = new MessageDTO();

        when(messageService.updateMessage(eq(messageId), eq(newContent))).thenReturn(messageDTO);

        MessageDTO response = messageController.updateMessage(messageId, newContent);

        assertEquals(messageDTO, response);
        verify(messageService).updateMessage(eq(messageId), eq(newContent));
    }
}