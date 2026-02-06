package com.gdgoc.t26.zero_gap.challenge.service;

import com.gdgoc.t26.zero_gap.challenge.domain.*;
import com.gdgoc.t26.zero_gap.challenge.repository.ChallengeRepository;
import com.gdgoc.t26.zero_gap.challenge.repository.UserChallengeRepository;
import com.gdgoc.t26.zero_gap.challenge.dto.*;
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

    public List<UserChallenge> getMissionsByDate(Long userId, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        return userChallengeRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    public List<String> getTodayRecommendations(Integer durationInSeconds) {
        return challengeGenerationService.generateRecommendations(durationInSeconds);
    }

    @Transactional
    public MissionCreateResponse createMission(Long userId, String name, java.time.LocalDate date) {
        // Find existing challenge by title or create a virtual one?
        Challenge challenge = challengeRepository.findByTitle(name)
                .stream().findFirst()
                .orElseGet(() -> {
                    Challenge newChallenge = Challenge.builder()
                            .title(name)
                            .description(name)
                            .durationCategory(DurationCategory.SHORT)
                            .aiGenerated(false)
                            .build();
                    return challengeRepository.save(newChallenge);
                });

        UserChallenge userChallenge = UserChallenge.builder()
                .userId(userId)
                .challenge(challenge)
                .status(ChallengeStatus.STARTED)
                .date(date)
                .startTime(java.time.LocalDateTime.now())
                .build();

        userChallengeRepository.save(userChallenge);
        
        String cheerMessage = challengeGenerationService.generateCheerMessage(name);
        return MissionCreateResponse.of(userChallenge.getId(), cheerMessage);
    }

    @Transactional
    public MissionPatchResponse patchMission(Long userId, UUID missionId, Boolean accomplished, String description) {
        UserChallenge userChallenge = userChallengeRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        if (!userChallenge.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (accomplished != null) {
            userChallenge.setAccomplished(accomplished);
            if (accomplished) {
                userChallenge.setStatus(ChallengeStatus.COMPLETED);
                userChallenge.setCompletionTime(java.time.LocalDateTime.now());
            }
        }
        if (description != null) {
            userChallenge.setDescription(description);
        }

        userChallengeRepository.save(userChallenge);

        String title = userChallenge.getChallenge().getTitle();
        String cheerMessage = challengeGenerationService.generateCompletionMessage(title, description);
        return MissionPatchResponse.from(cheerMessage);
    }


    @Transactional
    public UserChallenge startChallenge(Long userId, UUID challengeId, String description) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        UserChallenge userChallenge = UserChallenge.builder()
                .userId(userId)
                .challenge(challenge)
                .status(ChallengeStatus.STARTED)
                .description(description)
                .date(java.time.LocalDate.now())
                .startTime(java.time.LocalDateTime.now())
                .build();

        return userChallengeRepository.save(userChallenge);
    }

    @Transactional
    public UserChallenge completeChallenge(Long userId, UUID challengeId) {
        UserChallenge userChallenge = userChallengeRepository.findByUserIdAndStatus(userId, ChallengeStatus.STARTED)
                .stream()
                .filter(uc -> uc.getChallenge().getId().equals(challengeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No started challenge found for this user"));

        userChallenge.setStatus(ChallengeStatus.COMPLETED);
        userChallenge.setAccomplished(true);
        userChallenge.setCompletionTime(java.time.LocalDateTime.now());

        return userChallengeRepository.save(userChallenge);
    }
}
