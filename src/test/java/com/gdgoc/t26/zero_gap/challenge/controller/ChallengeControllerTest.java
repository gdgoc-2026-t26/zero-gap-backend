package com.gdgoc.t26.zero_gap.challenge.controller;

import com.gdgoc.t26.zero_gap.challenge.domain.Challenge;
import com.gdgoc.t26.zero_gap.challenge.domain.ChallengeStatus;
import com.gdgoc.t26.zero_gap.challenge.domain.DurationCategory;
import com.gdgoc.t26.zero_gap.challenge.domain.UserChallenge;
import com.gdgoc.t26.zero_gap.challenge.service.ChallengeService;
import com.gdgoc.t26.zero_gap.config.TestAiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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
@AutoConfigureMockMvc(addFilters = false)
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
        Challenge challenge = Challenge.builder()
                .id(UUID.randomUUID())
                .title("Test Challenge")
                .description("Desc")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        
        when(challengeService.getRecommendedChallenges(DurationCategory.SHORT)).thenReturn(List.of(challenge));

        mockMvc.perform(get("/challenges")
                        .param("duration", "SHORT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Challenge"))
                .andExpect(jsonPath("$[0].durationCategory").value("SHORT"));
    }

    @Test
    @DisplayName("POST /challenges/{id}/start should start a challenge with description")
    void startChallengeShouldReturnUserChallenge() throws Exception {
        UUID challengeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String description = "User description";
        
        UserChallenge userChallenge = UserChallenge.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .challenge(Challenge.builder().id(challengeId).build())
                .status(ChallengeStatus.STARTED)
                .description(description)
                .startTime(LocalDateTime.now())
                .build();

        when(challengeService.startChallenge(any(UUID.class), eq(challengeId), eq(description))).thenReturn(userChallenge);

        mockMvc.perform(post("/challenges/" + challengeId + "/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"" + description + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("STARTED"))
                .andExpect(jsonPath("$.description").value(description));
    }

    @Test
    @DisplayName("POST /challenges/{id}/complete should complete a challenge")
    void completeChallengeShouldReturnUserChallenge() throws Exception {
        UUID challengeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        
        UserChallenge userChallenge = UserChallenge.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .challenge(Challenge.builder().id(challengeId).build())
                .status(ChallengeStatus.COMPLETED)
                .completionTime(LocalDateTime.now())
                .build();

        when(challengeService.completeChallenge(any(UUID.class), eq(challengeId))).thenReturn(userChallenge);

        mockMvc.perform(post("/challenges/" + challengeId + "/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.challengeId").value(challengeId.toString()));
    }
}