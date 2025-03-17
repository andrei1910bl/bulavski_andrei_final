package com.example.demo.controller;

import com.example.demo.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost() {
        Long userId = 1L;
        String content = "Hello, World!";

        postController.createPost(userId, content);

        verify(postService, times(1)).createPost(userId, content);
    }

    @Test
    void updatePost() {
        Long postId = 1L;
        String newContent = "Updated content";

        postController.updatePost(postId, newContent);

        verify(postService, times(1)).updatePost(postId, newContent);
    }

    @Test
    void getAllPostsByUser() {
        Long userId = 1L;

        postController.getAllPostsByUser(userId);

        verify(postService, times(1)).getAllPostsByUser(userId);
    }

    @Test
    void deletePost() {
        Long postId = 1L;

        postController.deletePost(postId);

        verify(postService, times(1)).deletePost(postId);
    }
}