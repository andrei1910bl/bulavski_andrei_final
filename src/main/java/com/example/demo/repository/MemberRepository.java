package com.example.demo.repository;

import com.example.demo.repository.entity.Community;
import com.example.demo.repository.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPosition(String position);

    @Query("SELECT m FROM Member m WHERE m.community.id = :communityId")
    List<Member> findByCommunityId(Long communityId);

    @Query("SELECT m.community FROM Member m WHERE m.user.id = :userId")
    List<Community> findCommunitiesByUserId(Long userId);
}
