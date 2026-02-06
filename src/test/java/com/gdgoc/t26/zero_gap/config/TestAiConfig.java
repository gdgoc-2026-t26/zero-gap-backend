package com.gdgoc.t26.zero_gap.config;

import org.mockito.Mockito;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestAiConfig {

    @Bean
    @Primary
    public ChatModel mockChatModel() {
        ChatModel mock = Mockito.mock(ChatModel.class);
        Mockito.when(mock.call(Mockito.anyString())).thenReturn("{\"title\": \"Default AI Title\"}");
        return mock;
    }
}
