package com.example.demo.repository;

import com.example.demo.repository.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByContent(String content);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    List<Post> findByUserId(Long userId);
}
