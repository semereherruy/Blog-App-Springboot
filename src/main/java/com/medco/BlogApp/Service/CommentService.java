package com.medco.BlogApp.Service;

import com.medco.BlogApp.DTO.Request.CommentRequestDto;
import com.medco.BlogApp.DTO.Response.CommentResponseDto;
import com.medco.BlogApp.Entity.Comment;

import java.util.List;

public interface CommentService {
    CommentResponseDto addComment(CommentRequestDto dto);
    List<CommentResponseDto> getCommentsByPostId(Long postId);
}
