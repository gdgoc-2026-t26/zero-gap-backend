package com.gdgoc.t26.zero_gap.challenge.dto;

import com.gdgoc.t26.zero_gap.challenge.domain.Challenge;
import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ChallengeResponse {
    private UUID id;
    private String title;
    private String description;
    private DurationCategory durationCategory;
    private Boolean aiGenerated;

    public static ChallengeResponse from(Challenge challenge) {
        return ChallengeResponse.builder()
                .id(challenge.getId())
                .title(challenge.getTitle())
                .description(challenge.getDescription())
                .durationCategory(challenge.getDurationCategory())
                .aiGenerated(challenge.getAiGenerated())
                .build();
    }
}
