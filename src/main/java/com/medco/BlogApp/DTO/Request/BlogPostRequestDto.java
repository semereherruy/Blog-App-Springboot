package com.medco.BlogApp.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostRequestDto {
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    private String title;
    private String content;
    private MultipartFile image;
   // private String imagePath;
    private String category;
    private List<String> tags;
}
