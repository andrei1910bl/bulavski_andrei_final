package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.Post;
import com.example.demo.repository.entity.User;
import com.example.demo.service.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPostSuccessfully() {
        Long userId = 1L;
        String content = "This is a test post";

        User user = new User();
        user.setId(userId);

        Post post = new Post();
        post.setId(1L);
        post.setUser(user);
        post.setContent(content);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(content);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        PostDTO createdPost = postService.createPost(userId, content);

        assertNotNull(createdPost);
        assertEquals(content, createdPost.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void updatePostSuccessfully() {
        Long postId = 1L;
        String newContent = "Updated content";

        Post post = new Post();
        post.setId(postId);
        post.setContent("Old content");

        PostDTO postDTO = new PostDTO();
        postDTO.setId(postId);
        postDTO.setContent(newContent);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        PostDTO updatedPost = postService.updatePost(postId, newContent);

        assertNotNull(updatedPost);
        assertEquals(newContent, updatedPost.getContent());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void updatePostNotFound() {
        Long postId = 1L;
        String newContent = "Updated content";

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postService.updatePost(postId, newContent);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void getAllPostsByUserSuccessfully() {
        Long userId = 1L;
        Post post = new Post();
        post.setId(1L);
        post.setContent("User's post");

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());

        when(postRepository.findByUserId(userId)).thenReturn(Collections.singletonList(post));
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        List<PostDTO> posts = postService.getAllPostsByUser(userId);

        assertEquals(1, posts.size());
        assertEquals(post.getContent(), posts.get(0).getContent());
        verify(postRepository, times(1)).findByUserId(userId);
    }

    @Test
    void deletePostSuccessfully() {
        Long postId = 1L;

        postService.deletePost(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }
}