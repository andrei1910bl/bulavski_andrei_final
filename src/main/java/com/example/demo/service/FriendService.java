package com.example.demo.service;

import com.example.demo.dto.FriendDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Friend;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.FriendMapper;
import com.example.demo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    private final FriendMapper friendMapper = FriendMapper.INSTANCE;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Transactional
    public FriendDTO sendFriendRequest(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("Friend not found"));

        Friend friendRequest = new Friend();
        friendRequest.setUser(user);
        friendRequest.setFriend(friend);

        FriendDTO friendDTO = friendMapper.toDTO(friendRepository.save(friendRequest));
        log.info("Отправлен запрос на дружбу от пользователя с ID {} к пользователю с ID {}", userId, friendId);
        return friendDTO;
    }

    @Transactional
    public void acceptFriendRequest(Long userId, Long friendId) {
        Friend friendRequest = friendRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));
        friendRepository.save(friendRequest);
        log.info("Запрос на дружбу от пользователя с ID {} принят пользователем с ID {}", friendId, userId);
    }

    @Transactional
    public void removeFriend(Long userId, Long friendId) {
        Friend friend = friendRepository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found"));
        friendRepository.delete(friend);
        log.info("Пользователь с ID {} удалил пользователя с ID {} из друзей", userId, friendId);
    }

    public List<ProfileDTO> getFriends(Long userId) {
        List<Friend> friendsAsUser = friendRepository.findAllByUserId(userId);
        List<Friend> friendsAsFriend = friendRepository.findAllByFriendId(userId);

        List<Friend> allFriends = new ArrayList<>();
        allFriends.addAll(friendsAsUser);
        allFriends.addAll(friendsAsFriend);

        log.info("Получение списка друзей для пользователя с ID {}", userId);
        return allFriends.stream()
                .map(friend -> {
                    Long friendId;
                    if (friend.getUser().getId().equals(userId)) {
                        friendId = friend.getFriend().getId();
                    } else {
                        friendId = friend.getUser().getId();
                    }
                    return profileService.getProfileByUserId(friendId);
                })
                .collect(Collectors.toList());
    }
}