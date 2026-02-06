package com.gdgoc.t26.zero_gap.challenge.service;

import com.gdgoc.t26.zero_gap.challenge.domain.*;
import com.gdgoc.t26.zero_gap.challenge.repository.ChallengeRepository;
import com.gdgoc.t26.zero_gap.challenge.repository.UserChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserChallengeRepository userChallengeRepository;
    private final ChallengeGenerationService challengeGenerationService;

    public List<Challenge> getRecommendedChallenges(DurationCategory duration) {
        List<Challenge> challenges = challengeRepository.findByDurationCategory(duration);
        if (challenges.isEmpty()) {
            Challenge newChallenge = challengeGenerationService.generateChallenge(duration);
            challengeRepository.save(newChallenge);
            return List.of(newChallenge);
        }
        return challenges;
    }

    @Transactional
    public UserChallenge startChallenge(UUID userId, UUID challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        UserChallenge userChallenge = UserChallenge.builder()
                .userId(userId)
                .challenge(challenge)
                .status(ChallengeStatus.STARTED)
                .startTime(LocalDateTime.now())
                .build();

        return userChallengeRepository.save(userChallenge);
    }

    @Transactional
    public UserChallenge completeChallenge(UUID userId, UUID challengeId) {
        // Simple logic: find any started challenge for this user and this challenge
        // In a real app, you might want to be more specific or handle multiple active challenges
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndStatus(userId, ChallengeStatus.STARTED)
                .stream()
                .filter(uc -> uc.getChallenge().getId().equals(challengeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No started challenge found for this user"));

        userChallenge.setStatus(ChallengeStatus.COMPLETED);
        userChallenge.setCompletionTime(LocalDateTime.now());

        return userChallengeRepository.save(userChallenge);
    }
}
