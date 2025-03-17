package com.example.demo.repository;

import com.example.demo.repository.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByFirstUserId(Long userId);
    List<Chat> findAllBySecondUserId(Long userId);
}
