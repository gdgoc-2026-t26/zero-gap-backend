package com.gdgoc.t26.zero_gap.summarize.service;

import com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge;
import com.gdgoc.t26.zero_gap.challenge.repository.UserChallengeRepository;
import com.gdgoc.t26.zero_gap.challenge.service.ChallengeGenerationService;
import com.gdgoc.t26.zero_gap.emotion.domain.Emotion;
import com.gdgoc.t26.zero_gap.emotion.repository.EmotionRepository;
import com.gdgoc.t26.zero_gap.user.domain.User;
import com.gdgoc.t26.zero_gap.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SummarizeService {

    private final UserChallengeRepository userChallengeRepository;
    private final EmotionRepository emotionRepository;
    private final UserRepository userRepository;
    private final ChallengeGenerationService challengeGenerationService;

    public String generateSummary(String email, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        List<UserChallenge> missions = userChallengeRepository.findByUserIdAndDateBetween(user.getId(), startDate, endDate);
        List<Emotion> emotions = emotionRepository.findByUserAndDateBetween(user, startDate, endDate);

        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append("Missions:\n");
        for (UserChallenge m : missions) {
            dataBuilder.append(String.format("- %s (Date: %s, Accomplished: %s, Description: %s)\n",
                    m.getChallenge().getTitle(), m.getDate(), m.getAccomplished(), m.getDescription()));
        }
        
        dataBuilder.append("\nEmotions:\n");
        for (Emotion e : emotions) {
            dataBuilder.append(String.format("- Date: %s, Score: %d, Description: %s\n",
                    e.getDate(), e.getScore(), e.getDescription()));
        }

        return challengeGenerationService.generateSummary(dataBuilder.toString());
    }
}
