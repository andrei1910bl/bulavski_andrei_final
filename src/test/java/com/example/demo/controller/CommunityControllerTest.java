package com.example.demo.controller;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.CommunityService;
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

class CommunityControllerTest {

    @InjectMocks
    private CommunityController communityController;

    @Mock
    private CommunityService communityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCommunitySuccessfully() {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setName("Test Community");
        communityDTO.setDescription("This is a test community.");
        communityDTO.setPhotoUrl("http://example.com/photo.png");

        when(communityService.createCommunity(eq("Test Community"), eq("This is a test community."), eq("http://example.com/photo.png")))
                .thenReturn(communityDTO);

        ResponseEntity<CommunityDTO> response = communityController.createCommunity("Test Community", "This is a test community.", "http://example.com/photo.png");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(communityDTO, response.getBody());
        verify(communityService).createCommunity(eq("Test Community"), eq("This is a test community."), eq("http://example.com/photo.png"));
    }

    @Test
    void updateCommunitySuccessfully() {
        Long communityId = 1L;
        CommunityDTO updatedCommunity = new CommunityDTO();

        when(communityService.updateCommunity(eq(communityId), eq("Updated Name"), eq("Updated Description"), eq("http://example.com/newphoto.png")))
                .thenReturn(updatedCommunity);

        ResponseEntity<CommunityDTO> response = communityController.updateCommunity(communityId, "Updated Name", "Updated Description", "http://example.com/newphoto.png");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCommunity, response.getBody());
        verify(communityService).updateCommunity(eq(communityId), eq("Updated Name"), eq("Updated Description"), eq("http://example.com/newphoto.png"));
    }

    @Test
    void deleteCommunitySuccessfully() {
        Long communityId = 1L;

        ResponseEntity<Void> response = communityController.deleteCommunity(communityId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(communityService).deleteCommunity(eq(communityId));
    }

    @Test
    void findCommunitiesByNameSuccessfully() {
        String name = "Test";
        List<CommunityDTO> communities = new ArrayList<>();

        when(communityService.findCommunitiesByName(eq(name))).thenReturn(communities);

        ResponseEntity<List<CommunityDTO>> response = communityController.findCommunitiesByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(communities, response.getBody());
        verify(communityService).findCommunitiesByName(eq(name));
    }

    @Test
    void getAllCommunitiesByUserSuccessfully() {
        Long userId = 1L;
        List<CommunityDTO> communities = new ArrayList<>();

        when(communityService.getAllCommunitiesByUser(eq(userId))).thenReturn(communities);

        ResponseEntity<List<CommunityDTO>> response = communityController.getAllCommunitiesByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(communities, response.getBody());
        verify(communityService).getAllCommunitiesByUser(eq(userId));
    }

    @Test
    void addMemberSuccessfully() {
        Long communityId = 1L;
        Long userId = 2L;
        String position = "Member";
        MemberDTO memberDTO = new MemberDTO();

        when(communityService.addMemberToCommunity(eq(userId), eq(communityId), eq(position))).thenReturn(memberDTO);

        ResponseEntity<MemberDTO> response = communityController.addMember(communityId, userId, position);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(memberDTO, response.getBody());
        verify(communityService).addMemberToCommunity(eq(userId), eq(communityId), eq(position));
    }
}