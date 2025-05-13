package com.medco.BlogApp.Controller;

import com.medco.BlogApp.DTO.Response.ReactionResponseDto;
import com.medco.BlogApp.Entity.Reaction;
import com.medco.BlogApp.Service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog/reactions")
public class ReactionController {
    @Autowired
    private ReactionService reactionService;

    @PostMapping("/toggle")
    public ResponseEntity<ReactionResponseDto> toggleLike(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {
        // this still returns the saved Reaction
        Reaction reaction = reactionService.isLikedPost(userId, postId, true);

        // now build a tiny DTO instead of returning the whole entity
        boolean nowLiked = reaction.isLiked();
        long totalLikes = reactionService.countLikes(postId);
        ReactionResponseDto dto = new ReactionResponseDto(nowLiked, totalLikes);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
        long likeCount = reactionService.countLikes(postId);
        return ResponseEntity.ok(likeCount);
    }

}
