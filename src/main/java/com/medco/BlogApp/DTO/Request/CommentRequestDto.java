package com.medco.BlogApp.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    @NotNull(message = "postId is required")
    private Long postId;

    @NotNull(message = "userId is required")
    private Long userId;

    @NotBlank(message = "content cannot be blank")
    private String content;
}
