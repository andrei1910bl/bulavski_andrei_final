package com.example.demo.controller;

import com.example.demo.dto.FriendDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/{userId}/{friendId}")
    public FriendDTO sendFriendRequest(@PathVariable Long userId, @PathVariable Long friendId) {
        return friendService.sendFriendRequest(userId, friendId);
    }

    @PutMapping("/{userId}/{friendId}")
    public void acceptFriendRequest(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.acceptFriendRequest(userId, friendId);
    }

    @DeleteMapping("/{userId}/{friendId}")
    public void removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        friendService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}")
    public List<ProfileDTO> getFriends(@PathVariable Long userId) {
        return friendService.getFriends(userId);
    }
}