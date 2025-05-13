package com.medco.BlogApp.ServiceImpl;

import com.medco.BlogApp.DTO.Request.CommentRequestDto;
import com.medco.BlogApp.DTO.Response.CommentResponseDto;
import com.medco.BlogApp.Entity.BlogPost;
import com.medco.BlogApp.Entity.Comment;
import com.medco.BlogApp.Entity.User;
import com.medco.BlogApp.Repository.BlogPostRepository;
import com.medco.BlogApp.Repository.CommentRepository;
import com.medco.BlogApp.Repository.UserRepository;
import com.medco.BlogApp.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository   commentRepository;

    @Autowired
    private BlogPostRepository  blogPostRepository;

    @Autowired
    private UserRepository      userRepository;

    @Override
    @Transactional
    public CommentResponseDto addComment(CommentRequestDto dto) {
        // validate DTO
        if (dto.getPostId() == null) throw new IllegalArgumentException("postId is required");
        if (dto.getUserId() == null) throw new IllegalArgumentException("userId is required");
        if (dto.getContent() == null || dto.getContent().trim().isEmpty())
            throw new IllegalArgumentException("content cannot be empty");

        // fetch entities
        BlogPost post = blogPostRepository.findById(dto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + dto.getPostId()));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.getUserId()));

        // build and save the Comment entity
        Comment comment = new Comment();
        comment.setBlogPost(post);
        comment.setUser(user);
        comment.setContent(dto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);

        // map to DTO and return
        return new CommentResponseDto(
                saved.getId(),
                saved.getBlogPost().getId(),
                saved.getUser().getId(),
                saved.getContent(),
                saved.getCreatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        return commentRepository.findByBlogPostId(postId).stream()
                .map(c -> new CommentResponseDto(
                        c.getId(),
                        c.getBlogPost().getId(),
                        c.getUser().getId(),
                        c.getContent(),
                        c.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
