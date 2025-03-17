package com.example.demo.controller;

import com.example.demo.dto.ChatDTO;
import com.example.demo.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @Mock
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createChatSuccessfully() {
        Long userId = 1L;
        Long friendId = 2L;
        ChatDTO chatDTO = new ChatDTO();

        when(chatService.createChat(eq(userId), eq(friendId))).thenReturn(chatDTO);

        ChatDTO response = chatController.createChat(userId, friendId);

        assertEquals(chatDTO, response);
        verify(chatService).createChat(eq(userId), eq(friendId));
    }

    @Test
    void deleteChatSuccessfully() {
        Long chatId = 1L;

        chatController.deleteChat(chatId);

        verify(chatService).deleteChat(eq(chatId));
    }

    @Test
    void getUserChatsByNameSuccessfully() {
        Long userId = 1L;
        String chatName = "Test Chat";
        List<ChatDTO> chats = new ArrayList<>();

        when(chatService.getUserChatsByName(eq(userId), eq(chatName))).thenReturn(chats);

        List<ChatDTO> response = chatController.getUserChatsByName(userId, chatName);

        assertEquals(chats, response);
        verify(chatService).getUserChatsByName(eq(userId), eq(chatName));
    }

    @Test
    void getAllUserChatsSuccessfully() {
        Long userId = 1L;
        List<ChatDTO> chats = new ArrayList<>();

        when(chatService.getAllUserChats(eq(userId))).thenReturn(chats);

        List<ChatDTO> response = chatController.getAllUserChats(userId);

        assertEquals(chats, response);
        verify(chatService).getAllUserChats(eq(userId));
    }
}