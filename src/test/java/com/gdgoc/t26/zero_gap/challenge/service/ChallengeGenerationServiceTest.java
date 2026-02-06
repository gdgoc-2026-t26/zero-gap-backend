package com.gdgoc.t26.zero_gap.challenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgoc.t26.zero_gap.challenge.domain.Challenge;
import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ChatModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChallengeGenerationServiceTest {

    @Mock
    private ChatModel chatModel;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ChallengeGenerationService challengeGenerationService;

    @Test
    @DisplayName("Should generate a challenge using AI")
    void shouldGenerateChallengeUsingAI() throws Exception {
        // Given
        String aiResponseContent = """
                {
                  "title": "AI Generated Challenge"
                }
                """;
        
        when(chatModel.call(any(String.class))).thenReturn(aiResponseContent);
        
        java.util.Map<String, String> responseMap = new java.util.HashMap<>();
        responseMap.put("title", "AI Generated Challenge");
        
        when(objectMapper.readValue(aiResponseContent, java.util.Map.class)).thenReturn(responseMap);
        
        // When
        Challenge challenge = challengeGenerationService.generateChallenge(DurationCategory.SHORT);

        // Then
        assertThat(challenge.getTitle()).isEqualTo("AI Generated Challenge");
        assertThat(challenge.getDescription()).isEqualTo("");
        assertThat(challenge.getAiGenerated()).isTrue();
        assertThat(challenge.getDurationCategory()).isEqualTo(DurationCategory.SHORT);
    }
}
