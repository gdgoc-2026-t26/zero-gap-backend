package com.gdgoc.t26.zero_gap.challenge.repository;

import com.gdgoc.t26.zero_gap.challenge.domain.Challenge;
import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import com.gdgoc.t26.zero_gap.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaConfig.class)
class ChallengeRepositoryTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Test
    @DisplayName("Should save and find a challenge by ID")
    void shouldSaveAndFindChallengeById() {
        Challenge challenge = Challenge.builder()
                .title("Test Challenge")
                .description("Test Description")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();

        Challenge savedChallenge = challengeRepository.save(challenge);
        Optional<Challenge> foundChallenge = challengeRepository.findById(savedChallenge.getId());

        assertThat(foundChallenge).isPresent();
        assertThat(foundChallenge.get().getTitle()).isEqualTo("Test Challenge");
    }

    @Test
    @DisplayName("Should find challenges by duration category")
    void shouldFindChallengesByDurationCategory() {
        Challenge challenge1 = Challenge.builder()
                .title("Short Challenge")
                .description("Desc")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        Challenge challenge2 = Challenge.builder()
                .title("Medium Challenge")
                .description("Desc")
                .durationCategory(DurationCategory.MEDIUM)
                .aiGenerated(false)
                .build();

        challengeRepository.save(challenge1);
        challengeRepository.save(challenge2);

        List<Challenge> shortChallenges = challengeRepository.findByDurationCategory(DurationCategory.SHORT);

        assertThat(shortChallenges).hasSize(1);
        assertThat(shortChallenges.get(0).getTitle()).isEqualTo("Short Challenge");
    }
}
