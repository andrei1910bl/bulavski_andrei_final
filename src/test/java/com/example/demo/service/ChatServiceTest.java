package com.example.demo.service;

import com.example.demo.dto.ChatDTO;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Chat;
import com.example.demo.repository.entity.Profile;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.ChatMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    private final ChatMapper chatMapper = ChatMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createChatSuccessfully() {
        Long userId = 1L;
        Long friendId = 2L;

        User firstUser = new User();
        firstUser.setId(userId);
        User secondUser = new User();
        secondUser.setId(friendId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(firstUser));
        when(userRepository.findById(friendId)).thenReturn(Optional.of(secondUser));

        Chat chat = new Chat();
        chat.setFirstUser(firstUser);
        chat.setSecondUser(secondUser);
        when(chatRepository.save(any(Chat.class))).thenReturn(chat);

        ChatDTO chatDTO = chatService.createChat(userId, friendId);

        assertNotNull(chatDTO);
        assertEquals(userId, chatDTO.getFirstUser().getId());
        assertEquals(friendId, chatDTO.getSecondUser().getId());
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void deleteChatSuccessfully() {
        Long chatId = 1L;

        Chat chat = new Chat();
        chat.setId(chatId);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));

        chatService.deleteChat(chatId);

        verify(chatRepository, times(1)).delete(chat);
    }

    @Test
    void getAllUserChats() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Chat chat1 = new Chat();
        chat1.setFirstUser(user);
        Chat chat2 = new Chat();
        chat2.setSecondUser(user);

        when(chatRepository.findAllByFirstUserId(userId)).thenReturn(new ArrayList<>(List.of(chat1)));
        when(chatRepository.findAllBySecondUserId(userId)).thenReturn(new ArrayList<>(List.of(chat2)));

        List<ChatDTO> chats = chatService.getAllUserChats(userId);

        assertEquals(2, chats.size());
        verify(chatRepository, times(1)).findAllByFirstUserId(userId);
        verify(chatRepository, times(1)).findAllBySecondUserId(userId);
    }

    @Test
    void getUserChatsByName() {
        Long userId = 1L;
        String name = "John";

        Profile profile = new Profile();
        User user = new User();
        profile.setUser(user);
        user.setId(2L);
        List<Profile> profiles = new ArrayList<>(List.of(profile));

        when(profileRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name))
                .thenReturn(profiles);

        Chat chat1 = new Chat();
        chat1.setFirstUser(user);
        Chat chat2 = new Chat();
        chat2.setSecondUser(user);

        when(chatRepository.findAllByFirstUserId(userId)).thenReturn(new ArrayList<>(List.of(chat1)));
        when(chatRepository.findAllBySecondUserId(userId)).thenReturn(new ArrayList<>(List.of(chat2)));

        List<ChatDTO> chats = chatService.getUserChatsByName(userId, name);

        assertEquals(2, chats.size());
        verify(profileRepository, times(1)).findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
}