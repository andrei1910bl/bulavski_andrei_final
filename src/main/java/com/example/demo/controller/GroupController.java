package com.example.demo.controller;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        GroupDTO createdGroup = groupService.createGroup(groupDTO.getName(), groupDTO.getPhotoUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PostMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Void> addUserToGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        groupService.addUserToGroup(userId, groupId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDTO> updateGroup(
            @PathVariable Long groupId,
            @RequestParam(required = false) @NotEmpty(message = "Name cannot be empty") String name,
            @RequestParam(required = false) @NotEmpty(message = "Photo URL cannot be empty") String photoUrl) {

        GroupDTO updatedGroup = groupService.updateGroup(groupId, name, photoUrl);
        return ResponseEntity.ok(updatedGroup);
    }

    @GetMapping("/{groupId}/users")
    public ResponseEntity<List<ProfileDTO>> getUsersInGroup(@PathVariable Long groupId) {
        List<ProfileDTO> users = groupService.getProfilesInGroup(groupId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable Long userId) {
        List<GroupDTO> userGroups = groupService.getUserGroups(userId);
        return ResponseEntity.ok(userGroups);
    }
}