package com.gdgoc.t26.zero_gap;

import com.gdgoc.t26.zero_gap.config.TestAiConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
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
class ZeroGapApplicationTests {

	@Test
	void contextLoads() {
	}

}
