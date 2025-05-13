package com.medco.BlogApp.Service;

import com.medco.BlogApp.Entity.Reaction;

public interface ReactionService {
    Reaction isLikedPost(Long userId, Long postId, boolean isLiked);
    long countLikes(Long postId);

}
