package com.example.demo.service;

import com.example.demo.dto.ChatDTO;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Chat;
import com.example.demo.repository.entity.Profile;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.ChatMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatService {



    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private final ChatMapper chatMapper = ChatMapper.INSTANCE;

    @Transactional
    public ChatDTO createChat(Long userId, Long friendId) {
        log.info("Создание чата между пользователями с ID: {} и {}", userId, friendId);
        User firstUser = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь с ID {} не найден", userId);
                    return new RuntimeException("User not found");
                });
        User secondUser = userRepository.findById(friendId)
                .orElseThrow(() -> {
                    log.error("Друг с ID {} не найден", friendId);
                    return new RuntimeException("Friend not found");
                });

        Chat chat = new Chat();
        chat.setFirstUser(firstUser);
        chat.setSecondUser(secondUser);

        ChatDTO chatDTO = chatMapper.toDTO(chatRepository.save(chat));
        log.info("Чат успешно создан: {}", chatDTO);
        return chatDTO;
    }

    @Transactional
    public void deleteChat(Long chatId) {
        log.info("Удаление чата с ID: {}", chatId);
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> {
                    log.error("Чат с ID {} не найден", chatId);
                    return new RuntimeException("Chat not found");
                });
        chatRepository.delete(chat);
        log.info("Чат с ID {} успешно удален", chatId);
    }

    public List<ChatDTO> getAllUserChats(Long userId) {
        log.info("Получение всех чатов для пользователя с ID: {}", userId);
        List<Chat> chatsAsFirstUser = chatRepository.findAllByFirstUserId(userId);
        List<Chat> chatsAsSecondUser = chatRepository.findAllBySecondUserId(userId);

        List<Chat> allChats = chatsAsFirstUser;
        allChats.addAll(chatsAsSecondUser);

        return allChats.stream()
                .map(chatMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ChatDTO> getUserChatsByName(Long userId, String name) {
        log.info("Получение чатов пользователя с ID: {} по имени: {}", userId, name);
        List<Profile> matchingProfiles = profileRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        List<User> matchingUsers = matchingProfiles.stream().map(Profile::getUser).collect(Collectors.toList());

        List<Chat> allChats = chatRepository.findAllByFirstUserId(userId);
        allChats.addAll(chatRepository.findAllBySecondUserId(userId));

        return allChats.stream()
                .filter(chat -> matchingUsers.contains(chat.getFirstUser()) || matchingUsers.contains(chat.getSecondUser()))
                .map(chatMapper::toDTO)
                .collect(Collectors.toList());
    }
}