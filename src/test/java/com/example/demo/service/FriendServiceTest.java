package com.example.demo.service;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Friend;
import com.example.demo.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @InjectMocks
    private FriendService friendService;

    @Mock
    private FriendRepository friendRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void acceptFriendRequestSuccessfully() {
        Long userId = 1L;
        Long friendId = 2L;

        User user = new User();
        user.setId(userId);
        User friend = new User();
        friend.setId(friendId);

        Friend friendRequest = new Friend();
        friendRequest.setUser(user);
        friendRequest.setFriend(friend);

        when(friendRepository.findByUserIdAndFriendId(userId, friendId)).thenReturn(Optional.of(friendRequest));

        friendService.acceptFriendRequest(userId, friendId);

        verify(friendRepository, times(1)).save(friendRequest);
    }

    @Test
    void removeFriendSuccessfully() {
        Long userId = 1L;
        Long friendId = 2L;

        User user = new User();
        user.setId(userId);
        User friendUser = new User(); // Переименовали переменную
        friendUser.setId(friendId);

        Friend friendRelation = new Friend();
        friendRelation.setUser(user);
        friendRelation.setFriend(friendUser); // Используем новое имя переменной

        when(friendRepository.findByUserIdAndFriendId(userId, friendId)).thenReturn(Optional.of(friendRelation));

        friendService.removeFriend(userId, friendId);

        verify(friendRepository, times(1)).delete(friendRelation);
    }

    public List<ProfileDTO> getFriends(Long userId) {
        List<Friend> friendsAsUser = friendRepository.findAllByUserId(userId);
        List<Friend> friendsAsFriend = friendRepository.findAllByFriendId(userId);

        List<Friend> allFriends = new ArrayList<>();
        allFriends.addAll(friendsAsUser);
        allFriends.addAll(friendsAsFriend);

        return allFriends.stream()
                .map(friend -> {
                    User friendUser = friend.getFriend() != null ? friend.getFriend() : friend.getUser();
                    if (friendUser.getId() == null) {
                        throw new IllegalArgumentException("Friend ID cannot be null");
                    }
                    return profileService.getProfileByUserId(friendUser.getId());
                })
                .collect(Collectors.toList());
    }
}