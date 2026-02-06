package com.gdgoc.t26.zero_gap.challenge.dto;

import com.gdgoc.t26.zero_gap.challenge.domain.ChallengeStatus;
import com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserChallengeResponse {
    private UUID id;
    private UUID userId;
    private UUID challengeId;
    private ChallengeStatus status;
    private LocalDateTime startTime;
    private LocalDateTime completionTime;

    public static UserChallengeResponse from(UserChallenge userChallenge) {
        return UserChallengeResponse.builder()
                .id(userChallenge.getId())
                .userId(userChallenge.getUserId())
                .challengeId(userChallenge.getChallenge().getId())
                .status(userChallenge.getStatus())
                .startTime(userChallenge.getStartTime())
                .completionTime(userChallenge.getCompletionTime())
                .build();
    }
}
