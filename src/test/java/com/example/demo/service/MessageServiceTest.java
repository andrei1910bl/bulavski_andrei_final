package com.example.demo.service;

import com.example.demo.dto.ChatDTO;
import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Chat;
import com.example.demo.repository.entity.Group;
import com.example.demo.repository.entity.Message;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.ChatMapper;
import com.example.demo.service.mapper.GroupMapper;
import com.example.demo.service.mapper.MessageMapper;
import com.example.demo.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ChatMapper chatMapper;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private MessageMapper messageMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessageSuccessfully() {
        Long userId = 1L;
        Long chatId = 2L;
        Long groupId = 3L;
        String content = "Hello, World!";

        User user = new User();
        user.setId(userId);

        Chat chat = new Chat();
        chat.setId(chatId);

        Group group = new Group();
        group.setId(groupId);

        Message message = new Message();
        message.setId(1L);
        message.setContent(content);
        message.setUser(user);
        message.setChat(chat);
        message.setGroup(group);
        message.setSentAt(new Timestamp(System.currentTimeMillis()));

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(content);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageMapper.toDTO(message)).thenReturn(messageDTO);
        when(userMapper.toDTO(user)).thenReturn(new UserDTO());
        when(chatMapper.toDTO(chat)).thenReturn(new ChatDTO());
        when(groupMapper.toDTO(group)).thenReturn(new GroupDTO());

        MessageDTO savedMessage = messageService.sendMessage(userId, chatId, groupId, content);

        assertNotNull(savedMessage);
        assertEquals(content, savedMessage.getContent());
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void deleteMessageSuccessfully() {
        Long messageId = 1L;

        messageService.deleteMessage(messageId);

        verify(messageRepository, times(1)).deleteById(messageId);
    }

    @Test
    void updateMessageSuccessfully() {
        Long messageId = 1L;
        String newContent = "Updated content";

        Message message = new Message();
        message.setId(messageId);
        message.setContent("Old content");

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(newContent);

        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageMapper.toDTO(message)).thenReturn(messageDTO);

        MessageDTO updatedMessage = messageService.updateMessage(messageId, newContent);

        assertNotNull(updatedMessage);
        assertEquals(newContent, updatedMessage.getContent());
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void updateMessageNotFound() {
        Long messageId = 1L;
        String newContent = "Updated content";

        when(messageRepository.findById(messageId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            messageService.updateMessage(messageId, newContent);
        });

        assertEquals("Message not found", exception.getMessage());
    }
}