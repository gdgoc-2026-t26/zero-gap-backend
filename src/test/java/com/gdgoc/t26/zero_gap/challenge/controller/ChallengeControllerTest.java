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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        // Given
        Challenge challenge = Challenge.builder()
                .id(UUID.randomUUID())
                .title("Test Challenge")
                .description("Desc")
                .durationCategory(DurationCategory.SHORT)
                .aiGenerated(false)
                .build();
        
        when(challengeService.getRecommendedChallenges(DurationCategory.SHORT)).thenReturn(List.of(challenge));

        // When & Then
        mockMvc.perform(get("/challenges")
                        .param("duration", "SHORT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Challenge"))
                .andExpect(jsonPath("$[0].durationCategory").value("SHORT"));
    }
}
