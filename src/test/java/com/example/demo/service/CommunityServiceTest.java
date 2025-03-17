package com.example.demo.service;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Community;
import com.example.demo.repository.entity.Member;
import com.example.demo.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommunityServiceTest {

    @InjectMocks
    private CommunityService communityService;

    @Mock
    private CommunityRepository communityRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCommunitySuccessfully() {
        String name = "Community Name";
        String description = "Community Description";
        String photoUrl = "http://example.com/photo.jpg";

        Community community = new Community();
        community.setName(name);
        community.setDescription(description);
        community.setPhotoUrl(photoUrl);
        community.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        when(communityRepository.save(any(Community.class))).thenReturn(community);

        CommunityDTO communityDTO = communityService.createCommunity(name, description, photoUrl);

        assertNotNull(communityDTO);
        assertEquals(name, communityDTO.getName());
        assertEquals(description, communityDTO.getDescription());
        assertEquals(photoUrl, communityDTO.getPhotoUrl());
        verify(communityRepository, times(1)).save(any(Community.class));
    }

    @Test
    void updateCommunitySuccessfully() {
        Long communityId = 1L;
        String newName = "Updated Community Name";
        String newDescription = "Updated Description";
        String newPhotoUrl = "http://example.com/newphoto.jpg";

        Community community = new Community();
        community.setId(communityId);
        community.setName("Old Name");
        community.setDescription("Old Description");
        community.setPhotoUrl("http://example.com/oldphoto.jpg");

        when(communityRepository.findById(communityId)).thenReturn(Optional.of(community));
        when(communityRepository.save(any(Community.class))).thenReturn(community);

        CommunityDTO updatedCommunityDTO = communityService.updateCommunity(communityId, newName, newDescription, newPhotoUrl);

        assertNotNull(updatedCommunityDTO);
        assertEquals(newName, updatedCommunityDTO.getName());
        assertEquals(newDescription, updatedCommunityDTO.getDescription());
        assertEquals(newPhotoUrl, updatedCommunityDTO.getPhotoUrl());
        verify(communityRepository, times(1)).findById(communityId);
        verify(communityRepository, times(1)).save(any(Community.class));
    }

    @Test
    void deleteCommunitySuccessfully() {
        Long communityId = 1L;

        communityService.deleteCommunity(communityId);

        verify(communityRepository, times(1)).deleteById(communityId);
    }

    @Test
    void getAllCommunitiesByUser() {
        Long userId = 1L;

        Community community1 = new Community();
        community1.setId(1L);
        community1.setName("Community 1");

        Community community2 = new Community();
        community2.setId(2L);
        community2.setName("Community 2");

        List<Community> communities = new ArrayList<>();
        communities.add(community1);
        communities.add(community2);

        when(memberRepository.findCommunitiesByUserId(userId)).thenReturn(communities);

        List<CommunityDTO> communityDTOs = communityService.getAllCommunitiesByUser(userId);

        assertEquals(2, communityDTOs.size());
        verify(memberRepository, times(1)).findCommunitiesByUserId(userId);
    }

    @Test
    void addMemberToCommunitySuccessfully() {
        Long userId = 1L;
        Long communityId = 1L;
        String position = "Member";

        User user = new User();
        user.setId(userId);

        Community community = new Community();
        community.setId(communityId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(communityRepository.findById(communityId)).thenReturn(Optional.of(community));

        Member member = new Member();
        member.setUser(user);
        member.setCommunity(community);
        member.setPosition(position);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        MemberDTO memberDTO = communityService.addMemberToCommunity(userId, communityId, position);

        assertNotNull(memberDTO);
        assertEquals(userId, memberDTO.getUserDTO().getId());
        assertEquals(communityId, memberDTO.getCommunityDTO().getId());
        assertEquals(position, memberDTO.getPosition());
        verify(userRepository, times(1)).findById(userId);
        verify(communityRepository, times(1)).findById(communityId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void removeMemberFromCommunitySuccessfully() {
        Long memberId = 1L;

        communityService.removeMemberFromCommunity(memberId);

        verify(memberRepository, times(1)).deleteById(memberId);
    }

    @Test
    void getAllMembersOfCommunity() {
        Long communityId = 1L;

        Member member1 = new Member();
        member1.setId(1L);
        member1.setPosition("Member 1");

        Member member2 = new Member();
        member2.setId(2L);
        member2.setPosition("Member 2");

        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);

        when(memberRepository.findByCommunityId(communityId)).thenReturn(members);

        List<MemberDTO> memberDTOs = communityService.getAllMembersOfCommunity(communityId);

        assertEquals(2, memberDTOs.size());
        verify(memberRepository, times(1)).findByCommunityId(communityId);
    }
}