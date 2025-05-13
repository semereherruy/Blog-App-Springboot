package com.medco.BlogApp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;


    @ManyToMany(mappedBy = "tags")
    private List<BlogPost> blogPosts = new ArrayList<>();
}
