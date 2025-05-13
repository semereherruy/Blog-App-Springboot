package com.medco.BlogApp.Controller;

import com.medco.BlogApp.DTO.Request.CommentRequestDto;
import com.medco.BlogApp.Entity.Comment;
import com.medco.BlogApp.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.medco.BlogApp.DTO.Response.CommentResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/blog/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentResponseDto> addComment(
            @Valid @RequestBody CommentRequestDto dto
    ) {
        CommentResponseDto saved = commentService.addComment(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(
            @PathVariable Long postId
    ) {
        List<CommentResponseDto> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
