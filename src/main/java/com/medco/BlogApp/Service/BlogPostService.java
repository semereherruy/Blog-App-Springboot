package com.medco.BlogApp.Service;

import com.medco.BlogApp.DTO.Request.BlogPostRequestDto;
import com.medco.BlogApp.DTO.Response.BlogPostResponseDto;
import com.medco.BlogApp.Entity.BlogPost;

import java.util.List;

public interface BlogPostService {
    BlogPostResponseDto createPost(BlogPostRequestDto dto);
    BlogPostResponseDto updatePost(Long postId, BlogPostRequestDto dto);
    List<BlogPostResponseDto> getAllPosts();
    BlogPostResponseDto getPostById(Long postId);
    List<BlogPostResponseDto> getPostsByUserId(Long userId);
    List<BlogPostResponseDto> getPostsByCategory(String category);
    List<BlogPostResponseDto> searchPosts(String query);
    void deletePost(Long postId);
}
