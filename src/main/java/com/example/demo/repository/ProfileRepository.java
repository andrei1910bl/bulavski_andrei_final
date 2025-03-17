package com.example.demo.repository;

import com.example.demo.repository.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByName(String name);
    Profile findByUserId(Long userId);
    List<Profile> findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name, String lastName);
}
