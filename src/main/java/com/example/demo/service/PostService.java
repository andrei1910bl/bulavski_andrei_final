package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Post;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PostDTO createPost(Long userId, String content) {
        log.info("Создание поста для пользователя с ID {}: {}", userId, content);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Пользователь с ID {} не найден", userId);
                    return new RuntimeException("User not found");
                });

        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Post savedPost = postRepository.save(post);
        log.info("Пост успешно создан с ID {}", savedPost.getId());
        return PostMapper.INSTANCE.toDTO(savedPost);
    }

    @Transactional
    public PostDTO updatePost(Long postId, String newContent) {
        log.info("Обновление поста с ID: {}", postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.error("Пост с ID {} не найден", postId);
                    return new RuntimeException("Post not found");
                });

        post.setContent(newContent);
        Post updatedPost = postRepository.save(post);
        log.info("Пост с ID {} успешно обновлен", postId);
        return PostMapper.INSTANCE.toDTO(updatedPost);
    }

    public List<PostDTO> getAllPostsByUser(Long userId) {
        log.info("Получение всех постов для пользователя с ID: {}", userId);
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(PostMapper.INSTANCE::toDTO)
                .toList();
    }

    @Transactional
    public void deletePost(Long postId) {
        log.info("Удаление поста с ID: {}", postId);
        postRepository.deleteById(postId);
        log.info("Пост с ID {} успешно удален", postId);
    }
}