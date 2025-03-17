package com.example.demo.controller;

import com.example.demo.dto.FriendDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.service.FriendService;
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

class FriendControllerTest {

    @InjectMocks
    private FriendController friendController;

    @Mock
    private FriendService friendService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendFriendRequestSuccessfully() {
        Long userId = 1L;
        Long friendId = 2L;
        FriendDTO friendDTO = new FriendDTO();

        when(friendService.sendFriendRequest(eq(userId), eq(friendId))).thenReturn(friendDTO);

        FriendDTO response = friendController.sendFriendRequest(userId, friendId);

        assertEquals(friendDTO, response);
        verify(friendService).sendFriendRequest(eq(userId), eq(friendId));
    }

    @Test
    void acceptFriendRequestSuccessfully() {
        Long userId = 1L;
        Long friendId = 2L;

        friendController.acceptFriendRequest(userId, friendId);

        verify(friendService).acceptFriendRequest(eq(userId), eq(friendId));
    }

    @Test
    void removeFriendSuccessfully() {
        Long userId = 1L;
        Long friendId = 2L;

        friendController.removeFriend(userId, friendId);

        verify(friendService).removeFriend(eq(userId), eq(friendId));
    }

    @Test
    void getFriendsSuccessfully() {
        Long userId = 1L;
        List<ProfileDTO> profiles = new ArrayList<>();

        when(friendService.getFriends(eq(userId))).thenReturn(profiles);

        List<ProfileDTO> response = friendController.getFriends(userId);

        assertEquals(profiles, response);
        verify(friendService).getFriends(eq(userId));
    }
}