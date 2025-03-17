package com.example.demo.service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.PublicationDTO;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PublicationRepository;
import com.example.demo.repository.entity.Member;
import com.example.demo.repository.entity.Publication;
import com.example.demo.service.mapper.MemberMapper;
import com.example.demo.service.mapper.PublicationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PublicationServiceTest {

    @InjectMocks
    private PublicationService publicationService;

    @Mock
    private PublicationRepository publicationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PublicationMapper publicationMapper;

    @Mock
    private MemberMapper memberMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPublicationSuccessfully() {
        Long memberId = 1L;
        String content = "This is a test publication.";

        Member member = new Member();
        member.setId(memberId);

        Publication publication = new Publication();
        publication.setId(1L);
        publication.setContent(content);
        publication.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        publication.setMember(member);

        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setContent(content);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(publicationRepository.save(any(Publication.class))).thenReturn(publication);
        when(publicationMapper.toDTO(publication)).thenReturn(publicationDTO);
        when(memberMapper.toDTO(member)).thenReturn(new MemberDTO());

        PublicationDTO createdPublication = publicationService.createPublication(memberId, content);

        assertNotNull(createdPublication);
        assertEquals(content, createdPublication.getContent());
        verify(publicationRepository, times(1)).save(any(Publication.class));
    }

    @Test
    void updatePublicationSuccessfully() {
        Long publicationId = 1L;
        String newContent = "Updated content.";

        Publication publication = new Publication();
        publication.setId(publicationId);
        publication.setContent("Old content");

        Member member = new Member();
        member.setId(2L);
        publication.setMember(member);

        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publicationId);
        publicationDTO.setContent(newContent);

        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publication));
        when(publicationRepository.save(any(Publication.class))).thenReturn(publication);
        when(publicationMapper.toDTO(publication)).thenReturn(publicationDTO);
        when(memberMapper.toDTO(member)).thenReturn(new MemberDTO());

        PublicationDTO updatedPublication = publicationService.updatePublication(publicationId, newContent);

        assertNotNull(updatedPublication);
        assertEquals(newContent, updatedPublication.getContent());
        verify(publicationRepository, times(1)).save(publication);
    }

    @Test
    void updatePublicationNotFound() {
        Long publicationId = 1L;
        String newContent = "Updated content.";

        when(publicationRepository.findById(publicationId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            publicationService.updatePublication(publicationId, newContent);
        });

        assertEquals("Publication not found", exception.getMessage());
    }

    @Test
    void getAllPublicationsByCommunitySuccessfully() {
        Long communityId = 1L;

        Publication publication = new Publication();
        publication.setId(1L);
        publication.setContent("Community publication");

        when(publicationRepository.findByCommunityId(communityId)).thenReturn(Collections.singletonList(publication));
        when(publicationMapper.toDTO(publication)).thenReturn(new PublicationDTO());

        List<PublicationDTO> publications = publicationService.getAllPublicationsByCommunity(communityId);

        assertEquals(1, publications.size());
        verify(publicationRepository, times(1)).findByCommunityId(communityId);
    }

    @Test
    void deletePublicationSuccessfully() {
        Long publicationId = 1L;

        publicationService.deletePublication(publicationId);

        verify(publicationRepository, times(1)).deleteById(publicationId);
    }

    @Test
    void searchPublicationsSuccessfully() {
        String content = "test";
        Long communityId = 1L;

        Publication publication = new Publication();
        publication.setId(1L);
        publication.setContent("This is a test publication");

        when(publicationRepository.findByContentAndCommunityId(content, communityId)).thenReturn(Collections.singletonList(publication));
        when(publicationMapper.toDTO(publication)).thenReturn(new PublicationDTO());

        List<PublicationDTO> results = publicationService.searchPublications(content, communityId);

        assertEquals(1, results.size());
        verify(publicationRepository, times(1)).findByContentAndCommunityId(content, communityId);
    }
}