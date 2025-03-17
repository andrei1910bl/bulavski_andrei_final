package com.example.demo.controller;

import com.example.demo.dto.PublicationDTO;
import com.example.demo.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping
    public PublicationDTO createPublication(@RequestParam Long memberId,
                                            @RequestParam String content) {
        return publicationService.createPublication(memberId, content);
    }

    @PutMapping("/{publicationId}")
    public PublicationDTO updatePublication(@PathVariable Long publicationId,
                                            @RequestParam String content) {
        return publicationService.updatePublication(publicationId, content);
    }

    @GetMapping("/community/{communityId}")
    public List<PublicationDTO> getAllPublicationsByCommunity(@PathVariable Long communityId) {
        return publicationService.getAllPublicationsByCommunity(communityId);
    }

    @DeleteMapping("/{publicationId}")
    public void deletePublication(@PathVariable Long publicationId) {
        publicationService.deletePublication(publicationId);
    }

    @GetMapping("/search")
    public List<PublicationDTO> searchPublications(@RequestParam String content, @RequestParam Long communityId) {
        return publicationService.searchPublications(content, communityId);
    }
}