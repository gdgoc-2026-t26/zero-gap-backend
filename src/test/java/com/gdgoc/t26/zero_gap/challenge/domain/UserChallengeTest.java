package com.gdgoc.t26.zero_gap.challenge.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserChallengeTest {

    private Validator validator;
    private Challenge challenge;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        challenge = Challenge.builder()
                .title("Test Challenge")
                .description("Test Description")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
    }

    @Test
    @DisplayName("UserChallenge with null user ID should fail validation")
    void userChallengeWithNullUserIdShouldFailValidation() {
        UserChallenge userChallenge = UserChallenge.builder()
                .userId(null)
                .challenge(challenge)
                .status(ChallengeStatus.PENDING)
                .build();
        Set<ConstraintViolation<UserChallenge>> violations = validator.validate(userChallenge);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("UserChallenge with null challenge should fail validation")
    void userChallengeWithNullChallengeShouldFailValidation() {
        UserChallenge userChallenge = UserChallenge.builder()
                .userId(UUID.randomUUID())
                .challenge(null)
                .status(ChallengeStatus.PENDING)
                .build();
        Set<ConstraintViolation<UserChallenge>> violations = validator.validate(userChallenge);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("UserChallenge with null status should fail validation")
    void userChallengeWithNullStatusShouldFailValidation() {
        UserChallenge userChallenge = UserChallenge.builder()
                .userId(UUID.randomUUID())
                .challenge(challenge)
                .status(null)
                .build();
        Set<ConstraintViolation<UserChallenge>> violations = validator.validate(userChallenge);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("UserChallenge with valid attributes should pass validation")
    void userChallengeWithValidAttributesShouldPassValidation() {
        UserChallenge userChallenge = UserChallenge.builder()
                .userId(UUID.randomUUID())
                .challenge(challenge)
                .status(ChallengeStatus.PENDING)
                .build();
        Set<ConstraintViolation<UserChallenge>> violations = validator.validate(userChallenge);
        assertThat(violations).isEmpty();
    }
}
