package com.example.demo.repository;

import com.example.demo.repository.entity.Group;
import com.example.demo.repository.entity.GroupUser;
import com.example.demo.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT gu.group FROM GroupUser gu WHERE gu.user.id = :userId")
    List<Group> findGroupsByUserId(Long userId);
}
