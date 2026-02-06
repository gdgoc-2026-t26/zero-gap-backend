package com.gdgoc.t26.zero_gap.challenge.service;

import com.gdgoc.t26.zero_gap.challenge.domain.*;
import com.gdgoc.t26.zero_gap.challenge.repository.ChallengeRepository;
import com.gdgoc.t26.zero_gap.challenge.repository.UserChallengeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private UserChallengeRepository userChallengeRepository;

    @Mock
    private ChallengeGenerationService challengeGenerationService;

    @InjectMocks
    private ChallengeService challengeService;

    @Test
    @DisplayName("Should return recommended challenges")
    void shouldReturnRecommendedChallenges() {
        // Given
        DurationCategory duration = DurationCategory.SHORT;
        Challenge challenge = Challenge.builder().title("Test").build();
        when(challengeRepository.findByDurationCategory(duration)).thenReturn(List.of(challenge));

        // When
        List<Challenge> challenges = challengeService.getRecommendedChallenges(duration);

        // Then
        assertThat(challenges).hasSize(1);
        verify(challengeRepository).findByDurationCategory(duration);
    }

    @Test
    @DisplayName("Should start a challenge")
    void shouldStartChallenge() {
        // Given
        UUID userId = UUID.randomUUID();
        UUID challengeId = UUID.randomUUID();
        Challenge challenge = Challenge.builder().id(challengeId).build();
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(userChallengeRepository.save(any(UserChallenge.class))).thenAnswer(i -> i.getArgument(0));

        // When
        UserChallenge userChallenge = challengeService.startChallenge(userId, challengeId);

        // Then
        assertThat(userChallenge.getStatus()).isEqualTo(ChallengeStatus.STARTED);
        assertThat(userChallenge.getStartTime()).isNotNull();
        verify(userChallengeRepository).save(any(UserChallenge.class));
    }

    @Test
    @DisplayName("Should complete a challenge")
    void shouldCompleteChallenge() {
        // Given
        UUID userId = UUID.randomUUID();
        UUID challengeId = UUID.randomUUID();
        Challenge challenge = Challenge.builder().id(challengeId).build();
        UserChallenge existingUserChallenge = UserChallenge.builder()
                .userId(userId)
                .challenge(challenge)
                .status(ChallengeStatus.STARTED)
                .build();
        
        when(userChallengeRepository.findByUserIdAndStatus(userId, ChallengeStatus.STARTED))
                .thenReturn(List.of(existingUserChallenge));
        when(userChallengeRepository.save(any(UserChallenge.class))).thenAnswer(i -> i.getArgument(0));

        // When
        UserChallenge completedChallenge = challengeService.completeChallenge(userId, challengeId);

        // Then
        assertThat(completedChallenge.getStatus()).isEqualTo(ChallengeStatus.COMPLETED);
        assertThat(completedChallenge.getCompletionTime()).isNotNull();
        verify(userChallengeRepository).save(any(UserChallenge.class));
    }
}
