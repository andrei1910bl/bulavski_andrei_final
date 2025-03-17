package com.example.demo.controller;

import com.example.demo.dto.CommunityDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @PostMapping
    public ResponseEntity<CommunityDTO> createCommunity(
            @RequestParam @NotEmpty(message = "Name should not be empty") String name,
            @RequestParam @NotEmpty(message = "Description should not be empty") String description,
            @RequestParam @NotEmpty(message = "Photo URL should not be empty") String photoUrl) {

        CommunityDTO communityDTO = communityService.createCommunity(name, description, photoUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(communityDTO);
    }

    @PutMapping("/{communityId}")
    public ResponseEntity<CommunityDTO> updateCommunity(
            @PathVariable Long communityId,
            @RequestParam @NotEmpty(message = "Name should not be empty") String name,
            @RequestParam @NotEmpty(message = "Description should not be empty") String description,
            @RequestParam @NotEmpty(message = "Photo URL should not be empty") String photoUrl) {

        CommunityDTO updatedCommunity = communityService.updateCommunity(communityId, name, description, photoUrl);
        return ResponseEntity.ok(updatedCommunity);
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable Long communityId) {
        communityService.deleteCommunity(communityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommunityDTO>> findCommunitiesByName(@RequestParam @NotEmpty(message = "Name should not be empty") String name) {
        List<CommunityDTO> communities = communityService.findCommunitiesByName(name);
        return ResponseEntity.ok(communities);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommunityDTO>> getAllCommunitiesByUser(@PathVariable Long userId) {
        List<CommunityDTO> communities = communityService.getAllCommunitiesByUser(userId);
        return ResponseEntity.ok(communities);
    }

    @PostMapping("/{communityId}/members")
    public ResponseEntity<MemberDTO> addMember(
            @PathVariable Long communityId,
            @RequestParam Long userId,
            @RequestParam @NotEmpty(message = "Position should not be empty") String position) {

        MemberDTO memberDTO = communityService.addMemberToCommunity(userId, communityId, position);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDTO);
    }
}