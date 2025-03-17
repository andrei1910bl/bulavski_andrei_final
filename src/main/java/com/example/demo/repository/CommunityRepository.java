package com.example.demo.repository;

import com.example.demo.repository.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    List<Community> findByNameContaining(String name);

    @Query("SELECT c FROM Community c JOIN c.members m WHERE m.user.id = :userId")
    List<Community> findAllByUserId(Long userId);
}
