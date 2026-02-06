package com.gdgoc.t26.zero_gap.auth;

import com.gdgoc.t26.zero_gap.config.TestAiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc 
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
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("GET /challenges without authentication should return 401 Unauthorized")
    void getChallengesWithoutAuthShouldReturn401() throws Exception {
        mockMvc.perform(get("/challenges"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /challenges with valid JWT should return 200 OK")
    void getChallengesWithValidJwtShouldReturn200() throws Exception {
        String token = jwtTokenProvider.createToken("test-user");
        
        mockMvc.perform(get("/challenges")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
