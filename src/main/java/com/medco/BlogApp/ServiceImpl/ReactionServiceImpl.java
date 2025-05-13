package com.medco.BlogApp.ServiceImpl;

import com.medco.BlogApp.Entity.BlogPost;
import com.medco.BlogApp.Entity.Reaction;
import com.medco.BlogApp.Entity.User;
import com.medco.BlogApp.Repository.BlogPostRepository;
import com.medco.BlogApp.Repository.ReactionRepository;
import com.medco.BlogApp.Repository.UserRepository;
import com.medco.BlogApp.Service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public Reaction isLikedPost(Long userId, Long postId, boolean isLiked) {
        Reaction reaction = reactionRepository.findByUserIdAndBlogPostId(userId, postId).orElse(null);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        BlogPost blogPost = blogPostRepository.findById(postId).orElseThrow(() -> new RuntimeException("Blog not found!"));

        if (reaction == null) {
            reaction = new Reaction();
            reaction.setUser(user);
            reaction.setBlogPost(blogPost);
            reaction.setLiked(true);
        } else {
            reaction.setLiked(!reaction.isLiked()); // toggle
        }

        return reactionRepository.save(reaction);
    }

    @Override
    public long countLikes(Long postId) {
        return reactionRepository.countByBlogPostIdAndIsLikedTrue(postId);
    }
}
