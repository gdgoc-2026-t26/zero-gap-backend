package com.gdgoc.t26.zero_gap.challenge.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ChallengeTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Challenge with null title should fail validation")
    void challengeWithNullTitleShouldFailValidation() {
        Challenge challenge = Challenge.builder()
                .title(null)
                .description("Description")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation ->
                violation.getPropertyPath().toString().equals("title") &&
                violation.getMessage().equals("Title cannot be blank")
        );
    }

    @Test
    @DisplayName("Challenge with empty title should fail validation")
    void challengeWithEmptyTitleShouldFailValidation() {
        Challenge challenge = Challenge.builder()
                .title("")
                .description("Description")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("Challenge with null description should pass validation")
    void challengeWithNullDescriptionShouldPassValidation() {
        Challenge challenge = Challenge.builder()
                .title("Title")
                .description(null)
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Challenge with empty description should pass validation")
    void challengeWithEmptyDescriptionShouldPassValidation() {
        Challenge challenge = Challenge.builder()
                .title("Title")
                .description("")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Challenge with null duration category should fail validation")
    void challengeWithNullDurationCategoryShouldFailValidation() {
        Challenge challenge = Challenge.builder()
                .title("Title")
                .description("Description")
                .durationCategory(null)
                .aiGenerated(false)
                .build();
        Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("Challenge with null AI Generated flag should fail validation")
    void challengeWithNullAiGeneratedFlagShouldFailValidation() {
        Challenge challenge = Challenge.builder()
                .title("Title")
                .description("Description")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(null)
                .build();
        Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("Challenge with valid attributes should pass validation")
    void challengeWithValidAttributesShouldPassValidation() {
        Challenge challenge = Challenge.builder()
                .title("Valid Title")
                .description("Valid Description")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(true)
                .build();
        Set<ConstraintViolation<Challenge>> violations = validator.validate(challenge);
        assertThat(violations).isEmpty();
    }
}
