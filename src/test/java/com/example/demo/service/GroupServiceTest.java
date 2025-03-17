package com.example.demo.service;

import com.example.demo.dto.GroupDTO;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Group;
import com.example.demo.repository.entity.GroupUser;
import com.example.demo.repository.entity.Profile;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.GroupMapper;
import com.example.demo.service.mapper.ProfileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private ProfileMapper profileMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGroupSuccessfully() {
        String groupName = "Test Group";
        String photoUrl = "http://example.com/photo.jpg";

        Group group = new Group();
        group.setId(1L);
        group.setName(groupName);
        group.setPhotoUrl(photoUrl);
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(groupName);
        groupDTO.setPhotoUrl(photoUrl);

        when(groupRepository.save(any(Group.class))).thenReturn(group);
        when(groupMapper.toDTO(group)).thenReturn(groupDTO);

        GroupDTO createdGroup = groupService.createGroup(groupName, photoUrl);

        assertNotNull(createdGroup);
        assertEquals(groupName, createdGroup.getName());
        verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    void addUserToGroupSuccessfully() {
        Long userId = 1L;
        Long groupId = 2L;

        User user = new User();
        user.setId(userId);

        Group group = new Group();
        group.setId(groupId);
        group.setGroupUsers(new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        groupService.addUserToGroup(userId, groupId);

        assertEquals(1, group.getGroupUsers().size());
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    void deleteGroupSuccessfully() {
        Long groupId = 1L;

        Group group = new Group();
        group.setId(groupId);

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));

        groupService.deleteGroup(groupId);

        verify(groupRepository, times(1)).delete(group);
    }

    @Test
    void updateGroupSuccessfully() {
        Long groupId = 1L;
        String newName = "Updated Group Name";
        String newPhotoUrl = "http://example.com/newphoto.jpg";

        Group group = new Group();
        group.setId(groupId);
        group.setName("Old Name");
        group.setPhotoUrl("http://example.com/oldphoto.jpg");

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(groupRepository.save(any(Group.class))).thenReturn(group);
        when(groupMapper.toDTO(group)).thenReturn(new GroupDTO());

        GroupDTO updatedGroup = groupService.updateGroup(groupId, newName, newPhotoUrl);

        assertNotNull(updatedGroup);
        assertEquals(newName, group.getName());
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    void getProfilesInGroupSuccessfully() {
        Long groupId = 1L;

        Group group = new Group();
        group.setId(groupId);
        group.setGroupUsers(new HashSet<>()); // Инициализация коллекции

        GroupUser groupUser = new GroupUser();
        User user = new User();
        Profile profile = new Profile();
        profile.setId(1L);

        user.setProfile(profile);
        groupUser.setUser(user);
        group.getGroupUsers().add(groupUser); // Теперь это не null

        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(profileMapper.toDTO(profile)).thenReturn(new ProfileDTO());

        List<ProfileDTO> profiles = groupService.getProfilesInGroup(groupId);

        assertEquals(1, profiles.size());
        verify(groupRepository, times(1)).findById(groupId);
    }

    @Test
    void getUserGroupsSuccessfully() {
        Long userId = 1L;

        Group group = new Group();
        group.setId(1L);
        group.setName("User's Group");

        when(userRepository.findGroupsByUserId(userId)).thenReturn(Collections.singletonList(group));
        when(groupMapper.toDTO(group)).thenReturn(new GroupDTO());

        List<GroupDTO> userGroups = groupService.getUserGroups(userId);

        assertEquals(1, userGroups.size());
        verify(userRepository, times(1)).findGroupsByUserId(userId);
    }
}