package com.example.demo.repository;

import com.example.demo.repository.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUserIdAndFriendId(Long userId, Long friendId);
    List<Friend> findAllByUserId(Long userId);
    List<Friend> findAllByFriendId(Long friendId);

}
