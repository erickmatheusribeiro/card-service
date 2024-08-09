package com.spring.card.util.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

@Value("${swagger.server.address}")
private String serverUrl;


    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Gerenciamento de Cartão")
                                .description("API para gerenciamento de cartões")
                                .version("1.0.0")
                ).addServersItem(new Server().url(serverUrl))
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")).components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth", new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer")
                        )
                );
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Cartao")
                .packagesToScan("com.spring.card.frameworks.web")
                .build();
    }
}
