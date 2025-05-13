package com.medco.BlogApp.ServiceImpl;

import com.medco.BlogApp.DTO.Request.BlogPostRequestDto;
import com.medco.BlogApp.DTO.Response.BlogPostResponseDto;
import com.medco.BlogApp.Entity.BlogPost;
import com.medco.BlogApp.Entity.Tag;
import com.medco.BlogApp.Entity.User;
import com.medco.BlogApp.Repository.*;
import com.medco.BlogApp.Service.BlogPostService;
import com.medco.BlogApp.Service.FileStorageService;
import com.medco.BlogApp.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepository    blogPostRepository;
    private final UserRepository        userRepository;
    private final TagRepository         tagRepository;
    private final ReactionRepository    reactionRepository;
    private final FileStorageService    fileStorageService;

    @Override
    public BlogPostResponseDto createPost(BlogPostRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Handle file upload if present
        String imgPath = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            imgPath = fileStorageService.savePostImage(dto.getImage());
        }

        BlogPost post = new BlogPost();
        post.setUser(user);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImagePath(imgPath);
        post.setCategory(dto.getCategory());
        post.setCreatedAt(LocalDateTime.now());

        // Tags
        List<Tag> tags = dto.getTags().stream()
                .map(String::toLowerCase)
                .map(tagKey -> tagRepository.findByKeyword(tagKey)
                        .orElseGet(() -> tagRepository.save(new Tag(null, tagKey, new ArrayList<>())))
                )
                .collect(Collectors.toList());
        post.setTags(tags);
        tags.forEach(t -> t.getBlogPosts().add(post));

        BlogPost saved = blogPostRepository.save(post);

        return mapToDto(saved);
    }

    @Override
    public BlogPostResponseDto updatePost(Long postId, BlogPostRequestDto dto) {
        BlogPost existing = blogPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));

        //image replace
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            existing.setImagePath(fileStorageService.savePostImage(dto.getImage()));
        }

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());
        existing.setCategory(dto.getCategory());
        existing.setUpdatedAt(LocalDateTime.now());

        BlogPost updated = blogPostRepository.save(existing);
        return mapToDto(updated);
    }

    @Override
    public void deletePost(Long postId) {
        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));
        blogPostRepository.delete(post);
    }

    @Override
    public BlogPostResponseDto getPostById(Long postId) {
        return mapToDto(blogPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId)));
    }

    @Override
    public List<BlogPostResponseDto> getAllPosts() {


        return blogPostRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<BlogPostResponseDto> getPostsByUserId(Long userId) {
        return blogPostRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogPostResponseDto> getPostsByCategory(String category) {
        return blogPostRepository.findByCategory(category).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogPostResponseDto> searchPosts(String query) {
        return blogPostRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query)
                .stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // --- helpers ---

    private BlogPostResponseDto mapToDto(BlogPost bp) {
        var tags = bp.getTags().stream()
                .map(Tag::getKeyword)
                .collect(Collectors.toList());
        long likes = reactionRepository.countByBlogPostIdAndIsLikedTrue(bp.getId());

        String imageUrl = null;
        if (bp.getImagePath() != null) {
            String filename = Paths.get(bp.getImagePath()).getFileName().toString();

            //manually
            //imageUrl = "http://192.168.100.222:8900/post-images/" + filename;

            //Dynamically
            imageUrl = ServletUriComponentsBuilder
             .fromCurrentContextPath()
             .path("/post-images/")
             .path(filename)
             .toUriString();
        }


        return new BlogPostResponseDto(
                bp.getId(),
                bp.getTitle(),
                bp.getContent(),
                imageUrl,
                bp.getCategory(),
                tags,
                bp.getCreatedAt(),
                bp.getUpdatedAt(),
                likes
        );
    }
}
