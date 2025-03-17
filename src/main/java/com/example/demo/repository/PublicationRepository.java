package com.example.demo.repository;

import com.example.demo.repository.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT p FROM Publication p WHERE p.member.community.id = :communityId")
    List<Publication> findByCommunityId(Long communityId);

    @Query("SELECT p FROM Publication p WHERE p.content LIKE %:content% AND p.member.community.id = :communityId")
    List<Publication> findByContentAndCommunityId(@Param("content") String content, @Param("communityId") Long communityId);
}
