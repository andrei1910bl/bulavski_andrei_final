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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    private final GroupMapper groupMapper = GroupMapper.INSTANCE;
    private final ProfileMapper profileMapper = ProfileMapper.INSTANCE;

    @Transactional
    public GroupDTO createGroup(String name, String photoUrl) {
        log.info("Создание группы с именем: {}", name);
        Group group = new Group();
        group.setName(name);
        group.setPhotoUrl(photoUrl);
        group.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        GroupDTO groupDTO = groupMapper.toDTO(groupRepository.save(group));
        log.info("Группа успешно создана: {}", groupDTO);
        return groupDTO;
    }

    @Transactional
    public void addUserToGroup(Long userId, Long groupId) {
        log.info("Добавление пользователя с ID {} в группу с ID {}", userId, groupId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь с ID {} не найден", userId);
                    return new RuntimeException("User not found");
                });
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> {
                    log.error("Группа с ID {} не найдена", groupId);
                    return new RuntimeException("Group not found");
                });

        GroupUser groupUser = new GroupUser();
        groupUser.setUser(user);
        groupUser.setGroup(group);

        group.getGroupUsers().add(groupUser);
        groupRepository.save(group);
        log.info("Пользователь с ID {} успешно добавлен в группу с ID {}", userId, groupId);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        log.info("Удаление группы с ID: {}", groupId);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> {
                    log.error("Группа с ID {} не найдена", groupId);
                    return new RuntimeException("Group not found");
                });
        groupRepository.delete(group);
        log.info("Группа с ID {} успешно удалена", groupId);
    }

    @Transactional
    public GroupDTO updateGroup(Long groupId, String newName, String newPhotoUrl) {
        log.info("Обновление группы с ID: {}", groupId);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> {
                    log.error("Группа с ID {} не найдена", groupId);
                    return new RuntimeException("Group not found");
                });

        if (newName != null) {
            group.setName(newName);
        }
        if (newPhotoUrl != null) {
            group.setPhotoUrl(newPhotoUrl);
        }

        GroupDTO groupDTO = groupMapper.toDTO(groupRepository.save(group));
        log.info("Группа успешно обновлена: {}", groupDTO);
        return groupDTO;
    }

    public List<ProfileDTO> getProfilesInGroup(Long groupId) {
        log.info("Получение профилей в группе с ID: {}", groupId);
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> {
                    log.error("Группа с ID {} не найдена", groupId);
                    return new RuntimeException("Group not found");
                });

        return group.getGroupUsers()
                .stream()
                .map(groupUser -> {
                    User user = groupUser.getUser();
                    Profile profile = user.getProfile();
                    return profileMapper.toDTO(profile);
                })
                .collect(Collectors.toList());
    }

    public List<GroupDTO> getUserGroups(Long userId) {
        log.info("Получение групп пользователя с ID: {}", userId);
        return userRepository.findGroupsByUserId(userId)
                .stream()
                .map(groupMapper::toDTO)
                .collect(Collectors.toList());
    }
}