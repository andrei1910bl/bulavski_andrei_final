package com.example.demo.service;

import com.example.demo.dto.MessageDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private GroupRepository groupRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final ChatMapper chatMapper = ChatMapper.INSTANCE;
    private final GroupMapper groupMapper = GroupMapper.INSTANCE;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    @Transactional
    public MessageDTO sendMessage(Long userId, Long chatId, Long groupId, String content) {
        log.info("Отправка сообщения от пользователя с ID {} в чат с ID {} в группе с ID {}", userId, chatId, groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь с ID {} не найден", userId);
                    return new RuntimeException("User not found");
                });

        Chat chat = chatId != null ? chatRepository.findById(chatId).orElse(null) : null;
        Group group = groupId != null ? groupRepository.findById(groupId).orElse(null) : null;

        Message message = new Message();
        message.setContent(content);
        message.setSentAt(new Timestamp(System.currentTimeMillis()));
        message.setUser(user);
        message.setChat(chat);
        message.setGroup(group);

        Message savedMessage = messageRepository.save(message);
        MessageDTO messageDTO = messageMapper.toDTO(savedMessage);
        messageDTO.setUserDTO(userMapper.toDTO(user));
        if (chat != null) {
            messageDTO.setChatDTO(chatMapper.toDTO(chat));
        }
        if (group != null) {
            messageDTO.setGroupDTO(groupMapper.toDTO(group));
        }

        log.info("Сообщение успешно отправлено: {}", messageDTO);
        return messageDTO;
    }

    @Transactional
    public void deleteMessage(Long messageId) {
        log.info("Удаление сообщения с ID: {}", messageId);
        messageRepository.deleteById(messageId);
        log.info("Сообщение с ID {} успешно удалено", messageId);
    }

    @Transactional
    public MessageDTO updateMessage(Long messageId, String newContent) {
        log.info("Обновление сообщения с ID: {}", messageId);
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setContent(newContent);
            Message updatedMessage = messageRepository.save(message);
            log.info("Сообщение с ID {} успешно обновлено", messageId);
            return messageMapper.toDTO(updatedMessage);
        } else {
            log.error("Сообщение с ID {} не найдено", messageId);
            throw new RuntimeException("Message not found");
        }
    }
}