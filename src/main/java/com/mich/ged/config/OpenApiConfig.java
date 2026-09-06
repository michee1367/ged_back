package com.mich.ged.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    public static final String SECURITY_SCHEME_NAME = "jwt";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://apiged.mink67.com/api/v1").description("Serveur HTTPS Production prefix"),
                        new Server().url("https://apiged.mink67.com").description("Serveur HTTPS Production"),
                        new Server().url("http://localhost:8182").description("Serveur Local Dev"),
                        new Server().url("http://localhost:8182/api/v1").description("Serveur Local Dev prefix")
                ))
                .info(new Info()
                        .title("GED API")
                        .version("1.0"))
                // 1. Déclarer le composant de sécurité
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Entrez votre jeton JWT (sans le mot 'Bearer')")))
                // 2. Appliquer la sécurité sur tous les endpoints
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}