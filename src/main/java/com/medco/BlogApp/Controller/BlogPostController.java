package com.medco.BlogApp.Controller;

import com.medco.BlogApp.DTO.Request.BlogPostRequestDto;
import com.medco.BlogApp.DTO.Response.BlogPostResponseDto;
import com.medco.BlogApp.Service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blog/posts")
@RequiredArgsConstructor
@Validated
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<BlogPostResponseDto> createPost(@ModelAttribute @Validated BlogPostRequestDto blogPostRequestDto
    ) {
        BlogPostResponseDto created = blogPostService.createPost(blogPostRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/{postId}", consumes = "multipart/form-data")
    public ResponseEntity<BlogPostResponseDto> updatePost(@PathVariable Long postId,
                                                          @ModelAttribute @Validated BlogPostRequestDto blogPostRequestDto
    ) {
        BlogPostResponseDto updated = blogPostService.updatePost(postId, blogPostRequestDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<BlogPostResponseDto>> getAllPosts() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BlogPostResponseDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(blogPostService.getPostById(postId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BlogPostResponseDto>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(blogPostService.getPostsByUserId(userId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<BlogPostResponseDto>> getPostsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(blogPostService.getPostsByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BlogPostResponseDto>> searchPosts(@RequestParam String query) {
        return ResponseEntity.ok(blogPostService.searchPosts(query));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        blogPostService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
