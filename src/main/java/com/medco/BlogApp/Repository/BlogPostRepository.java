package com.medco.BlogApp.Repository;

import com.medco.BlogApp.Entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByUserId(Long userId);
    List<BlogPost> findByCategory(String category);
    List<BlogPost> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}
