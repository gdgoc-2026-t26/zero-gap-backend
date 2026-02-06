package com.gdgoc.t26.zero_gap.emotion.service;

import com.gdgoc.t26.zero_gap.emotion.domain.Emotion;
import com.gdgoc.t26.zero_gap.emotion.dto.EmotionRequest;
import com.gdgoc.t26.zero_gap.emotion.dto.EmotionResponse;
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
public class EmotionService {

    private final EmotionRepository emotionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long registerEmotion(String email, EmotionRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        Emotion emotion = Emotion.builder()
                .user(user)
                .name(request.getName())
                .date(request.getDate())
                .description(request.getDescription())
                .build();

        return emotionRepository.save(emotion).getId();
    }

    public List<EmotionResponse> getEmotionsByDateRange(String email, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));

        return emotionRepository.findByUserAndDateBetween(user, startDate, endDate)
                .stream()
                .map(EmotionResponse::from)
                .collect(Collectors.toList());
    }
}
