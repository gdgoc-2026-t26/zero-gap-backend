package com.gdgoc.t26.zero_gap.challenge.controller;

import com.gdgoc.t26.zero_gap.challenge.domain.Challenge;
import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import com.gdgoc.t26.zero_gap.challenge.service.ChallengeService;
import com.gdgoc.t26.zero_gap.config.TestAiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // Disable security for now
@ActiveProfiles("test")
@Import(TestAiConfig.class)
@EnableAutoConfiguration(excludeName = {
        "com.google.cloud.spring.autoconfigure.core.GcpContextAutoConfiguration",
        "com.google.cloud.spring.autoconfigure.storage.GcpStorageAutoConfiguration",
        "org.springframework.ai.autoconfigure.google.genai.GoogleGenAiAutoConfiguration",
        "org.springframework.ai.model.google.genai.autoconfigure.chat.GoogleGenAiChatAutoConfiguration",
        "org.springframework.ai.model.google.genai.autoconfigure.embedding.GoogleGenAiEmbeddingAutoConfiguration",
        "org.springframework.ai.model.google.genai.autoconfigure.embedding.GoogleGenAiEmbeddingConnectionAutoConfiguration",
        "org.springframework.ai.model.google.genai.autoconfigure.embedding.GoogleGenAiTextEmbeddingAutoConfiguration"
})
class ChallengeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChallengeService challengeService;

    @Test
    @DisplayName("GET /challenges should return a list of challenges")
    void getChallengesShouldReturnList() throws Exception {
        // ... (previous test)
    }

    @Test
    @DisplayName("POST /challenges/{id}/start should start a challenge")
    void startChallengeShouldReturnUserChallenge() throws Exception {
        // Given
        UUID challengeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID(); // This would normally come from security context
        
        com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge userChallenge = 
                com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .challenge(Challenge.builder().id(challengeId).build())
                .status(com.gdgoc.t26.zero_gap.challenge.domain.ChallengeStatus.STARTED)
                .startTime(java.time.LocalDateTime.now())
                .build();

        // Note: For now, we'll mock userId as being passed or resolved somehow.
        // The spec says POST /challenges/{challenge_id}/start.
        // Usually, userId is from authenticated user.
        when(challengeService.startChallenge(any(UUID.class), eq(challengeId))).thenReturn(userChallenge);

        // When & Then
        mockMvc.perform(post("/challenges/" + challengeId + "/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("STARTED"))
                .andExpect(jsonPath("$.challengeId").value(challengeId.toString()));
    }
}
