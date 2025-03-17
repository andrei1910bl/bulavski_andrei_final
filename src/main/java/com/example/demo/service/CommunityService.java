package com.example.demo.service;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Community;
import com.example.demo.repository.entity.Member;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.CommunityMapper;
import com.example.demo.service.mapper.MemberMapper;
import com.example.demo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public CommunityDTO createCommunity(String name, String description, String photoUrl) {
        log.info("Создание сообщества с именем: {}", name);
        Community community = new Community();
        community.setName(name);
        community.setDescription(description);
        community.setPhotoUrl(photoUrl);
        community.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Community savedCommunity = communityRepository.save(community);
        log.info("Сообщество успешно создано: {}", savedCommunity);
        return CommunityMapper.INSTANCE.toDTO(savedCommunity);
    }

    @Transactional
    public CommunityDTO updateCommunity(Long communityId, String name, String description, String photoUrl) {
        log.info("Обновление сообщества с ID: {}", communityId);
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> {
                    log.error("Сообщество с ID {} не найдено", communityId);
                    return new RuntimeException("Community not found");
                });

        community.setName(name);
        community.setDescription(description);
        community.setPhotoUrl(photoUrl);

        Community updatedCommunity = communityRepository.save(community);
        log.info("Сообщество успешно обновлено: {}", updatedCommunity);
        return CommunityMapper.INSTANCE.toDTO(updatedCommunity);
    }

    @Transactional
    public void deleteCommunity(Long communityId) {
        log.info("Удаление сообщества с ID: {}", communityId);
        communityRepository.deleteById(communityId);
        log.info("Сообщество с ID {} успешно удалено", communityId);
    }

    public List<CommunityDTO> getAllCommunitiesByUser(Long userId) {
        log.info("Получение всех сообществ для пользователя с ID: {}", userId);
        List<Community> communities = memberRepository.findCommunitiesByUserId(userId);
        return communities.stream()
                .map(CommunityMapper.INSTANCE::toDTO)
                .toList();
    }

    public List<CommunityDTO> findCommunitiesByName(String name) {
        log.info("Поиск сообществ по имени: {}", name);
        List<Community> communities = communityRepository.findByNameContaining(name);
        return communities.stream()
                .map(CommunityMapper.INSTANCE::toDTO)
                .toList();
    }

    @Transactional
    public MemberDTO addMemberToCommunity(Long userId, Long communityId, String position) {
        log.info("Добавление пользователя с ID {} в сообщество с ID {} на позицию {}", userId, communityId, position);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь с ID {} не найден", userId);
                    return new RuntimeException("User not found");
                });
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> {
                    log.error("Сообщество с ID {} не найдено", communityId);
                    return new RuntimeException("Community not found");
                });

        Member member = new Member();
        member.setUser(user);
        member.setCommunity(community);
        member.setJoinedAt(new Timestamp(System.currentTimeMillis()));
        member.setPosition(position);

        Member savedMember = memberRepository.save(member);
        MemberDTO memberDTO = MemberMapper.INSTANCE.toDTO(savedMember);
        memberDTO.setCommunityDTO(CommunityMapper.INSTANCE.toDTO(community));
        memberDTO.setUserDTO(UserMapper.INSTANCE.toDTO(user));

        log.info("Пользователь успешно добавлен в сообщество: {}", memberDTO);
        return memberDTO;
    }

    @Transactional
    public void removeMemberFromCommunity(Long memberId) {
        log.info("Удаление участника с ID: {}", memberId);
        memberRepository.deleteById(memberId);
        log.info("Участник с ID {} успешно удален из сообщества", memberId);
    }

    public List<MemberDTO> getAllMembersOfCommunity(Long communityId) {
        log.info("Получение всех участников сообщества с ID: {}", communityId);
        List<Member> members = memberRepository.findByCommunityId(communityId);
        return members.stream()
                .map(MemberMapper.INSTANCE::toDTO)
                .toList();
    }
}