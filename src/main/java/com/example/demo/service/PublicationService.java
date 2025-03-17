package com.example.demo.service;

import com.example.demo.dto.PublicationDTO;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PublicationRepository;
import com.example.demo.repository.entity.Publication;
import com.example.demo.repository.entity.Member;
import com.example.demo.service.mapper.MemberMapper;
import com.example.demo.service.mapper.PublicationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public PublicationDTO createPublication(Long memberId, String content) {
        log.info("Создание публикации для участника с ID {}: {}", memberId, content);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("Участник с ID {} не найден", memberId);
                    return new RuntimeException("Member not found");
                });

        Publication publication = new Publication();
        publication.setContent(content);
        publication.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        publication.setMember(member);

        Publication savedPublication = publicationRepository.save(publication);
        PublicationDTO publicationDTO = PublicationMapper.INSTANCE.toDTO(savedPublication);
        publicationDTO.setMemberDTO(MemberMapper.INSTANCE.toDTO(member));

        log.info("Публикация успешно создана с ID {}", savedPublication.getId());
        return publicationDTO;
    }

    @Transactional
    public PublicationDTO updatePublication(Long publicationId, String content) {
        log.info("Обновление публикации с ID: {}", publicationId);
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> {
                    log.error("Публикация с ID {} не найдена", publicationId);
                    return new RuntimeException("Publication not found");
                });

        publication.setContent(content);
        Publication updatedPublication = publicationRepository.save(publication);
        PublicationDTO publicationDTO = PublicationMapper.INSTANCE.toDTO(updatedPublication);
        publicationDTO.setMemberDTO(MemberMapper.INSTANCE.toDTO(publication.getMember()));

        log.info("Публикация с ID {} успешно обновлена", publicationId);
        return publicationDTO;
    }

    public List<PublicationDTO> getAllPublicationsByCommunity(Long communityId) {
        log.info("Получение всех публикаций для сообщества с ID: {}", communityId);
        List<Publication> publications = publicationRepository.findByCommunityId(communityId);
        return publications.stream()
                .map(PublicationMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePublication(Long publicationId) {
        log.info("Удаление публикации с ID: {}", publicationId);
        publicationRepository.deleteById(publicationId);
        log.info("Публикация с ID {} успешно удалена", publicationId);
    }

    public List<PublicationDTO> searchPublications(String content, Long communityId) {
        log.info("Поиск публикаций с содержимым '{}' для сообщества с ID: {}", content, communityId);
        List<Publication> publications = publicationRepository.findByContentAndCommunityId(content, communityId);
        return publications.stream()
                .map(PublicationMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}