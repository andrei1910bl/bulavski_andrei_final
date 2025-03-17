package com.example.demo.controller;

import com.example.demo.service.PublicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PublicationControllerTest {

    @InjectMocks
    private PublicationController publicationController;

    @Mock
    private PublicationService publicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPublicationSuccessfully() {
        Long memberId = 1L;
        String content = "New publication";

        publicationController.createPublication(memberId, content);

        verify(publicationService).createPublication(eq(memberId), eq(content));
    }

    @Test
    void updatePublicationSuccessfully() {
        Long publicationId = 1L;
        String newContent = "Updated publication";

        publicationController.updatePublication(publicationId, newContent);

        verify(publicationService).updatePublication(eq(publicationId), eq(newContent));
    }

    @Test
    void getAllPublicationsByCommunitySuccessfully() {
        Long communityId = 1L;

        publicationController.getAllPublicationsByCommunity(communityId);

        verify(publicationService).getAllPublicationsByCommunity(eq(communityId));
    }

    @Test
    void deletePublicationSuccessfully() {
        Long publicationId = 1L;

        publicationController.deletePublication(publicationId);

        verify(publicationService).deletePublication(eq(publicationId));
    }

    @Test
    void searchPublicationsSuccessfully() {
        String content = "New";
        Long communityId = 1L;

        publicationController.searchPublications(content, communityId);

        verify(publicationService).searchPublications(eq(content), eq(communityId));
    }
}