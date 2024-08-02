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

//    @Value("${swagger.server.address}")
//    private String serverUrl;
//
//    private static final String SCHEME_NAME = "bearerAuth";
//
//    private static final String BEARER_FORMAT = "JWT";
//
//    private static final String SCHEME = "bearer";
//
//    @Bean
//    public OpenAPI apiDocConfig() {
//        return new OpenAPI()
//                .info(
//                        new Info()
//                                .title("Sistema de Gestão de Cartões")
//                                .description("API para Microsserviço de Gestão de Cartões")
//                                .version("1.0.0")
//                ).addServersItem(new Server().url(serverUrl))
//                .addSecurityItem(new SecurityRequirement()
//                        .addList(SCHEME_NAME)).components(new Components()
//                        .addSecuritySchemes(
//                                SCHEME_NAME, new SecurityScheme()
//                                        .name(SCHEME_NAME)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .bearerFormat(BEARER_FORMAT)
//                                        .in(SecurityScheme.In.HEADER)
//                                        .scheme(SCHEME)
//                        )
//                );
//    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Cartao")
                .packagesToScan("com.spring.card.frameworks.web")
                .build();
    }

}
