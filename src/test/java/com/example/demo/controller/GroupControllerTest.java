package com.example.demo.controller;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GroupControllerTest {

    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGroupSuccessfully() {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName("Test Group");
        groupDTO.setPhotoUrl("http://example.com/photo.png");

        when(groupService.createGroup(eq("Test Group"), eq("http://example.com/photo.png"))).thenReturn(groupDTO);

        ResponseEntity<GroupDTO> response = groupController.createGroup(groupDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(groupDTO, response.getBody());
        verify(groupService).createGroup(eq("Test Group"), eq("http://example.com/photo.png"));
    }

    @Test
    void addUserToGroupSuccessfully() {
        Long userId = 1L;
        Long groupId = 2L;

        ResponseEntity<Void> response = groupController.addUserToGroup(userId, groupId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(groupService).addUserToGroup(eq(userId), eq(groupId));
    }

    @Test
    void deleteGroupSuccessfully() {
        Long groupId = 1L;

        ResponseEntity<Void> response = groupController.deleteGroup(groupId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(groupService).deleteGroup(eq(groupId));
    }

    @Test
    void updateGroupSuccessfully() {
        Long groupId = 1L;
        String newName = "Updated Group";
        String newPhotoUrl = "http://example.com/newphoto.png";
        GroupDTO updatedGroup = new GroupDTO();

        when(groupService.updateGroup(eq(groupId), eq(newName), eq(newPhotoUrl))).thenReturn(updatedGroup);

        ResponseEntity<GroupDTO> response = groupController.updateGroup(groupId, newName, newPhotoUrl);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGroup, response.getBody());
        verify(groupService).updateGroup(eq(groupId), eq(newName), eq(newPhotoUrl));
    }

    @Test
    void getUsersInGroupSuccessfully() {
        Long groupId = 1L;
        List<ProfileDTO> profiles = new ArrayList<>();

        when(groupService.getProfilesInGroup(eq(groupId))).thenReturn(profiles);

        ResponseEntity<List<ProfileDTO>> response = groupController.getUsersInGroup(groupId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profiles, response.getBody());
        verify(groupService).getProfilesInGroup(eq(groupId));
    }

    @Test
    void getUserGroupsSuccessfully() {
        Long userId = 1L;
        List<GroupDTO> groups = new ArrayList<>();

        when(groupService.getUserGroups(eq(userId))).thenReturn(groups);

        ResponseEntity<List<GroupDTO>> response = groupController.getUserGroups(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(groups, response.getBody());
        verify(groupService).getUserGroups(eq(userId));
    }
}