package com.medco.BlogApp.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionResponseDto {
        private boolean liked;
        private long likeCount;
}
