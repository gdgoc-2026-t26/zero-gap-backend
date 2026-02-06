package com.gdgoc.t26.zero_gap.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgoc.t26.zero_gap.challenge.domain.Challenge;
import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChallengeGenerationService {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    public Challenge generateChallenge(DurationCategory duration) {
        String prompt = String.format("Generate a brief challenge title for a person experiencing burnout with duration %s. " +
                "Return the response in JSON format with only a 'title' field.", duration);
        
        String response = chatModel.call(prompt);
        
        try {
            Map<String, String> responseMap = objectMapper.readValue(response, Map.class);
            return Challenge.builder()
                    .title(responseMap.get("title"))
                    .description("") // User will provide description later
                    .durationCategory(duration)
                    .aiGenerated(true)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }
}
