package com.gdgoc.t26.zero_gap.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeGenerationService {

    private final ChatModel chatModel;

    public List<String> generateRecommendations(Integer durationInSeconds) {
        String durationText = durationInSeconds != null ? durationInSeconds + " seconds" : "a short period";
        String prompt = String.format("Generate 5 brief challenge titles for a person experiencing burnout with available time of %s. " +
                "Output each title on a new line, only in Korean, without any other text, numbering, or formatting.", durationText);
        
        String response = chatModel.call(prompt);
        System.out.println("AI Response (Recommendations):\n" + response);
        
        return Arrays.stream(response.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .limit(5)
                .collect(Collectors.toList());
    }

    public String generateCheerMessage(String missionName) {
        String prompt = String.format("A user is starting a mission called '%s'. " +
                "Generate a short, encouraging cheer message only in Korean, as raw text without any formatting.", missionName);
        
        String response = chatModel.call(prompt);
        System.out.println("AI Response (Cheer):\n" + response);
        
        return response.trim();
    }

    public String generateCompletionMessage(String missionName, String description) {
        String prompt = String.format("A user has completed a mission called '%s'. " +
                "The user's description of the completion is: '%s'. " +
                "Generate a short, praising message only in Korean, as raw text without any formatting.", missionName, description);
        
        String response = chatModel.call(prompt);
        System.out.println("AI Response (Completion):\n" + response);
        
        return response.trim();
    }

    public String generateSummary(String data) {
        String prompt = String.format("Based on the following user mission and emotion data during a period: \n%s\n " +
                "Generate a short one-sentence AI summary and encouragement in Korean. Output only the text without any formatting.", data);
        
        String response = chatModel.call(prompt);
        System.out.println("AI Response (Summary):\n" + response);
        
        return response.trim();
    }
}
