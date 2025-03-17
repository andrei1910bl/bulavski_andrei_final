package com.example.demo.controller;

import com.example.demo.dto.PostDTO;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public PostDTO createPost(@RequestParam Long userId, @RequestParam String content) {
        return postService.createPost(userId, content);
    }

    @PutMapping("/{postId}")
    public PostDTO updatePost(@PathVariable Long postId, @RequestParam String newContent) {
        return postService.updatePost(postId, newContent);
    }

    @GetMapping("/user/{userId}")
    public List<PostDTO> getAllPostsByUser(@PathVariable Long userId) {
        return postService.getAllPostsByUser(userId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}