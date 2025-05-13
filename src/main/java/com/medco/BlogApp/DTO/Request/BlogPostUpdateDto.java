package com.medco.BlogApp.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostUpdateDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private String category;
    private List<String> tags;
}
