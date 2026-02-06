package com.gdgoc.t26.zero_gap.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Zero Gap API Docs")
                        .description("Zero Gap Backend API Documentation")
                        .version("v0.0.1"));
    }
}
