package com.gdgoc.t26.zero_gap.challenge.repository;

import com.gdgoc.t26.zero_gap.challenge.domain.*;
import com.gdgoc.t26.zero_gap.config.JpaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaConfig.class)
class UserChallengeRepositoryTest {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    private Challenge challenge;
    private UUID userId;

    @BeforeEach
    void setUp() {
        challenge = Challenge.builder()
                .title("Test Challenge")
                .description("Desc")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        challengeRepository.save(challenge);
        userId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Should save and find user challenge by user ID")
    void shouldSaveAndFindUserChallengeByUserId() {
        UserChallenge userChallenge = UserChallenge.builder()
                .userId(userId)
                .challenge(challenge)
                .status(ChallengeStatus.PENDING)
                .build();

        userChallengeRepository.save(userChallenge);

        List<UserChallenge> userChallenges = userChallengeRepository.findByUserId(userId);

        assertThat(userChallenges).hasSize(1);
        assertThat(userChallenges.get(0).getChallenge().getTitle()).isEqualTo("Test Challenge");
    }

    @Test
    @DisplayName("Should find user challenges by user ID and status")
    void shouldFindUserChallengesByUserIdAndStatus() {
        UserChallenge userChallenge1 = UserChallenge.builder()
                .userId(userId)
                .challenge(challenge)
                .status(ChallengeStatus.STARTED)
                .build();
        UserChallenge userChallenge2 = UserChallenge.builder()
                .userId(userId)
                .challenge(challenge)
                .status(ChallengeStatus.COMPLETED)
                .build();

        userChallengeRepository.save(userChallenge1);
        userChallengeRepository.save(userChallenge2);

        List<UserChallenge> startedChallenges = userChallengeRepository.findByUserIdAndStatus(userId, ChallengeStatus.STARTED);

        assertThat(startedChallenges).hasSize(1);
        assertThat(startedChallenges.get(0).getStatus()).isEqualTo(ChallengeStatus.STARTED);
    }
}
