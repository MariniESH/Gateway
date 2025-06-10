package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        String corso = "http://localhost:8080";
        String docenteAlunni = "http://localhost:8081";
        return builder.routes()
                .route(r -> r.path("/corsi/**", "/iscrizioni/**")
                                .uri(corso)
                )
                .route(r -> r.path("/alunni/**", "/docenti/**")
                .uri(docenteAlunni))
                .build();
    }
}
