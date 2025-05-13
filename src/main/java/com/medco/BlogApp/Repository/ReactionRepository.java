package com.medco.BlogApp.Repository;

import com.medco.BlogApp.Entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByUserIdAndBlogPostId(Long userId, Long blogPostId);
    long countByBlogPostIdAndIsLikedTrue(Long blogPostId);
}
