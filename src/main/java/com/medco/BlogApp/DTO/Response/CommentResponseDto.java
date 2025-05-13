package com.medco.BlogApp.DTO.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long   id;
    private Long   postId;
    private Long   userId;
    private String content;
    private LocalDateTime createdAt;
}
